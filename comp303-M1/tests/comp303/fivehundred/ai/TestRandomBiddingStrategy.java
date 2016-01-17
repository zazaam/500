package comp303.fivehundred.ai;

/**
 * Created by Zaz on 2016-01-17.
 */

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class TestRandomBiddingStrategy
{
    @Test
    public void testMultipleCalls()
    {
        RandomBiddingStrategy input0 = new RandomBiddingStrategy (0);
    }

    @Test
    public void testRandomBiddingStrategyCorrectInput()
    {
        RandomBiddingStrategy input0 = new RandomBiddingStrategy (0);
        RandomBiddingStrategy input40 = new RandomBiddingStrategy (40);
        RandomBiddingStrategy input100 = new RandomBiddingStrategy (100);
    }

    @Test
    public void testRandomBiddingStrategySelectBid()
    {

        RandomBiddingStrategy input100 = new RandomBiddingStrategy (100);

        Bid[] bids = new Bid[3];
        bids[0] = new Bid(7);
        bids[1] = new Bid(8);
        bids[2] = new Bid(9);


        Hand h = new Hand();
        Bid result = input100.selectBid(bids, h);
        assertTrue(result.isPass());


    }

    @Test
    public void testCertain10NoTrump()
    {
        //strategy cannot pass
        RandomBiddingStrategy strategy = new RandomBiddingStrategy(0);
        // previous bid is the second strongest in the game
        Bid [] bids = {new Bid(10, Card.Suit.HEARTS)};
        Hand hand = new Hand();
        Bid selectedBid = strategy.selectBid(bids, hand);
        //We expect the strategy to produce the strongest bid ( since it cannot pass )
        assertTrue(selectedBid.equals(new Bid(10, null)));
    }
}

