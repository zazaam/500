package comp303.fivehundred.util;

import java.util.*;

/**
 * A mutable list of cards. Does not support duplicates.
 * The cards are maintained in the order added.
 */
public class CardList implements Iterable<Card>, Cloneable
{
    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    List<Card> cardList;
	/**
	 * Creates a new, empty card list.
	 */
	public CardList()
	{
        cardList = new ArrayList<>();
	}
	
	/**
	 * Adds a card if it is not
	 * already in the list. Has no effect if the card
	 * is already in the list.
	 * @param pCard The card to add.
	 * @pre pCard != null
	 */
	public void add(Card pCard)
	{
        if(!cardList.stream().anyMatch(card -> card.equals(pCard)))
            cardList.add(pCard);
	}
	
	/**
	 * @return The number of cards in the list.
	 */
	public int size()
	{
		return cardList.size();
	}
	
	/**
	 * @return The first card in the list, according to whatever
	 * order is currently being used. 
	 * @throws GameUtilException if the list is empty.
	 */
	public Card getFirst() throws GameUtilException
	{
        if(cardList.size() < 1)
            throw new GameUtilException("CardList is empty\n");
        return cardList.get(0);
	}
	
	/**
	 * @return The last card in the list, according to whatever
	 * order is currently being used. 
	 * @throws GameUtilException If the list is empty.
	 */
	public Card getLast() throws GameUtilException
	{
        if(cardList.size() < 1)
            throw new GameUtilException("CardList is empty\n");
		return cardList.get(cardList.size()-1);
	}
	
	/**
	 * Removes a card from the list. Has no effect if the card is
	 * not in the list.
	 * @param pCard The card to remove. 
	 * @pre pCard != null;
	 */
	public void remove(Card pCard)
	{
        cardList.remove(pCard);
	}
	
	/**
	 * @see java.lang.Object#clone()
	 * {@inheritDoc}
	 */
    @Override
	public CardList clone()
	{
        CardList t = new CardList();
        List<Card> temp = new ArrayList<>();
		cardList.stream()
            .forEach(card -> {
                if(!card.isJoker())
				    temp.add(new Card(card.getRank(), card.getSuit()));
                else
                    temp.add(new Card(card.getJokerValue()));
            });
        t.setCardList(temp);
        return t;
	}
	
	/**
	 * @see java.lang.Iterable#iterator()
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Card> iterator()
	{
		Iterator<Card> it = new Iterator<Card>() {

            int position = 0;

            @Override
            public boolean hasNext() {
                return position+1 <= cardList.size()-1;
            }

            @Override
            public Card next() {
                return cardList.get(++position);
            }
        };
        return it;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * {@inheritDoc}
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
        sb.append("This deck contains " + cardList.size() + " cards. Here they are:\n");
        for(Card card : cardList){
            sb.append(card + " ");
        }
        return sb.toString();
	}
	
	/**
	 * @pre aCards.size() > 0
	 * @return A randomly-chosen card from the set. 
	 */
	public Card random()
	{
        Random r = new Random(System.currentTimeMillis());
		return cardList.get(r.nextInt(cardList.size()));
	}
	
	/**
	 * Returns another CardList, sorted as desired. This
	 * method has no side effects.
	 * @param pComparator Defines the sorting order.
	 * @return the sorted CardList
	 * @pre pComparator != null
	 */
	public CardList sort(Comparator<Card> pComparator)
	{
        CardList l = clone();
        Collections.sort(l.getCardList(), pComparator);
        return l;
	}
}
