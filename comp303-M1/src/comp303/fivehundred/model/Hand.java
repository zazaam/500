package comp303.fivehundred.model;

import comp303.fivehundred.engine.Events.Event;
import comp303.fivehundred.engine.IGameEventListener;
import comp303.fivehundred.gui.IDrawable;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.CardList;

import java.util.ArrayList;
import java.util.List;

/**
 * Additional services to manage a card list that corresponds to
 * the cards in a player's hand.
 */
public class Hand extends CardList implements IGameEventListener, IDrawable
{
	/**
	 * @see java.lang.Object#clone()
	 * {@inheritDoc}
	 */
	@Override
	public Hand clone()
	{
		CardList t = new CardList();
		List<Card> temp = new ArrayList<>();
		getCardList().stream()
				.forEach(card -> {
					if(!card.isJoker())
						temp.add(new Card(card.getRank(), card.getSuit()));
					else
						temp.add(new Card(card.getJokerValue()));
				});
		t.setCardList(temp);
		Hand h = new Hand();
		h.setCardList(temp);
		return h;
	}
	
	/**
	 * @param pNoTrump If the contract is in no-trump
	 * @return A list of cards that can be used to lead a trick.
	 */
	public CardList canLead(boolean pNoTrump)
	{
		return this;
	}
	
	/**
	 * @return The cards that are jokers.
	 */
	public CardList getJokers()
	{
		List<Card> temp = new ArrayList<Card>();
		getCardList().stream()
				.filter(card -> card.isJoker())
				.forEach(j -> temp.add(j));
		CardList c = new CardList();
		c.setCardList(temp);
		return c ;
	}
	
	/**
	 * @return The cards that are not jokers.
	 */
	public CardList getNonJokers()
	{
		List<Card> temp = new ArrayList<Card>();
		getCardList().stream()
				.filter(card -> !card.isJoker())
				.forEach(j -> temp.add(j));
		CardList c = new CardList();
		c.setCardList(temp);
		return c ;
	}
	
	/**
	 * Returns all the trump cards in the hand, including jokers.
	 * Takes jack swaps into account.
	 * @param pTrump The trump to check for. Cannot be null.
	 * @return All the trump cards and jokers.
	 * @pre pTrump != null
	 */
	public CardList getTrumpCards(Suit pTrump)
	{
		List<Card> temp = new ArrayList<Card>();
		getCardList().stream()
				.filter(card -> card.isJoker() || card.getEffectiveSuit(pTrump) == pTrump)
				.forEach(j -> temp.add(j));
		CardList c = new CardList();
		c.setCardList(temp);
		return c ;
	}
	
	/**
	 * Returns all the cards in the hand that are not trumps or jokers.
	 * Takes jack swaps into account.
	 * @param pTrump The trump to check for. Cannot be null.
	 * @return All the cards in the hand that are not trump cards.
	 * @pre pTrump != null
	 */
	public CardList getNonTrumpCards(Suit pTrump)
	{

		List<Card> temp = new ArrayList<Card>();
		getCardList().stream()
				.filter(card -> !card.isJoker() && card.getEffectiveSuit(pTrump) != pTrump)
				.forEach(j -> temp.add(j));
		CardList c = new CardList();
		c.setCardList(temp);
		return c ;
	}
	
	
	/**
	 * Selects the least valuable card in the hand, if pTrump is the trump.
	 * @param pTrump The trump suit. Can be null to indicate no-trump.
	 * @return The least valuable card in the hand.
	 */
	public Card selectLowest(Suit pTrump)
	{
		if (pTrump == null)
			return sort(new Card.ByRankComparator()).getFirst();
		else
			return  sort(new Card.BySuitComparator(pTrump)).getFirst();
	}
	
	/**
	 * @param pLed The suit led.
	 * @param pTrump Can be null for no-trump
	 * @return All cards that can legally be played given a lead and a trump.
	 */
	public CardList playableCards( Suit pLed, Suit pTrump )
	{
		CardList trumps = getTrumpCards(pTrump);
		CardList nonTrumps = getNonTrumpCards(pTrump);

		if(pLed != pTrump){
			List<Card> temp = new ArrayList<Card>();
			nonTrumps.getCardList().stream()
					.filter(card -> card.getSuit() == pLed)
					.forEach(j -> temp.add(j));
			if(temp.size() != 0){
				CardList c = new CardList();
				c.setCardList(temp);
				return c;
			}
			else
			{
				if(trumps.getCardList().size() != 0)
					return trumps;
				else
					return nonTrumps;
			}

		}
		else{
			if(trumps.getCardList().size() != 0)
				return trumps;
			else
				return nonTrumps;
		}

	}
	
	/**
	 * Returns the number of cards of a certain suit 
	 * in the hand, taking jack swaps into account.
	 * Excludes jokers.
	 * @param pSuit Cannot be null.
	 * @param pTrump Cannot be null.
	 * @return pSuit Can be null.
	 */
	public int numberOfCards(Suit pSuit, Suit pTrump)
	{
		if(pSuit.getConverse() == pTrump) {
            return (int) getCardList().stream()
                    .filter(card1 -> !card1.isJoker())
                    .filter(card -> card.getSuit() == pSuit && (card.getEffectiveSuit(pTrump) != pTrump))
                    .count();

        }else if(pTrump == pSuit){
            return (int) getCardList().stream()
                    .filter(card1 -> !card1.isJoker())
                    .filter(card -> card.getSuit() == pSuit || (card.getEffectiveSuit(pTrump) == pTrump))
                    .count();

		}else{
			return (int)getCardList().stream()
                    .filter(card1 -> !card1.isJoker())
					.filter(card -> card.getSuit() == pSuit)
					.count();
		}
	}

    @Override
    public void listen(Event e) {

    }

	@Override
	public void draw(int x, int y) {
		List<Card> list = getCardList();
		if(x == 0)
		{
			for(int i = 0; i < getCardList().size(); i++){
				list.get(i).draw(i*50+140, -107);//-87);
			}
		}else if(x == 1){
			for(int i = 0; i < getCardList().size(); i++){
				list.get(i).draw(0, i*-30-210);
			}
		}else if(x == 2){
			for(int i = 0; i < getCardList().size(); i++){
				list.get(i).draw(i*50+140, -600);
			}
		}else{
			for(int i = 0; i < getCardList().size(); i++){
				list.get(i).draw(738, i*-30-210);
			}
		}
	}
}
