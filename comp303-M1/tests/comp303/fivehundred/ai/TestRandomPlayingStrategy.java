package comp303.fivehundred.ai;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Zaz on 2016-01-17.
 */

public class TestRandomPlayingStrategy
{
    @Test
    public void testRandomPlayingStrategyConstructorOnly()
    {
        new RandomPlayingStrategy ();
    }


/*	@Test(expected = NullPointerException.class)
	public void testRandomPlayingStrategyPlayEmptyArgumentHand()
	{
		RandomPlayingStrategy randomPlayingStrategy = new RandomPlayingStrategy ();
		randomPlayingStrategy.play(null, null);
	}

*/

    /*	@Test(expected = NullPointerException.class)
        public void testRandomPlayingStrategyPlayEmptyArgumentTrick()
        {
            RandomPlayingStrategy randomPlayingStrategy = new RandomPlayingStrategy ();
            Hand h = new Hand();
            h.add(new Card(Rank.FOUR,Suit.SPADES));
            h.add(new Card(Rank.FIVE,Suit.CLUBS));
            randomPlayingStrategy.play(null, h);
        }

    */
    @Test
    public void testRandomPlayingStrategyPlay()
    {
        RandomPlayingStrategy randomPlayingStrategy = new RandomPlayingStrategy ();

        Hand h = new Hand();
        h.add(new Card(Card.Rank.FIVE, Card.Suit.SPADES));
        //Card c = randomPlayingStrategy.play(null, h);
        //assertTrue((c.getRank()==Rank.FIVE)&&(c.getSuit()==Suit.SPADES));

        //h.add(new Card(Rank.FIVE,Suit.SPADES));
        Card c = randomPlayingStrategy.play(new Trick(new Bid(10)), h);

        assertTrue((c.getRank()== Card.Rank.FIVE)&&(c.getSuit()== Card.Suit.SPADES));

        h.add(new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS));
        h.add(new Card(Card.Rank.FIVE, Card.Suit.CLUBS));
        h.add(new Card(Card.Rank.FIVE, Card.Suit.HEARTS));
        h.add(new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS));
        h.add(new Card(Card.Rank.FOUR, Card.Suit.SPADES));
        h.add(new Card(Card.Rank.FOUR, Card.Suit.CLUBS));
        h.add(new Card(Card.Rank.FOUR, Card.Suit.HEARTS));
        h.add(new Card(Card.Rank.SIX, Card.Suit.DIAMONDS));
        h.add(new Card(Card.Rank.SIX, Card.Suit.SPADES));
        h.add(new Card(Card.Rank.SIX, Card.Suit.CLUBS));
        h.add(new Card(Card.Rank.SIX, Card.Suit.HEARTS));
        Card myCard = randomPlayingStrategy.play(new Trick(new Bid(20)), h);

        assertTrue(myCard.getRank()== Card.Rank.FOUR || myCard.getRank()== Card.Rank.FIVE || myCard.getRank()== Card.Rank.SIX);

    }
}
