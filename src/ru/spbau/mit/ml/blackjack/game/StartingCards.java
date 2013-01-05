package ru.spbau.mit.ml.blackjack.game;

import ru.spbau.mit.ml.blackjack.cards.Card;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 05.01.13
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */
public class StartingCards {
    public Card first;
    public Card second;
    public Card dealer;

    StartingCards(Card first, Card second, Card dealer) {
        this.first = first;
        this.second = second;
        this.dealer = dealer;
    }
}
