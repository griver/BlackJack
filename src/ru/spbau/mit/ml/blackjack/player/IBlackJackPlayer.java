package ru.spbau.mit.ml.blackjack.player;

import ru.spbau.mit.ml.blackjack.cards.Card;
import ru.spbau.mit.ml.blackjack.game.Position;
import ru.spbau.mit.ml.blackjack.game.StartingCards;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 05.01.13
 * Time: 1:06
 * To change this template use File | Settings | File Templates.
 */
public interface IBlackJackPlayer {

    boolean isBust(); // перебор

    /**
     * AbstractPlayer takes new card
     * @param card - new card
     * @return false if bust(), false - otherwise
     */
    boolean takes(Card newCard);

    /**
     * true if player wants to get new card, false - otherwise
     */
    boolean hit();

    /**
     * true if player wonts to stop, false - if he wants to continue
     */
    boolean close();

    /**
     * player show sum of his cards
     * @return
     */
    int showdowns();

    Position getCurrentPosition();
}
