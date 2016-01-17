package comp303.fivehundred.ai;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;

import java.util.Random;

/**
 * Enters a valid but random bid. Passes a configurable number of times.
 * If the robot does not pass, it uses a universal probability
 * distribution across all bids permitted.
 */
public class RandomBiddingStrategy implements IBiddingStrategy
{
	private Bid bid;
	/**
	 * Builds a robot that passes 50% of the time and bids randomly otherwise.
	 */
	public RandomBiddingStrategy()
	{
		Random r = new Random(System.currentTimeMillis());

		if(r.nextInt(2) == 0){
			bid = new Bid();
		}
		else{
			bid = new Bid(r.nextInt(25));
		}
	}
	
	/** 
	 * Builds a robot that passes the specified percentage number of the time.
	 * @param pPassFrequency A percentage point (e.g., 50 for 50%) of the time the robot 
	 * will pass. Must be between 0 and 100 inclusive. 
	 * Whether the robot passes is determined at random.
	 */
	public RandomBiddingStrategy(int pPassFrequency)
	{
		assert pPassFrequency >= 0 && pPassFrequency <= 100;

		Random r = new Random(System.currentTimeMillis());

		if(r.nextInt(101) <= pPassFrequency){
			bid = new Bid();
		}
		else{
			bid = new Bid(r.nextInt(25));
		}
	}
	
	@Override
	public Bid selectBid(Bid[] pPreviousBids, Hand pHand)
	{
		Bid max  = Bid.max(pPreviousBids);

		if(max.getType() == Bid.Type.PASS)
			return bid;
		else{
			int maxIndex = max.toIndex();
			if(maxIndex == 24) return new Bid();
			else {
				Random r = new Random(System.currentTimeMillis());
				int newIndex = r.nextInt(24-maxIndex)+maxIndex+1;
				return bid.getType() == Bid.Type.PASS ? bid : new Bid(newIndex);
			}
		}

	}

}
