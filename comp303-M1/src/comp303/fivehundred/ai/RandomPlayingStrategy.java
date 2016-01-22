package comp303.fivehundred.ai;

import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * If leading, picks a card at random except a joker if the contract is in no trump.
 * If not leading and the hand contains cards that can follow suit, pick a suit-following 
 * card at random. If not leading and the hand does not contain cards that can follow suit,
 * pick a card at random (including trumps, if available).
 */
public class RandomPlayingStrategy implements IPlayingStrategy
{
	@Override
	public Card play(Trick pTrick, Hand pHand)
	{
		if(pTrick.size() == 0)
		{
            if(pTrick.getTrumpSuit() == null)
                return pHand.getCardList().get(new Random(System.currentTimeMillis()).nextInt(pHand.getCardList().size()));
            else
                return pHand.getCardList().get(new Random(System.currentTimeMillis()).nextInt(pHand.getCardList().size()));

		}else {
            CardList cList = pHand.playableCards(pTrick.getSuitLed(), pTrick.getTrumpSuit());
            List<Card> list = new ArrayList<>();
            cList.getCardList().stream()
                    .filter(card -> card.compareTo(pTrick.highest()) > 0)
            .forEach(card1 -> list.add(card1));
            if(list.isEmpty()) {
                CardList clone = pHand.clone();
                Collections.shuffle(clone.getCardList());
                return clone.getFirst();
            }
            else{
                Collections.shuffle(list);
                return list.get(0);
            }
        }
	}
}
