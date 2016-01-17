package comp303.fivehundred.model;

/**
 * Created by Zaz on 2016-01-17.
 */

import comp303.fivehundred.util.AllCards;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestHand
{
    private Hand h;
    private Hand jokerhand;
    private Hand allClubs;
    private Hand jackHand;

    @Before
    public void setUp() throws Exception
    {
        h = new Hand();

        h.add(AllCards.a7D);
        h.add(AllCards.aQD);
        h.add(AllCards.aKD);
        h.add(AllCards.a9H);
        h.add(AllCards.aTS);
        h.add(AllCards.a7S);
        h.add(AllCards.aJS);
        h.add(AllCards.aKS);
        h.add(AllCards.a6C);
        h.add(AllCards.aTC);
        h.add(AllCards.aLJo);

        jokerhand = new Hand();
        jokerhand.add(AllCards.aLJo);
        jokerhand.add(AllCards.aHJo);

        allClubs = new Hand();
        allClubs.add(AllCards.a4C);
        allClubs.add(AllCards.a9C);
        allClubs.add(AllCards.aJC);
        allClubs.add(AllCards.aHJo);

        jackHand = new Hand();
        jackHand.add(AllCards.aJS);
        jackHand.add(AllCards.aJC);

    }

    @Test
    public void testClone()
    {
        Hand hClone = h.clone();
        Assert.assertTrue(((CardList) h).toString().equals(((CardList) hClone).toString()));
    }

//	@Test
//	public void testCanLead()
//	{
//		// cards beside jokers
//
//		CardList nonJokerList = new CardList();
//		nonJokerList.add(AllCards.a7D);
//		nonJokerList.add(AllCards.aQD);
//		nonJokerList.add(AllCards.aKD);
//		nonJokerList.add(AllCards.a9H);
//		nonJokerList.add(AllCards.aTS);
//		nonJokerList.add(AllCards.aJS);
//		nonJokerList.add(AllCards.a7S);
//		nonJokerList.add(AllCards.aKS);
//		nonJokerList.add(AllCards.a6C);
//		nonJokerList.add(AllCards.aTC);
//		nonJokerList = nonJokerList.sort(new Card.ByRankComparator());
//
//		assertTrue(h.canLead().sort(new Card.ByRankComparator()).toString().equals(nonJokerList.toString()));
//
//		// only jokers left
//		CardList jokerList = new CardList();
//		jokerhand.remove(AllCards.aHJo);
//		jokerList.add(AllCards.aLJo);
//		assertTrue(jokerhand.canLead().sort(new Card.ByRankComparator()).toString().equals(jokerList.toString()));
//
//		jokerhand.add(AllCards.aHJo);
//		jokerList.add(AllCards.aHJo);
//		jokerList = jokerList.sort(new Card.ByRankComparator());
//		assertTrue(jokerhand.canLead().sort(new Card.ByRankComparator()).toString().equals(jokerList.toString()));
//	}

    @Test
    public void testGetJokers()
    {

        h.remove(AllCards.aLJo);
        assertEquals(h.getJokers().size(),0);

        CardList jokerList = new CardList();
        jokerhand.remove(AllCards.aHJo);
        jokerList.add(AllCards.aLJo);
        assertTrue(jokerhand.getJokers().sort(new Card.ByRankComparator()).toString().equals(jokerList.toString()));

        jokerhand.add(AllCards.aHJo);
        jokerList.add(AllCards.aHJo);
        jokerList = jokerList.sort(new Card.ByRankComparator());
        assertTrue(jokerhand.getJokers().sort(new Card.ByRankComparator()).toString().equals(jokerList.toString()));

        assertEquals(jackHand.getJokers().size(), 0);
    }

    @Test
    public void testGetNonJokers()
    {
        CardList nonJokerList = new CardList();
        nonJokerList.add(AllCards.a7D);
        nonJokerList.add(AllCards.aQD);
        nonJokerList.add(AllCards.aKD);
        nonJokerList.add(AllCards.a9H);
        nonJokerList.add(AllCards.aTS);
        nonJokerList.add(AllCards.aJS);
        nonJokerList.add(AllCards.a7S);
        nonJokerList.add(AllCards.aKS);
        nonJokerList.add(AllCards.a6C);
        nonJokerList.add(AllCards.aTC);
        nonJokerList = nonJokerList.sort(new Card.ByRankComparator());

        assertTrue( h.getNonJokers().sort(new Card.ByRankComparator()).toString().equals(nonJokerList.toString()));

        assertEquals( jokerhand.getNonJokers().size(), 0);

        CardList jackList = new CardList();
        jackList.add(AllCards.aJS);
        jackList.add(AllCards.aJC);
        assertTrue(jackHand.getNonJokers().toString().equals(jackList.toString()));

    }

    @Test
    public void testGetTrumpCards()
    {

        CardList clubList = new CardList();
        clubList.add(AllCards.a6C);
        clubList.add(AllCards.aTC);
        clubList.add(AllCards.aJS);
        clubList.add(AllCards.aLJo);
        clubList = clubList.sort(new Card.ByRankComparator());

        CardList diamondsList = new CardList();
        diamondsList.add(AllCards.a7D);
        diamondsList.add(AllCards.aQD);
        diamondsList.add(AllCards.aKD);
        diamondsList.add(AllCards.aLJo);
        diamondsList = diamondsList.sort(new Card.ByRankComparator());

        CardList heartsList = new CardList();
        heartsList.add(AllCards.a9H);
        heartsList.add(AllCards.aLJo);
        heartsList = heartsList.sort(new Card.ByRankComparator());

        CardList spadesList = new CardList();
        spadesList.add(AllCards.aTS);
        spadesList.add(AllCards.a7S);
        spadesList.add(AllCards.aJS);
        spadesList.add(AllCards.aKS);
        spadesList.add(AllCards.aLJo);

        spadesList = spadesList.sort(new Card.ByRankComparator());

        assertTrue(h.getTrumpCards(Card.Suit.CLUBS).sort(new Card.ByRankComparator()).toString().equals(clubList.toString()));
        assertTrue(h.getTrumpCards(Card.Suit.DIAMONDS).sort(new Card.ByRankComparator()).toString().equals(diamondsList.toString()));
        assertTrue(h.getTrumpCards(Card.Suit.HEARTS).sort(new Card.ByRankComparator()).toString().equals(heartsList.toString()));
        assertTrue(h.getTrumpCards(Card.Suit.SPADES).sort(new Card.ByRankComparator()).toString().equals(spadesList.toString()));
    }

    @Test
    public void testGetNonTrumpCards()
    {

        CardList nonClubList = new CardList();
        nonClubList.add(AllCards.a7D);
        nonClubList.add(AllCards.aQD);
        nonClubList.add(AllCards.aKD);
        nonClubList.add(AllCards.a9H);
        nonClubList.add(AllCards.aTS);
        nonClubList.add(AllCards.a7S);
        nonClubList.add(AllCards.aKS);
        nonClubList = nonClubList.sort(new Card.ByRankComparator());

        assertTrue( h.getNonTrumpCards(Card.Suit.CLUBS).sort(new Card.ByRankComparator()).toString().equals(nonClubList.toString()));


        CardList nonDiamondList = new CardList();
        nonDiamondList.add(AllCards.a6C);
        nonDiamondList.add(AllCards.aTC);
        nonDiamondList.add(AllCards.a9H);
        nonDiamondList.add(AllCards.aTS);
        nonDiamondList.add(AllCards.a7S);
        nonDiamondList.add(AllCards.aJS);
        nonDiamondList.add(AllCards.aKS);
        nonDiamondList = nonDiamondList.sort(new Card.ByRankComparator());

        assertTrue( h.getNonTrumpCards(Card.Suit.DIAMONDS).sort(new Card.ByRankComparator()).toString().equals(nonDiamondList.toString()));

        CardList nonHeartList = new CardList();
        nonHeartList.add(AllCards.a6C);
        nonHeartList.add(AllCards.aTC);
        nonHeartList.add(AllCards.a7D);
        nonHeartList.add(AllCards.aQD);
        nonHeartList.add(AllCards.aKD);
        nonHeartList.add(AllCards.aTS);
        nonHeartList.add(AllCards.a7S);
        nonHeartList.add(AllCards.aJS);
        nonHeartList.add(AllCards.aKS);
        nonHeartList = nonHeartList.sort(new Card.ByRankComparator());

        assertTrue( h.getNonTrumpCards(Card.Suit.HEARTS).sort(new Card.ByRankComparator()).toString().equals(nonHeartList.toString()));

        CardList nonSpadesList = new CardList();
        nonSpadesList.add(AllCards.a6C);
        nonSpadesList.add(AllCards.aTC);
        nonSpadesList.add(AllCards.a7D);
        nonSpadesList.add(AllCards.aQD);
        nonSpadesList.add(AllCards.aKD);
        nonSpadesList.add(AllCards.a9H);
        nonSpadesList = nonSpadesList.sort(new Card.ByRankComparator());

        assertTrue( h.getNonTrumpCards(Card.Suit.SPADES).sort(new Card.ByRankComparator()).toString().equals(nonSpadesList.toString()));

        assertEquals(jackHand.getNonTrumpCards(Card.Suit.SPADES).size(), 0);
        assertEquals(jackHand.getNonTrumpCards(Card.Suit.CLUBS).size(), 0);
    }

    @Test
    public void testSelectLowest()
    {
        // no-trump
        assertTrue(h.selectLowest(null).equals(AllCards.a6C));

        //trump
        assertTrue(h.selectLowest(Card.Suit.CLUBS).equals(AllCards.a7S));
        assertEquals(h.selectLowest(Card.Suit.DIAMONDS),(AllCards.a6C));
        assertTrue(h.selectLowest(Card.Suit.DIAMONDS).equals(AllCards.a6C));
        assertTrue(h.selectLowest(Card.Suit.HEARTS).equals(AllCards.a6C));
        assertTrue(h.selectLowest(Card.Suit.SPADES).equals(AllCards.a6C));

        assertTrue(jokerhand.selectLowest(Card.Suit.SPADES).equals(AllCards.aLJo));

        assertTrue(allClubs.selectLowest(Card.Suit.CLUBS).equals(AllCards.a4C));
    }

    @Test
    public void testPlayableCards()
    {
//		// no-Trump not specified

        CardList clubList = new CardList();
        clubList.add(AllCards.a6C);
        clubList.add(AllCards.aTC);
//		assertTrue( h.playableCards(Suit.CLUBS, null).toString().equals(clubList.toString()));

        CardList diamondsList = new CardList();
        diamondsList.add(AllCards.a7D);
        diamondsList.add(AllCards.aQD);
        diamondsList.add(AllCards.aKD);
//		assertTrue( h.playableCards(Suit.DIAMONDS, null).toString().equals(diamondsList.toString()));

        CardList heartsList = new CardList();
        heartsList.add(AllCards.a9H);
//		assertTrue( h.playableCards(Suit.HEARTS, null).toString().equals(heartsList.toString()));

        CardList spadesList = new CardList();
        spadesList.add(AllCards.aTS);
        spadesList.add(AllCards.a7S);
        spadesList.add(AllCards.aJS);
        spadesList.add(AllCards.aKS);
//		assertTrue( h.playableCards(Suit.SPADES, null).toString().equals(spadesList.toString()));

        CardList joker = new CardList();
        joker.add(AllCards.aLJo);
        joker.add(AllCards.aHJo);
//		assertTrue(jokerhand.playableCards(Suit.DIAMONDS, null).sort(new Card.ByRankComparator()).toString().equals(joker.toString()));


        // Trump
        clubList.add(AllCards.aJS);
        clubList.add(AllCards.aLJo);
        clubList = clubList.sort(new Card.ByRankComparator());
        assertTrue( h.playableCards(Card.Suit.CLUBS, Card.Suit.CLUBS).sort(new Card.ByRankComparator()).toString().equals(clubList.toString()));

        diamondsList.add(AllCards.aLJo);
        diamondsList = diamondsList.sort(new Card.ByRankComparator());
        assertTrue( h.playableCards(Card.Suit.DIAMONDS, Card.Suit.DIAMONDS).sort(new Card.ByRankComparator()).toString().equals(diamondsList.toString()));

        heartsList = heartsList.sort(new Card.ByRankComparator());
        assertTrue( h.playableCards(Card.Suit.HEARTS, Card.Suit.DIAMONDS).sort(new Card.ByRankComparator()).toString().equals(heartsList.toString()));

        spadesList.remove(AllCards.aJS);
        spadesList = spadesList.sort(new Card.ByRankComparator());
        assertTrue( h.playableCards(Card.Suit.SPADES, Card.Suit.CLUBS).sort(new Card.ByRankComparator()).toString().equals(spadesList.toString()));

        // no Trump in hand
        assertTrue(jokerhand.playableCards(Card.Suit.DIAMONDS, Card.Suit.DIAMONDS).sort(new Card.ByRankComparator()).toString().equals(joker.toString()));
        assertTrue(jokerhand.playableCards(Card.Suit.DIAMONDS, Card.Suit.CLUBS).sort(new Card.ByRankComparator()).toString().equals(joker.toString()));

        CardList jackList = new CardList();
        jackList.add(AllCards.aJS);
        jackList.add(AllCards.aJC);
        assertTrue(jackHand.playableCards(Card.Suit.CLUBS, Card.Suit.SPADES).sort(new Card.ByRankComparator()).toString().equals(jackList.toString()));

    }

    @Test
    public void testNumberOfCards()
    {
        // Trump

        assertTrue(h.numberOfCards(Card.Suit.CLUBS, Card.Suit.CLUBS) == 3); // +1 for jack of spades
        assertTrue(h.numberOfCards(Card.Suit.DIAMONDS, Card.Suit.DIAMONDS) == 3);
        assertEquals(h.numberOfCards(Card.Suit.HEARTS, Card.Suit.DIAMONDS), 1);
        assertTrue(h.numberOfCards(Card.Suit.HEARTS, Card.Suit.DIAMONDS) == 1);
        assertTrue(h.numberOfCards(Card.Suit.SPADES, Card.Suit.CLUBS) == 3); //-1 for jack of spades

    }


}

