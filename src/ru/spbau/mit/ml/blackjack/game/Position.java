package ru.spbau.mit.ml.blackjack.game;

import ru.spbau.mit.ml.blackjack.cards.Card;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 04.01.13
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */
public class Position {
    Card dealerCard;
    int points;
    boolean aseBonus;

    public Position() {
        set(null, 0, false);
    }

    public Position(int points, boolean aseBonus) {
        set(null, points, aseBonus);
    }

    public Position(Card dealerCard, int playerSum, boolean useAseBonus) {
        set(dealerCard, playerSum, useAseBonus);
    }

    public Card getDealerCard() {
        return dealerCard;
    }

    public void set(Card dealerCard, int playerSum, boolean useAseBonus) {
        this.dealerCard = dealerCard;
        this.points = playerSum;
        this.aseBonus = useAseBonus;
    }

    public void setDealerCard(Card dealerCard) {
        this.dealerCard = dealerCard;
    }

    public int getPoints() {
        return points;
    }


    public void addPoints(int value) {
        this.points += value;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean haveAseBonus() {
        return aseBonus;
    }

    public void setAseBonus(boolean useAseBonus) {
        this.aseBonus = useAseBonus;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position) return equals((Position)obj);
        return false;
    }

    public boolean equals(Position pos) {
        if(pos.aseBonus == this.aseBonus)
            if(this.points == pos.points)
                if(this.dealerCard.getPoints() == pos.getDealerCard().getPoints())
                    return true;
        return false;
    }

    @Override
    public int hashCode() {
        int bonusVal = aseBonus ? 1 : 0;
        return points * 1000  + dealerCard.getPoints()* 10 + bonusVal;
    }

    @Override
    public Position clone() {
        return new Position(dealerCard, points, aseBonus);
    }

    @Override
    public String toString() {
        return points + " : " + aseBonus +" : " + dealerCard.getPoints() ;
    }
}
