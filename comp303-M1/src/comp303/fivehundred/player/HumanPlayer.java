package comp303.fivehundred.player;

import comp303.fivehundred.engine.Events.Event;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;

/**
 * Created by Zaz on 2016-03-05.
 */
public class HumanPlayer implements IPlayer {
    /**
     * Produces a valid bid, i.e., a between 6 and 10 for any suit or
     * no trump, that is higher that the last bid.
     *
     * @param pPreviousBids All the previous bids for this hand, in order. The
     *                      size of the array is the number of bids already entered (between 0 and 3).
     * @param pHand         The cards in the hand of the player entering the bid.
     * @return A valid bid (higher than the last bid, or pass).
     * @pre pPreviousBids.length <= 4
     * @pre pHand.size() == 10
     */
    @Override
    public Bid selectBid(Bid[] pPreviousBids, Hand pHand) {
        return null;
    }

    /**
     * Select exactly 6 cards from the 16 cards in pHand (basic deal plus widow),
     * to remove from the hand.
     *
     * @param pBids  The bids for this round. An array of 4 elements.
     * @param pIndex The index of the player in this round. Between 0 and 3 incl.
     * @param pHand  The hand of cards for this player, with the widow added in.
     * @return Six cards to remove from the hand.
     * @pre A least one bid in pBids is non-passing.
     * @pre pBids.length == 4
     * @pre pIndex >= 0 && pIndex <= 3
     * @pre pHand.size() == 16
     */
    @Override
    public CardList selectCardsToDiscard(Bid[] pBids, int pIndex, Hand pHand) {
        return null;
    }

    /**
     * Selects a card to be played by the player. This method should have no
     * side effect, i.e., it should not remove any cards from Hand.
     *
     * @param pTrick Cards played so far in the trick. Note that the
     *               number of cards in pTrick determines the playing order of the player. For
     *               example, if pTrick.size() == 0, the player leads. If pTrick.size() == 1, he plays
     *               second, etc.
     * @param pHand  The hand of the player to play.
     * @return One of the cards in pHand. The card must be a legal play, that is, follow suit
     * if possible.
     * @pre pTrick != null
     * @pre pHand != null
     */
    @Override
    public Card play(Trick pTrick, Hand pHand) {
        return null;
    }

    @Override
    public void listen(Event e) {

    }
}
