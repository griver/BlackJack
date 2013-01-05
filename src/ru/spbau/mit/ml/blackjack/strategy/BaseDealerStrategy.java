package ru.spbau.mit.ml.blackjack.strategy;

import ru.spbau.mit.ml.blackjack.game.Position;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 05.01.13
 * Time: 1:40
 * To change this template use File | Settings | File Templates.
 */
public class BaseDealerStrategy implements IDealerStrategy {
    protected int stopValue;

    public BaseDealerStrategy(int stopValue) {
        this.stopValue = stopValue;
    }

    @Override
    public boolean hit(Position dealerPosition) {
        return dealerPosition.getPoints() < stopValue;
    }

    @Override
    public boolean close(Position dealerPosition) {
        return !hit(dealerPosition);
    }

    @Override
    public String toString() {
        return stopValue + "_dealer_strategy";
    }
}
