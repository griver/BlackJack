package ru.spbau.mit.ml.blackjack.strategy;

import ru.spbau.mit.ml.blackjack.cards.Card;
import ru.spbau.mit.ml.blackjack.cards.Deck;
import ru.spbau.mit.ml.blackjack.cards.Rank;
import ru.spbau.mit.ml.blackjack.game.Position;
import ru.spbau.mit.ml.blackjack.player.AbstractPlayer;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 05.01.13
 * Time: 15:53
 * To change this template use File | Settings | File Templates.
 */
public class LearningStrategy implements IStrategy {
    private static final int STOP_VAL = 20;
    private static final int MAX_VAL = 21;

    private Map<Position, Integer> posToIndex = new HashMap<Position, Integer>();
    private Deck deck;
    private double[] winFactor;
    private double[] stopFactor;
    private int[] successNumber;
    private int[] caseNumber;
    private boolean[] strategy;

    private boolean analyze = false;
    private LinkedList<Position> visitedPositions = new LinkedList<Position>();

    public LearningStrategy(Set<Position> positions, Map<Position, Double> stopFactorMap, Deck deck) {

        int i = 0;
        for(Position pos : positions)
            posToIndex.put(pos, i++);

        this.deck = deck;
        this.winFactor = new double[i];
        this.stopFactor = new double[i];
        successNumber = new int[i];
        caseNumber = new int[i];
        strategy = new boolean[i];

        initStrategy();
        initArrays();
        initStopFactor(stopFactorMap);
    }

    //---utilities----------------------------
    protected int index(Position position) {
        if(!posToIndex.containsKey(position))
            System.err.println("Invalid position: " + position);
        return posToIndex.get(position);
    }

    protected Set<Position> getPositions() {
       return posToIndex.keySet();
    }

    protected void initStrategy() {
        if(strategy == null) return;

        for(Position pos : getPositions()){
            strategy[index(pos)] = pos.getPoints() < STOP_VAL;
        }
    }

    protected void initArrays() {
        Arrays.fill(successNumber, 0);
        Arrays.fill(caseNumber, 0);
        Arrays.fill(winFactor, 0.0);
    }

    protected void initStopFactor(Map<Position, Double> stopFactorMap) {
        Arrays.fill(stopFactor, 0.0);
        for(Position pos : getPositions()) {
            if(!stopFactorMap.containsKey(pos)) {
                System.err.println("stopFactorMap is incomplete");
                continue;
            }
            stopFactor[index(pos)] = stopFactorMap.get(pos);
        }
    }
    //---/utilities----------------------------


    @Override
    public boolean hit(Position position) {
        if(analyze) {
            if(visitedPositions.size() == 0 || !position.equals(visitedPositions.getLast()))
                visitedPositions.addLast(position.clone());
        }
        return strategy[index(position)];
    }

    @Override
    public boolean close(Position position) {
        return !hit(position);
    }
    public void startGameAnalysis() {
        analyze = true;
        visitedPositions.clear();
    }
    public void endGameAnalysis(int gameResult){
        for (Position pos : visitedPositions) {
            successNumber[index(pos)] += gameResult;
            ++caseNumber[index(pos)];
        }

        visitedPositions.clear();
        analyze = false;
    }

    public boolean calculateNewStrategy() {
        boolean isChanged = false;

        for(int i =0; i < winFactor.length; ++i)
            winFactor[i] = ((double) successNumber[i]) / (double) caseNumber[i];

        int index;
        double hitFactor;
        boolean newAction;
        for(Position pos : this.getPositions()){
            index = index(pos);
            hitFactor = calculateHitFactor(pos);
            newAction = hitFactor > stopFactor[index];

            if(strategy[index] != newAction){
                strategy[index] = newAction;
                isChanged = true;
            }
        }

        initArrays();
        return isChanged;
    }

    protected double calculateHitFactor(Position position) {
        double win = 0.0;
        Position newPos;
        Card  newCard;
        for(Rank rank : Rank.values()) {
            newCard = deck.getCard(rank);
            newPos = position.clone();

            AbstractPlayer.takes(newPos, newCard);

            if(posToIndex.containsKey(newPos))
                win += newCard.getProbability() * winFactor[index(newPos)];
            else
                win += 0.0;
        }
        return win;
    }

}
