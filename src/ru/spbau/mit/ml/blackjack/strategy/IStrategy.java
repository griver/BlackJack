package ru.spbau.mit.ml.blackjack.strategy;

import ru.spbau.mit.ml.blackjack.game.Position;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 05.01.13
 * Time: 1:38
 * To change this template use File | Settings | File Templates.
 */
public interface IStrategy {

    boolean hit(Position position);

    boolean close(Position position);

}
