package comp303.fivehundred.ai;

import comp303.fivehundred.engine.Events.Event;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.player.IPlayer;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;

/**
 * Plays correctly but randomly, i.e., very badly.
 */
public class RandomRobot implements IPlayer
{
	private IBiddingStrategy aBiddingStrategy = new RandomBiddingStrategy(30);
	private ICardExchangeStrategy aCardExchangeStrategy = new RandomCardExchangeStrategy();
	private IPlayingStrategy aPlayingStrategy = new RandomPlayingStrategy();
	
	@Override
	public Bid selectBid(Bid[] pPreviousBids, Hand pHand)
	{
		return aBiddingStrategy.selectBid(pPreviousBids, pHand);
	}

	@Override
	public CardList selectCardsToDiscard(Bid[] pBids, int pIndex, Hand pHand)
	{
		return aCardExchangeStrategy.selectCardsToDiscard(pBids, pIndex, pHand);
	}

	@Override
	public Card play(Trick pTrick, Hand pHand)
	{
		return aPlayingStrategy.play(pTrick, pHand);
	}

	@Override
	public void listen(Event e) {

	}
}
