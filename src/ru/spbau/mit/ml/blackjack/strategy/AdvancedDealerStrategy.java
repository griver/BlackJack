package ru.spbau.mit.ml.blackjack.strategy;

import ru.spbau.mit.ml.blackjack.game.Position;
import ru.spbau.mit.ml.blackjack.player.Player;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 05.01.13
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class AdvancedDealerStrategy implements IDealerStrategy{
    protected Player opponent;

    public AdvancedDealerStrategy(Player opponent) {
        this.opponent = opponent;
    }

    public AdvancedDealerStrategy() {

    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    @Override
    public boolean hit(Position dealerPosition) {
        if(opponent != null)
            return dealerPosition.getPoints() < opponent.getCurrentPosition().getPoints();
        return false;
    }

    @Override
    public boolean close(Position dealerPosition) {
        return !hit(dealerPosition);
    }

    @Override
    public String toString() {
        return "advanced_dealer_strategy";
    }
}
