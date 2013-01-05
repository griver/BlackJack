package ru.spbau.mit.ml.blackjack.cards;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 05.01.13
 * Time: 0:46
 * To change this template use File | Settings | File Templates.
 */
public class Deck {

    static private Card[] createDeck() {
        Card[] deck = new Card[Rank.values().length];
        int i = 0;
        for(Rank rank : Rank.values())
            deck[rank.ordinal()] = new Card(rank, 1 / (double)Rank.values().length );
        return deck;
    }

    private static Random random = new Random();

    private Card[] cards;



    public Deck() {
        cards = createDeck();
    }

    public Card getRandomCard(){
        return cards[random.nextInt(cards.length)];
    }

    public Card getCard(Rank rank) {
        return cards[rank.ordinal()];
    }

    public Card getCard(int i) {
        if(cards.length > i && i >= 0)
            return cards[i];
        return null;
    }
}
