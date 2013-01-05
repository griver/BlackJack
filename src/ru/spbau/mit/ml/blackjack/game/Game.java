package ru.spbau.mit.ml.blackjack.game;

import ru.spbau.mit.ml.blackjack.cards.Card;
import ru.spbau.mit.ml.blackjack.cards.Deck;
import ru.spbau.mit.ml.blackjack.player.Dealer;
import ru.spbau.mit.ml.blackjack.player.IBlackJackPlayer;
import ru.spbau.mit.ml.blackjack.player.Player;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 05.01.13
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
public class Game {
    private IBlackJackPlayer lastWinner = null;
    private Deck deck;
    private Set<Position> availablePositions;

    public Game(Deck deck, Set<Position> availablePositions) {
        this.deck = deck;
        this.availablePositions = availablePositions;
    }

    public void playFromPosition(Player player, Dealer dealer, Position start) {
        if(!availablePositions.contains(start))  return;

        player.init(start);
        dealer.init(start.dealerCard);

        while(!player.isBust() && player.hit()) {
            player.takes(deck.getRandomCard());
        }

        if(!player.isBust()) {

            dealer.takes(deck.getRandomCard());

            while (!dealer.isBust() && dealer.hit()) {
                dealer.takes(deck.getRandomCard());
            }
        }

        if(player.isBust()) lastWinner = dealer;
        else if(dealer.isBust()) lastWinner = player;
        else if(dealer.showdowns() == player.showdowns()) lastWinner = null;
        else lastWinner = (dealer.showdowns() > player.showdowns())? dealer : player;
    }


    public void play(Player player, Dealer dealer) {

        Card firstCard = deck.getRandomCard();
        Card secondCard = deck.getRandomCard();
        Card dealerCard = deck.getRandomCard();

        player.init(dealerCard);
        player.takes(firstCard);
        player.takes(secondCard);

        playFromPosition(player, dealer, player.getCurrentPosition());
    }

    public IBlackJackPlayer lastWinner() {
        return  lastWinner;
    }
}
