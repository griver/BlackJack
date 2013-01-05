package ru.spbau.mit.ml.blackjack.player;

import ru.spbau.mit.ml.blackjack.cards.Card;
import ru.spbau.mit.ml.blackjack.cards.Deck;
import ru.spbau.mit.ml.blackjack.game.Position;
import ru.spbau.mit.ml.blackjack.game.StartingCards;
import ru.spbau.mit.ml.blackjack.strategy.IDealerStrategy;
import ru.spbau.mit.ml.blackjack.strategy.IStrategy;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 05.01.13
 * Time: 0:46
 * To change this template use File | Settings | File Templates.
 */
public class Dealer extends StrategicPlayer {

    public Dealer(IDealerStrategy strategy, Position position) {
        super(strategy, position);
    }

    public void init(Card dealerCard) {
        position.set(dealerCard, 0, false);
        this.takes(dealerCard);
    }



}
