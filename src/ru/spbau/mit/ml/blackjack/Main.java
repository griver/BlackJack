package ru.spbau.mit.ml.blackjack;

import ru.spbau.mit.ml.blackjack.cards.Card;
import ru.spbau.mit.ml.blackjack.cards.Deck;
import ru.spbau.mit.ml.blackjack.cards.Rank;
import ru.spbau.mit.ml.blackjack.game.Game;
import ru.spbau.mit.ml.blackjack.game.Position;
import ru.spbau.mit.ml.blackjack.game.StartingCards;
import ru.spbau.mit.ml.blackjack.player.Dealer;
import ru.spbau.mit.ml.blackjack.player.IBlackJackPlayer;
import ru.spbau.mit.ml.blackjack.player.Player;
import ru.spbau.mit.ml.blackjack.player.StrategicPlayer;
import ru.spbau.mit.ml.blackjack.strategy.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 04.01.13
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    static final int MAX_SUM = 21;
    static final int SUITS_NUMBER = 4;
    static final int DECK_SIZE = SUITS_NUMBER * Rank.values().length;
    static final int TESTS_NUMBER = 1000;

    static Dealer createDealer(IDealerStrategy dealerStrategy) {
        Position dealerPosition = new Position();
        return new Dealer(dealerStrategy, dealerPosition);
    }

    static public void main(String[] argv) {

        Deck deck = new Deck();

        Set<Position> positions = createAllPossiblePositions(deck);

        Game game = new Game(deck, positions);

        Map<Position, Double> stopFactorMap = calculateStopFactor(positions, game);

        Position playerPosition = new Position();
        LearningStrategy playerStrategy = new LearningStrategy(positions, stopFactorMap, deck);
        Player player = new Player(playerStrategy, playerPosition);

        IDealerStrategy dealerStrategy = new AdvancedDealerStrategy(player);  // new BaseDealerStrategy(17);
        Dealer dealer = createDealer(dealerStrategy);


        do {

            int gameResult = 0;
            for(int i = 0; i < TESTS_NUMBER; ++i) {
                for (Position start : positions) {
                    playerStrategy.startGameAnalysis();

                    game.playFromPosition(player, dealer, start);

                    if(game.lastWinner() == null ) gameResult = 0;
                    else gameResult = game.lastWinner() == player ? 1 : -1;

                    playerStrategy.endGameAnalysis(gameResult);
                }
            }

        } while (playerStrategy.calculateNewStrategy());

        try {
            printStrategy(dealerStrategy.toString(), positions, playerStrategy);
        } catch (FileNotFoundException e) {
            System.err.println("can't save strategy in file");
            e.printStackTrace(System.err);
        }
    }

    static public void printStrategy(String filename, Set<Position> positions, LearningStrategy strategy) throws FileNotFoundException {
        PrintWriter printer = new PrintWriter(filename);
        for(Position pos : positions)
            printer.println(pos + " -> " + strategy.hit(pos));
        printer.flush();
        printer.close();
    }

    static public Set<Position> createAllPossiblePositions(Deck deck) {
        Set<Position> positions = new HashSet<Position>();
        Position position = new Position();
        Player player = new Player(null, position);

        Card first, second;
        for(Rank dealerRank : Rank.values()) {
            for(Rank one : Rank.values()) {
                for(Rank two : Rank.values()) {

                    player.init(deck.getCard(dealerRank));
                    player.takes(deck.getCard(one));
                    player.takes(deck.getCard(two));

                    if( !positions.contains(position) ){
                        recursiveCreatePosition(deck, player, position, positions);
                    }
                }
            }
        }
        return positions;
    }

    static void recursiveCreatePosition(Deck deck, Player player, Position position, Set<Position> positions){
        if(player.isBust()) return;
        Position oldPos = position.clone();
        positions.add(oldPos);

        for(Rank rank : Rank.values()) {
            player.takes(deck.getCard(rank));
            if(!positions.contains(position))
                recursiveCreatePosition(deck, player, position, positions);
            player.init(oldPos);
        }
    }


    static public Map<Position, Double> calculateStopFactor(Set<Position> positions, Game game) {
        Map<Position, Double> stopFactorMap = new HashMap<Position, Double>();

        IStrategy alwaysStop = new BaseDealerStrategy(0);
        Player player = new Player(alwaysStop, new Position());

        Dealer dealer = createDealer(new AdvancedDealerStrategy(player));  //new BaseDealerStrategy(17));

        int stopFactor, gameResult;
        for(Position position : positions) {
            stopFactor = 0;
            for(int i = 0; i < TESTS_NUMBER; ++i) {
                gameResult = 0;
                game.playFromPosition(player, dealer, position);

                if(game.lastWinner() == player) gameResult = 1;
                else if(game.lastWinner() == dealer) gameResult = -1;
                stopFactor += gameResult;
            }
            stopFactorMap.put(position, ((double)stopFactor)/(double)TESTS_NUMBER);
        }

        return stopFactorMap;
    }

}
