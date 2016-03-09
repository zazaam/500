package comp303.fivehundred.util;

import comp303.fivehundred.gui.IDrawable;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Comparator;

/**
 * An immutable description of a playing card.
 */
public final class Card implements Comparable<Card>, IDrawable
{


	/**
	 * Represents the rank of the card.
	 */
	public enum Rank 
	{ FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE }
	
	/**
	 * Represents the suit of the card.
	 */
	public enum Suit 
	{ SPADES, CLUBS, DIAMONDS, HEARTS; 
		
		/**
		 * @return the other suit of the same color.
		 */
		public Suit getConverse()
		{
			Suit lReturn;
			switch(this)
			{
				case SPADES: lReturn = CLUBS; break;
				case CLUBS:  lReturn = SPADES; break;
				case DIAMONDS: lReturn = HEARTS; break;
				case HEARTS: lReturn = DIAMONDS; break;
				default: lReturn = this;
			}
			return lReturn;
		}
	}
	
	/**
	 * Represents the value of the card, if the card is a joker.
	 */
	public enum Joker
	{ LOW, HIGH }
	
	// If this field is null, it means the card is a joker, and vice-versa.
	private final Rank aRank;

	// If this field is null, it means the card is a joker, and vice-versa.
	private final Suit aSuit;
	
	// If this field is null, it means the card is not a joker, and vice-versa.
	private final Joker aJoker;

	private ImageView cardImage = null;

	public ImageView backImage = new ImageView(new Image("/images/b.gif", 0, 100, false, false));

	public boolean isSelected() {
		return isSelected;
	}

	private boolean isSelected = false;

	private EventHandler<MouseEvent> handler = (MouseEvent event) -> {
		if(MouseEvent.MOUSE_ENTERED == event.getEventType()){
			if(!isSelected){
				cardImage.setEffect(new DropShadow());
			}
		}
		else if(MouseEvent.MOUSE_CLICKED == event.getEventType()){
			if(!isSelected){
				DropShadow d = new DropShadow();
				d.setColor(Color.CHOCOLATE);
				cardImage.setEffect(d);
				isSelected = !isSelected;
			}else{
				cardImage.setEffect(null);
				isSelected = !isSelected;
			}
		}
		else if(MouseEvent.MOUSE_EXITED == event.getEventType()){
			if(!isSelected){
				cardImage.setEffect(null);
			}
		}
	};

	/**
	 * Create a new card object that is not a joker. 
	 * @param pRank The rank of the card.
	 * @param pSuit The suit of the card.
	 * @pre pRank != null
	 * @pre pSuit != null
	 */
	public Card( Rank pRank, Suit pSuit )
	{
		assert pRank != null;
		assert pSuit != null;
		aRank = pRank;
		aSuit = pSuit;
		aJoker = null;
		setImage();
		setListeners();
	}




	/**
	 * Creates a new joker card.
	 * @param pValue Whether this is the low or high joker.
	 * @pre pValue != null
	 */
	public Card( Joker pValue )
	{
		assert pValue != null;
		aRank = null;
		aSuit = null;
		aJoker = pValue;
		setImage();
		setListeners();
	}

	private void setListeners() {
		cardImage.addEventHandler(MouseEvent.MOUSE_ENTERED, handler);
        cardImage.addEventHandler(MouseEvent.MOUSE_EXITED, handler);
		cardImage.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
	}

	public void removeListeners(){
		cardImage.setEffect(null);
		cardImage.removeEventHandler(MouseEvent.MOUSE_ENTERED, handler);
		cardImage.addEventHandler(MouseEvent.MOUSE_EXITED, handler);
		cardImage.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
	}

	private void setImage() {
		cardImage = new ImageView(new Image("/images/"+toShortString()+".gif", 0, 100, false, false));
	}
	
	/**
	 * @return True if this Card is a joker, false otherwise.
	 */
	public boolean isJoker()
	{
		return aJoker != null;
	}
	
	/**
	 * @return Whether this is the High or Low joker.
	 */
	public Joker getJokerValue()
	{
		assert isJoker();
		return aJoker;
	}
	
	/**
	 * Obtain the rank of the card.
	 * @return An object representing the rank of the card. Can be null if the card is a joker.
	 * @pre !isJoker();
	 */
	public Rank getRank()
	{
		assert !isJoker();
		return aRank;
	}
	
	/**
	 * Obtain the suit of the card.
	 * @return An object representing the suit of the card
	 * @pre !isJoker();
	 */
	public Suit getSuit()
	{
		assert !isJoker();
		return aSuit;
	}
	
