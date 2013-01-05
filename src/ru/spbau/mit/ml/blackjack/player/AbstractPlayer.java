package ru.spbau.mit.ml.blackjack.player;

import ru.spbau.mit.ml.blackjack.cards.Card;
import ru.spbau.mit.ml.blackjack.cards.Rank;
import ru.spbau.mit.ml.blackjack.game.Position;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 04.01.13
 * Time: 21:53
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPlayer implements IBlackJackPlayer {
    protected Position position;
    protected static final int maxSum = 21;


    public AbstractPlayer(Position position) {
        this.position = position;
    }

    @Override
    public int showdowns() {
        return position.getPoints();
    }

    @Override
    public boolean isBust() {
        return isBust(position);
    }

    @Override
    public boolean takes(Card card){
        return takes(position, card);
    }

    @Override
    public Position getCurrentPosition() {
        return position.clone();
    }

    //--static functions---------------------
    public static boolean isBust(Position position) {
        return position.getPoints() > maxSum;
    }

    protected static boolean canAdd(Position position, int points) {
        return position.getPoints() + points <= maxSum;
    }

    protected static void removeAseBonus(Position position) {
        if(position.haveAseBonus()) {
            position.addPoints(-10);
            position.setAseBonus(false);
        }
    }

    protected static void addAseBonus(Position position) {
        if(!position.haveAseBonus() && canAdd(position, 10)) {
            position.addPoints(10);
            position.setAseBonus(true);
        }
    }

    public static boolean takes(Position position, Card card) {
        position.addPoints(card.getPoints());
        if(!isBust(position)) {
            if(card.getRank() == Rank.ASE) addAseBonus(position);
        } else {
            removeAseBonus(position);
        }

        return !isBust(position);
    }

}
