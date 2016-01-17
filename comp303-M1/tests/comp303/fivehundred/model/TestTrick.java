package comp303.fivehundred.model;

/**
 * Created by Zaz on 2016-01-17.
 */

import comp303.fivehundred.util.AllCards;
import comp303.fivehundred.util.Card;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTrick
{

    Trick emptyTrick;
    Trick nonEmptyTrick;
    Trick nonTrumpTrick;
    Trick jokerLHTrick;
    Trick lowJokerFirstTrick;
    Trick highJokerFirstTrick;
    Trick firstjackTrick;

    @Before
    public void setUp() throws Exception
    {
        Bid contract = new Bid(8, Card.Suit.CLUBS);
        emptyTrick = new Trick(contract);

        nonEmptyTrick = new Trick(contract);
        nonEmptyTrick.add(AllCards.aQH);
        nonEmptyTrick.add(AllCards.a7C);

        nonTrumpTrick = new Trick(new Bid(9, null));
        nonTrumpTrick.add(AllCards.a7D);
        nonTrumpTrick.add(AllCards.aKD);
        nonTrumpTrick.add(AllCards.a6S);
        nonTrumpTrick.add(AllCards.aJH);

        jokerLHTrick = new Trick(new Bid(9, Card.Suit.DIAMONDS));
        jokerLHTrick.add(AllCards.aLJo);
        jokerLHTrick.add(AllCards.aKD);
        jokerLHTrick.add(AllCards.a7H);
        jokerLHTrick.add(AllCards.aHJo);

        lowJokerFirstTrick = new Trick(new Bid(9, Card.Suit.HEARTS));
        lowJokerFirstTrick.add(AllCards.aLJo);
        lowJokerFirstTrick.add(AllCards.aKD);
        lowJokerFirstTrick.add(AllCards.aQH);
        lowJokerFirstTrick.add(AllCards.a8H);

        highJokerFirstTrick = new Trick(new Bid(9, Card.Suit.HEARTS));
        highJokerFirstTrick.add(AllCards.aHJo);
        highJokerFirstTrick.add(AllCards.aKD);
        highJokerFirstTrick.add(AllCards.aQH);
        highJokerFirstTrick.add(AllCards.aLJo);

        firstjackTrick = new Trick(new Bid(9, Card.Suit.SPADES));
        firstjackTrick.add(AllCards.aJC);
    }

    @Test
    public void testGetTrumpSuit()
    {
        assertTrue(Card.Suit.CLUBS.equals(emptyTrick.getTrumpSuit()));
        assertTrue(Card.Suit.CLUBS.equals(nonEmptyTrick.getTrumpSuit()));
        assertTrue(nonTrumpTrick.getTrumpSuit() == null);
    }

    @Test
    public void testGetSuitLed()
    {

        assertTrue(nonEmptyTrick.getSuitLed().equals(Card.Suit.HEARTS));
        assertTrue(nonTrumpTrick.getSuitLed().equals(Card.Suit.DIAMONDS));
        assertTrue(lowJokerFirstTrick.getSuitLed().equals(Card.Suit.HEARTS));
        assertTrue(highJokerFirstTrick.getSuitLed().equals(Card.Suit.HEARTS));
        assertTrue(firstjackTrick.getSuitLed().equals(Card.Suit.SPADES));
    }

    @Test
    public void testJokerLed()
    {

        assertTrue(!nonEmptyTrick.jokerLed());
        assertTrue(!nonTrumpTrick.jokerLed());
        assertTrue(lowJokerFirstTrick.jokerLed());
        assertTrue(highJokerFirstTrick.jokerLed());
    }

    @Test
    public void testCardLed()
    {
        assertTrue(nonEmptyTrick.cardLed().equals(AllCards.aQH));
        assertTrue(nonTrumpTrick.cardLed().equals(AllCards.a7D));
        assertTrue(lowJokerFirstTrick.cardLed().equals(AllCards.aLJo));
        assertTrue(highJokerFirstTrick.cardLed().equals(AllCards.aHJo));
    }

    @Test
    public void testHighest()
    {
        assertTrue(nonEmptyTrick.highest().equals(AllCards.a7C));
        assertTrue(nonTrumpTrick.highest().equals(AllCards.aKD));
        assertEquals(jokerLHTrick.highest(),(AllCards.aHJo));
        assertTrue(jokerLHTrick.highest().equals(AllCards.aHJo));
        assertTrue(lowJokerFirstTrick.highest().equals(AllCards.aLJo));
        assertTrue(highJokerFirstTrick.highest().equals(AllCards.aHJo));
    }

    @Test
    public void testWinnerIndex()
    {

        assertTrue(nonTrumpTrick.winnerIndex() == 1);
        assertTrue(jokerLHTrick.winnerIndex() == 3);
        assertTrue(lowJokerFirstTrick.winnerIndex() == 0);
        assertTrue(highJokerFirstTrick.winnerIndex() == 0);
    }

}