	/**
	 * Returns the actual suit of the card if pTrump is the
	 * trump suit. Takes care of the suit swapping of jacks.
	 * @param pTrump The current trump. Null if no trump.
	 * @return The suit of the card, except if the card is a Jack
	 * and its converse suit is trump. Then, returns the trump.
	 */
	public Suit getEffectiveSuit( Suit pTrump )
	{
		if( pTrump == null )
		{
			return aSuit;
		}
		else if( aRank == Rank.JACK && aSuit == pTrump.getConverse())
		{
			return pTrump;
		}
		else
		{
			return aSuit;
		}
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * @return See above.
	 */
	public String toString()
	{
		if( !isJoker() )
		{
			return aRank + " of " + aSuit;
		}
		else
		{
			return aJoker + " " + "Joker";
		}
	}
	
	/**
	 * @return A short textual representation of the card
	 */
	public String toShortString()
	{
		String lReturn = "";
		if( isJoker() )
		{
			lReturn = aJoker.toString().charAt(0) + "J";
		}
		else
		{
			if( aRank.ordinal() <= Rank.NINE.ordinal() )
			{
				lReturn += new Integer(aRank.ordinal() + 4).toString();
			}
			else
			{
				lReturn += aRank.toString().charAt(0);
			}
			lReturn += aSuit.toString().charAt(0);
		}
		return lReturn;
	}

	/**
	 * Compares two cards according to their rank.
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * @param pCard The card to compare to
	 * @return Returns a negative integer, zero, or a positive integer 
	 * as this object is less than, equal to, or greater than pCard
	 */
	public int compareTo(Card pCard)
	{
        if(isJoker()){
            if(pCard.isJoker()){
                if(pCard.getJokerValue() == Joker.LOW)
                    return 1;
                else
                    return -1;
            }
            return 1;
        }

        if(pCard.isJoker())
            return -1;

        int pCardOrd = pCard.getRank().ordinal(), thisOrd = getRank().ordinal();

		if(pCardOrd < thisOrd){
            return 1;
        }else if (pCardOrd > thisOrd){
            return -1;
        }else {
            return 0;
        }
	}

	/**
	 * Two cards are equal if they have the same suit and rank or if they
	 * are two jokers of the same value.
	 * @param pCard The card to test.
	 * @return true if the two cards are equal
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object pCard )
	{
		if(pCard == null) return false;
        if(this == pCard) return true;
        if(!(pCard instanceof Card)) return false;
        Card card = (Card)pCard;
        if(card.isJoker()){
            if(this.isJoker())
                return card.getJokerValue() == this.getJokerValue();
            return false;
        }
        if(isJoker()){
            if(card.isJoker())
                return card.getJokerValue() == this.getJokerValue();
            return false;
        }
        return card.getRank() == this.getRank()
                && card.getSuit() == this.getSuit();

	}

	/**
	 * The hashcode for a card is the suit*number of ranks + that of the rank (perfect hash).
	 * @return the hashcode
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return getRank().ordinal()*getSuit().ordinal() + getRank().ordinal();
	}
	
	/**
	 * Compares cards using their rank as primary key, then suit. Jacks
	 * rank between 10 and queens of their suit.
	 */
	public static class ByRankComparator implements Comparator<Card>
	{
		@Override
		public int compare(Card pCard1, Card pCard2)
		{
            if(pCard1.compareTo(pCard2) < 0)
                return -1;
            if(pCard1.compareTo(pCard2) > 0)
                return 1;
            else
                return pCard1.getSuit().compareTo(pCard2.getSuit());

		}
		
	}
	
	/**
	 * Compares cards using their suit as primary key, then rank. Jacks
	 * rank between 10 and queens of their suit.
	 */
	public static class BySuitNoTrumpComparator implements Comparator<Card>
	{
		@Override
		public int compare(Card pCard1, Card pCard2)
		{

            if(pCard1.isJoker() || pCard2.isJoker())
                return pCard1.compareTo(pCard2);

            if(pCard1.getSuit().compareTo(pCard2.getSuit()) < 0)
                return -1;
            if(pCard1.getSuit().compareTo(pCard2.getSuit()) > 0)
                return 1;
            else
                return pCard1.compareTo(pCard2);
		}
	}
	
	/**
	 * Compares cards using their suit as primary key, then rank. Jacks
	 * rank above aces if they are in the trump suit. The trump suit becomes the
	 * highest suit.
	 */
	public static class BySuitComparator implements Comparator<Card>
	{
        private Suit temp;
        public BySuitComparator(Suit suit){
            this.temp = suit;
        }

		@Override
		public int compare(Card pCard1, Card pCard2)
		{
            if(pCard1.isJoker() || pCard2.isJoker())
                return pCard1.compareTo(pCard2);

            if(temp != null){
                Suit suit1 = pCard1.getEffectiveSuit(temp), suit2 = pCard2.getEffectiveSuit(temp);

                if(pCard1.getSuit() == temp){
                    if(pCard2.getSuit() == temp) {
                        pCard1.compareTo(pCard2);
                    }else
                        return 1;
                }else{
                    if(pCard2.getSuit() == temp)
                        return -1;
                    else if(suit1 == temp){
                        if(suit2 == temp){
                            if(pCard1.getRank() == Rank.JACK)
                                return 1;
                            else if(pCard2.getRank() == Rank.JACK)
                                return -1;
                            else
                                return pCard1.compareTo(pCard2);
                        }
                        else
                            return 1;

                    }else{
                        if(suit2 == temp){
                            return -1;
                        }
                        else
                            return new ByRankComparator().compare(pCard1, pCard2);
                    }

                }
            }
            return new BySuitNoTrumpComparator().compare(pCard1, pCard2);
		}
	}

	@Override
	public void draw(int x, int y) {
		cardImage.setX(x);
		cardImage.setY(y);
		backImage.setX(x);
		backImage.setY(y);
	}

	public ImageView getImage(boolean isFront)
	{
		return isFront ? cardImage : backImage;
	}
}
