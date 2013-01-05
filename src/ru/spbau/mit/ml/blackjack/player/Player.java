package ru.spbau.mit.ml.blackjack.player;

import ru.spbau.mit.ml.blackjack.cards.Card;
import ru.spbau.mit.ml.blackjack.game.Position;
import ru.spbau.mit.ml.blackjack.game.StartingCards;
import ru.spbau.mit.ml.blackjack.strategy.IStrategy;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 05.01.13
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class Player extends StrategicPlayer {
    public Player(IStrategy strategy, Position position) {
        super(strategy, position);
    }

    public void init(int points, boolean useAseBonus, Card dealerCard){
        this.position.set(dealerCard, points, useAseBonus);
    }

    public void init(Card dealerCard){
        init(0, false, dealerCard);
    }

    public void init(Position position) {
        this.position.set(position.getDealerCard(), position.getPoints(), position.haveAseBonus());
    }
}
