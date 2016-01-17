package comp303.fivehundred.model;

/**
 * Created by Zaz on 2016-01-17.
 */

import comp303.fivehundred.util.Card;
import org.junit.Test;

import static comp303.fivehundred.util.AllCards.aKS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestBid
{
    private static final Bid aPass = new Bid();
    private static final Bid a6S = new Bid(6, Card.Suit.SPADES);
    private static final Bid a6C = new Bid(6, Card.Suit.CLUBS);
    private static final Bid a6D = new Bid(6, Card.Suit.DIAMONDS);
    private static final Bid a6H = new Bid(6, Card.Suit.HEARTS);
    private static final Bid a6N = new Bid(6,null);
    private static final Bid a7S = new Bid(7, Card.Suit.SPADES);
    private static final Bid a7C = new Bid(7, Card.Suit.CLUBS);
    private static final Bid a7D = new Bid(7, Card.Suit.DIAMONDS);
    private static final Bid a7H = new Bid(7, Card.Suit.HEARTS);
    private static final Bid a7N = new Bid(7,null);
    private static final Bid a8S = new Bid(8, Card.Suit.SPADES);
    private static final Bid a8C = new Bid(8, Card.Suit.CLUBS);
    private static final Bid a8D = new Bid(8, Card.Suit.DIAMONDS);
    private static final Bid a8H = new Bid(8, Card.Suit.HEARTS);
    private static final Bid a8N = new Bid(8,null);
    private static final Bid a9S = new Bid(9, Card.Suit.SPADES);
    private static final Bid a9C = new Bid(9, Card.Suit.CLUBS);
    private static final Bid a9D = new Bid(9, Card.Suit.DIAMONDS);
    private static final Bid a9H = new Bid(9, Card.Suit.HEARTS);
    private static final Bid a9N = new Bid(9,null);
    private static final Bid a10S = new Bid(10, Card.Suit.SPADES);
    private static final Bid a10C = new Bid(10, Card.Suit.CLUBS);
    private static final Bid a10D = new Bid(10, Card.Suit.DIAMONDS);
    private static final Bid a10H = new Bid(10, Card.Suit.HEARTS);
    private static final Bid a10N = new Bid(10,null);

    @Test
    public void testCompareTo()
    {
        assertTrue(aPass.compareTo(a6S) < 0);
        assertTrue(aPass.compareTo(a6C) < 0);
        assertTrue(aPass.compareTo(a6D) < 0);
        assertTrue(aPass.compareTo(a6H) < 0);
        assertTrue(aPass.compareTo(a6N) < 0);

        assertTrue(aPass.compareTo(aPass) == 0);

        assertTrue(a6S.compareTo(a7S) < 0);
        assertTrue(a6N.compareTo(a7S) < 0);

        assertTrue(a6H.compareTo(a6C) > 0);
    }

    @Test
    public void testCompareToSameTricksCount()
    {
        assertTrue(a6N.compareTo(a6C) > 0);
    }

    @Test
    public void testCompareToSameTricksCount2()
    {
        assertTrue(a6C.compareTo(a6N) < 0);
    }

    @Test
    public void testEqualsNull()
    {
        assertFalse(a7C.equals(null));
    }

    @Test
    public void testEqualsWrongClass()
    {
        assertFalse(a7C.equals(aKS));
    }

    @Test
    public void testEquals()
    {
        assertTrue(a7S.equals(a7S));

        assertTrue(a7S.equals(new Bid(7, Card.Suit.SPADES)));
        assertFalse(a7S.equals(a7D));
        assertFalse(a7S.equals(a9S));
        assertFalse(a7S.equals(a9C));
    }

    @Test(expected=ModelException.class)
    public void toIndexPass()
    {
        aPass.toIndex();
    }

    @Test
    public void toIndex()
    {
        assertEquals(0, a6S.toIndex());
        assertEquals(1, a6C.toIndex());
        assertEquals(2, a6D.toIndex());
        assertEquals(3, a6H.toIndex());
        assertEquals(4, a6N.toIndex());

        assertEquals(5, a7S.toIndex());
        assertEquals(6, a7C.toIndex());
        assertEquals(7, a7D.toIndex());
        assertEquals(8, a7H.toIndex());
        assertEquals(9, a7N.toIndex());

        assertEquals(10, a8S.toIndex());
        assertEquals(11, a8C.toIndex());
        assertEquals(12, a8D.toIndex());
        assertEquals(13, a8H.toIndex());
        assertEquals(14, a8N.toIndex());

        assertEquals(15, a9S.toIndex());
        assertEquals(16, a9C.toIndex());
        assertEquals(17, a9D.toIndex());
        assertEquals(18, a9H.toIndex());
        assertEquals(19, a9N.toIndex());

        assertEquals(20, a10S.toIndex());
        assertEquals(21, a10C.toIndex());
        assertEquals(22, a10D.toIndex());
        assertEquals(23, a10H.toIndex());
        assertEquals(24, a10N.toIndex());
    }

    @Test
    public void testIsPass()
    {
        assertTrue(aPass.isPass());

        assertFalse(a6S.isPass());
        assertFalse(a6C.isPass());
        assertFalse(a6D.isPass());
        assertFalse(a6H.isPass());
        assertFalse(a6N.isPass());

        assertFalse(a7S.isPass());
        assertFalse(a7C.isPass());
        assertFalse(a7D.isPass());
        assertFalse(a7H.isPass());
        assertFalse(a7N.isPass());

        assertFalse(a8S.isPass());
        assertFalse(a8C.isPass());
        assertFalse(a8D.isPass());
        assertFalse(a8H.isPass());
        assertFalse(a8N.isPass());

        assertFalse(a9S.isPass());
        assertFalse(a9C.isPass());
        assertFalse(a9D.isPass());
        assertFalse(a9H.isPass());
        assertFalse(a9N.isPass());

        assertFalse(a10S.isPass());
        assertFalse(a10C.isPass());
        assertFalse(a10D.isPass());
        assertFalse(a10H.isPass());
        assertFalse(a10N.isPass());
    }

    @Test
    public void testIsNoTrump()
    {
        assertFalse(a6S.isNoTrump());
        assertFalse(a6C.isNoTrump());
        assertFalse(a6D.isNoTrump());
        assertFalse(a6H.isNoTrump());
        assertTrue(a6N.isNoTrump());

        assertFalse(a7S.isNoTrump());
        assertFalse(a7C.isNoTrump());
        assertFalse(a7D.isNoTrump());
        assertFalse(a7H.isNoTrump());
        assertTrue(a7N.isNoTrump());

        assertFalse(a8S.isNoTrump());
        assertFalse(a8C.isNoTrump());
        assertFalse(a8D.isNoTrump());
        assertFalse(a8H.isNoTrump());
        assertTrue(a8N.isNoTrump());

        assertFalse(a9S.isNoTrump());
        assertFalse(a9C.isNoTrump());
        assertFalse(a9D.isNoTrump());
        assertFalse(a9H.isNoTrump());
        assertTrue(a9N.isNoTrump());

        assertFalse(a10S.isNoTrump());
        assertFalse(a10C.isNoTrump());
        assertFalse(a10D.isNoTrump());
        assertFalse(a10H.isNoTrump());
        assertTrue(a10N.isNoTrump());
    }

    @Test
    public void testTricksBid()
    {
        assertEquals(6, a6C.getTricksBid());
        assertEquals(7, a7C.getTricksBid());
        assertEquals(8, a8C.getTricksBid());
        assertEquals(9, a9C.getTricksBid());
        assertEquals(10, a10C.getTricksBid());
    }

    @Test
    public void testMax()
    {
        Bid[] t1 = {};
        //assertEquals(aPass, Bid.max(t1));

        Bid[] t2 = {aPass, aPass, aPass};
        assertEquals(aPass, Bid.max(t2));

        Bid[] t3 = {a6S};
        assertEquals(a6S, Bid.max(t3));

        Bid[] t4 = {a6S, a7C};
        assertEquals(a7C, Bid.max(t4));

        Bid[] t5 = {a6S, aPass, a7C, aPass};
        assertEquals(a7C, Bid.max(t5));

    }

    @Test(expected=ModelException.class)
    public void testScore()
    {
        assertEquals( 40, new Bid(6, Card.Suit.SPADES).getScore());
        assertEquals( 60, new Bid(6, Card.Suit.CLUBS).getScore());
        assertEquals( 80, new Bid(6, Card.Suit.DIAMONDS).getScore());
        assertEquals( 100, new Bid(6, Card.Suit.HEARTS).getScore());
        assertEquals( 120, new Bid(6,null).getScore());

        assertEquals( 140, new Bid(7, Card.Suit.SPADES).getScore());
        assertEquals( 160, new Bid(7, Card.Suit.CLUBS).getScore());
        assertEquals( 180, new Bid(7, Card.Suit.DIAMONDS).getScore());
        assertEquals( 200, new Bid(7, Card.Suit.HEARTS).getScore());
        assertEquals( 220, new Bid(7,null).getScore());

        assertEquals( 240, new Bid(8, Card.Suit.SPADES).getScore());
        assertEquals( 260, new Bid(8, Card.Suit.CLUBS).getScore());
        assertEquals( 280, new Bid(8, Card.Suit.DIAMONDS).getScore());
        assertEquals( 300, new Bid(8, Card.Suit.HEARTS).getScore());
        assertEquals( 320, new Bid(8,null).getScore());

        assertEquals( 340, new Bid(9, Card.Suit.SPADES).getScore());
        assertEquals( 360, new Bid(9, Card.Suit.CLUBS).getScore());
        assertEquals( 380, new Bid(9, Card.Suit.DIAMONDS).getScore());
        assertEquals( 400, new Bid(9, Card.Suit.HEARTS).getScore());
        assertEquals( 420, new Bid(9,null).getScore());

        assertEquals( 440, new Bid(10, Card.Suit.SPADES).getScore());
        assertEquals( 460, new Bid(10, Card.Suit.CLUBS).getScore());
        assertEquals( 480, new Bid(10, Card.Suit.DIAMONDS).getScore());
        assertEquals( 500, new Bid(10, Card.Suit.HEARTS).getScore());
        assertEquals( 520, new Bid(10,null).getScore());

        new Bid().getScore();
    }

    @Test(expected=ModelException.class)
    public void testPassSuit()
    {
        Bid bid = new Bid();
        bid.getSuit();
    }

    @Test
    public void testGetSuit()
    {
        assertEquals(Card.Suit.SPADES, a6S.getSuit());
        assertEquals(Card.Suit.CLUBS, a6C.getSuit());
        assertEquals(Card.Suit.DIAMONDS, a6D.getSuit());
        assertEquals(Card.Suit.HEARTS, a6H.getSuit());
        assertEquals(null, a6N.getSuit());
    }

    @Test
    public void testIndexedBid()
    {
        assertEquals(new Bid(0), a6S);
        assertEquals(new Bid(1), a6C);
        assertEquals(new Bid(2), a6D);
        assertEquals(new Bid(3), a6H);

        assertEquals(new Bid(5), a7S);
        assertEquals(new Bid(6), a7C);
        assertEquals(new Bid(7), a7D);
        assertEquals(new Bid(8), a7H);

        assertEquals(new Bid(10), a8S);
        assertEquals(new Bid(11), a8C);
        assertEquals(new Bid(12), a8D);
        assertEquals(new Bid(13), a8H);

        assertEquals(new Bid(15), a9S);
        assertEquals(new Bid(16), a9C);
        assertEquals(new Bid(17), a9D);
        assertEquals(new Bid(18), a9H);

        assertEquals(new Bid(20), a10S);
        assertEquals(new Bid(21), a10C);
        assertEquals(new Bid(22), a10D);
        assertEquals(new Bid(23), a10H);

    }

    @Test
    public void testIndexedBidNoTrump()
    {
        assertEquals(new Bid(4), a6N);
        assertEquals(new Bid(9), a7N);
        assertEquals(new Bid(14), a8N);
        assertEquals(new Bid(19), a9N);
        assertEquals(new Bid(24), a10N);
    }
}

