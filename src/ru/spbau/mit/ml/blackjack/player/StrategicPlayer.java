package ru.spbau.mit.ml.blackjack.player;

import ru.spbau.mit.ml.blackjack.game.Position;
import ru.spbau.mit.ml.blackjack.strategy.IStrategy;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 05.01.13
 * Time: 1:37
 * To change this template use File | Settings | File Templates.
 */
public abstract class StrategicPlayer extends AbstractPlayer {
    protected IStrategy myStrategy;


    public StrategicPlayer(IStrategy strategy, Position position) {
        super(position);
        myStrategy = strategy;
    }


    /**
     * true if player wants to get new card, false - otherwise
     */
    @Override
    public boolean hit() {
        return myStrategy.hit(this.position);
    }

    /**
     * true if player wonts to stop, false - if he wants to continue
     */
    @Override
    public boolean close() {
        return myStrategy.close(this.position);
    }
}
