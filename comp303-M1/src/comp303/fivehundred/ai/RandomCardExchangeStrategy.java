package comp303.fivehundred.ai;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Picks six cards at random. 
 */
public class RandomCardExchangeStrategy implements ICardExchangeStrategy
{
	@Override
	public CardList selectCardsToDiscard(Bid[] pBids, int pIndex, Hand pHand)
	{
        Random r = new Random(System.currentTimeMillis());
        List<Card> list = new ArrayList<>();
        CardList c = new CardList();
		for(int i = 0; i < 6; i++){
			list.add(pHand.getCardList().remove(r.nextInt(pHand.getCardList().size())));
		}
        c.setCardList(list);
        return c;
	}

}
