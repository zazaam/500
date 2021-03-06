package comp303.fivehundred.model;

import comp303.fivehundred.engine.Events.Event;
import comp303.fivehundred.engine.IGameEventListener;
import comp303.fivehundred.gui.IDrawable;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.CardList;

import java.util.List;


/**
 * A card list specialized for handling cards discarded
 * as part of the play of a trick.
 */
public class Trick extends CardList implements IGameEventListener, IDrawable {

	private Bid contract;
	private Suit suitLed;
	private int trickLed;


	public Trick() {

	}

	/**
	 * Constructs a new empty trick for the specified contract.
	 *
	 * @param pContract The contract that this trick is played for.
	 */
	public Trick(Bid pContract) {
		this.contract = pContract;
	}


	/**
	 * @return Can be null for no-trump.
	 */
	public Suit getTrumpSuit() {
		return contract.getSuit();
	}


	/**
	 * @return The effective suit led.
	 */
	public Suit getSuitLed() {
		if (getFirst().isJoker() || getFirst().getEffectiveSuit(getTrumpSuit()) == getTrumpSuit())
			return getTrumpSuit();
		return getFirst().getSuit();
	}

	/**
	 * @return True if a joker led this trick
	 */
	public boolean jokerLed() {
		return getFirst().isJoker();
	}

	/**
	 * @return The card that led this trick
	 * @pre size() > 0
	 */
	public Card cardLed() {
		return getFirst();
	}

	/**
	 * @return Highest card that actually follows suit (or trumps it).
	 * I.e., the card currently winning the trick.
	 * @pre size() > 0
	 */
	public Card highest() {
		if (getTrumpSuit() != null) {
			return sort(new Card.BySuitComparator(getTrumpSuit())).getLast();
		} else
			return sort(new Card.ByRankComparator()).getLast();
	}

	/**
	 * @return The index of the card that wins the trick.
	 */
	public int winnerIndex() {
		return getCardList().indexOf(highest());
	}

	@Override
	public void listen(Event e) {

	}


	@Override
	public void draw(int x, int y) {
		List<Card> list = getCardList();
		list.get(x).removeListeners();
		if (x == 0) {

				list.get(x).draw(360, -290);//-87);
		} else if (x == 1) {

				list.get(x).draw(300,- 350);

		} else if (x == 2) {

				list.get(x).draw(360, -430);

		} else {

				list.get(x).draw(420, -350);

		}
	}
}
