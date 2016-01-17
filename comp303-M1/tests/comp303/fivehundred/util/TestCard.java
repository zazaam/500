package comp303.fivehundred.util;

import static comp303.fivehundred.util.AllCards.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestCard
{
	@Test
	public void testToString()
	{
		assertEquals( "ACE of CLUBS", aAC.toString());
		assertEquals( "TEN of CLUBS", aTC.toString());
		assertEquals( "JACK of CLUBS", aJC.toString());
		assertEquals( "QUEEN of HEARTS", aQH.toString());
		assertEquals( "KING of SPADES", aKS.toString());
		assertEquals( "QUEEN of DIAMONDS", aQD.toString());
	}

	@Test
	public void testJokerConstruction()
	{
		assertTrue( aHJo.isJoker() );
		assertTrue( aLJo.isJoker() );
		assertTrue( aHJo.getJokerValue().equals(Card.Joker.HIGH) );
		assertTrue( aLJo.getJokerValue().equals(Card.Joker.LOW) );
	}

	@Test
	public void testGetRank()
	{
		assertEquals(Card.Rank.ACE, aAD.getRank());
	}

	@Test
	public void testGetSuit()
	{
		assertEquals(Card.Suit.DIAMONDS, aAD.getSuit());
	}

	@Test
	public void testEffectiveSuitNoTrump()
	{
		assertEquals( a7C.getEffectiveSuit(null), Card.Suit.CLUBS );
	}

	@Test
	public void testEffectiveSuitConverseJack()
	{
		assertEquals(aJS.getEffectiveSuit(Card.Suit.CLUBS), Card.Suit.CLUBS);
		assertEquals(aJC.getEffectiveSuit(Card.Suit.SPADES), Card.Suit.SPADES);
		assertEquals(aJD.getEffectiveSuit(Card.Suit.HEARTS), Card.Suit.HEARTS);
		assertEquals(aJH.getEffectiveSuit(Card.Suit.DIAMONDS), Card.Suit.DIAMONDS);
	}

	@Test
	public void testEffectiveSuitNotJack()
	{
		assertEquals(aAS.getEffectiveSuit(Card.Suit.CLUBS), Card.Suit.SPADES);
		assertEquals(aAC.getEffectiveSuit(Card.Suit.SPADES), Card.Suit.CLUBS);
		assertEquals(aAD.getEffectiveSuit(Card.Suit.HEARTS), Card.Suit.DIAMONDS);
		assertEquals(aAH.getEffectiveSuit(Card.Suit.DIAMONDS), Card.Suit.HEARTS);
	}

	@Test
	public void testEffectiveSuitRegularJack()
	{
		assertEquals(Card.Suit.CLUBS, aJC.getEffectiveSuit(Card.Suit.HEARTS));
	}

	@Test
	public void testEqualsNull()
	{
		assertFalse(aAC.equals(null));
	}

	@Test
	public void testEqualsWrongClass()
	{
		assertFalse(aAC.equals(new Object()));
	}


	@Test
	public void testEqualsTrivial()
	{
		assertTrue(aAC.equals(aAC));
		assertTrue(aHJo.equals(new Card(Card.Joker.HIGH)));
	}

	@Test
	public void testEquals()
	{
		assertFalse(aHJo.equals(aLJo));
		assertFalse(aHJo.equals(aAC));
		assertFalse(aAC.equals(aHJo));

		assertTrue(aJD.equals(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS)));
		assertFalse(aJD.equals(a4D));
		assertFalse(aJD.equals(aJC));
	}

	@Test
	public void testCompareTo()
	{
		assertTrue(aJD.compareTo(a4D) > 0);
		assertEquals(a5D.compareTo(a5D), 0);

		// Same tests as in ByRankComparator:
		assertTrue(aHJo.compareTo(aLJo) > 0);
		assertTrue(aLJo.compareTo(aHJo) < 0);


		assertTrue(aHJo.compareTo(aAH) > 0);
		assertTrue(aAH.compareTo(aHJo) < 0);

		assertTrue(aAH.compareTo(aQH) > 0);
		assertTrue(aQS.compareTo(aKS) < 0);

		///////////////
	}

	@Test
	public void testHashcodeConsistent()
	{
		assertEquals(a4C.hashCode(), a4C.hashCode());
	}
}
