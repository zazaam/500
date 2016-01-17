package comp303.fivehundred.ai;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Zaz on 2016-01-17.
 */

public class TestRandomExchangeStrategy
{
    @Test
    public void testRandomBiddingStrategyConstructorOK()
    {
        new RandomCardExchangeStrategy ();
    }

    /*	@Test(expected=NullPointerException.class)
        public void testRandomBiddingStrategySelectCardsToDiscardEmptyInput()
        {
            RandomCardExchangeStrategy randomCardExStrategy = new RandomCardExchangeStrategy ();
            randomCardExStrategy.selectCardsToDiscard(null, 1, null);
        }
    */
    @Test
    public void testRandomBiddingStrategySelectCardsToDiscard()
    {
        Bid bid1 = new Bid(7, Card.Suit.HEARTS);
        Bid bid2 = new Bid(8, Card.Suit.CLUBS);
        Bid bid3 = new Bid(9, Card.Suit.SPADES);
        Bid bid4 = new Bid(10, Card.Suit.DIAMONDS);
        Bid[] bids = {bid1, bid2, bid3, bid4};

        RandomCardExchangeStrategy randomCardExStrategy = new RandomCardExchangeStrategy ();
        Hand h = new Hand();
        h.add(new Card(Card.Rank.FOUR, Card.Suit.SPADES));
        h.add(new Card(Card.Rank.FIVE, Card.Suit.CLUBS));
        h.add(new Card(Card.Rank.SIX, Card.Suit.DIAMONDS));
        h.add(new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS));
        h.add(new Card(Card.Rank.FIVE, Card.Suit.SPADES));
        h.add(new Card(Card.Rank.SIX, Card.Suit.CLUBS));
        h.add(new Card(Card.Rank.FOUR, Card.Suit.CLUBS));
        h.add(new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS));
        h.add(new Card(Card.Rank.SIX, Card.Suit.SPADES));
        h.add(new Card(Card.Rank.FOUR, Card.Suit.HEARTS));
        h.add(new Card(Card.Rank.FIVE, Card.Suit.CLUBS));
        h.add(new Card(Card.Rank.SIX, Card.Suit.HEARTS));
        h.add(new Card(Card.Rank.EIGHT, Card.Suit.CLUBS));
        h.add(new Card(Card.Rank.EIGHT, Card.Suit.HEARTS));
        h.add(new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS));
        h.add(new Card (Card.Rank.NINE, Card.Suit.DIAMONDS));
        h.add(new Card(Card.Rank.NINE, Card.Suit.CLUBS));

        CardList cl = randomCardExStrategy.selectCardsToDiscard(bids, 1, h);
        assertTrue(cl.size()==6);
    }


}
