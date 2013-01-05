package ru.spbau.mit.ml.blackjack.cards;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 04.01.13
 * Time: 20:22
 * To change this template use File | Settings | File Templates.
 */
public class Card {
    private Rank rank;
    private double probability;
    private int points;

    protected int calculatePoints(Rank rank) {
        if(rank.ordinal() <= Rank.TEN.ordinal()) return rank.ordinal() + 2;
        if(rank.ordinal() < Rank.ASE.ordinal()) return 10;
        return 1;
    }

    public Card(Rank rank, double probability) {
        this.rank = rank;
        this.probability = probability;
        points = calculatePoints(rank);
    }

    public Rank getRank() {
        return rank;
    }

    public double getProbability() {
        return probability;
    }

    public int getPoints() {
        return points;
    }

}
