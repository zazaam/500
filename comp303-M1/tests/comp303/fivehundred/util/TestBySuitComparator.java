package comp303.fivehundred.util;

/**
 * Created by Zaz on 2016-01-17.
 */

import org.junit.Test;

import static comp303.fivehundred.util.AllCards.*;
import static comp303.fivehundred.util.AllCards.aAC;
import static comp303.fivehundred.util.AllCards.aJC;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


public class TestBySuitComparator
{

    private Card.BySuitComparator comparator = new Card.BySuitComparator(Card.Suit.DIAMONDS);
    private Card.BySuitComparator comparatorNoTrump = new Card.BySuitComparator(null);

    @Test
    public void testNoTrumpJokers()
    {
        assertNotSame(0, comparatorNoTrump.compare(aHJo, a5D));
        assertNotSame(0, comparatorNoTrump.compare(a5D, aHJo));
    }

    @Test
    public void testNoTrumpRegularCards()
    {
        assertTrue(comparatorNoTrump.compare(a5C, a5D)<0);
    }

    @Test
    public void testTrumps()
    {
        Card.BySuitComparator comparator = new Card.BySuitComparator(Card.Suit.DIAMONDS);

        assertTrue(comparator.compare(a4D, aAH) > 0 );
        assertTrue(comparator.compare(a4H, a4D) < 0);
        assertTrue(comparator.compare(a4S, a4C) < 0);
        assertTrue(comparator.compare(a4D, a5D) < 0);
    }

    @Test
    public void testNonTrumps()
    {
        assertSame(0, comparator.compare(a4C, a4C));
        assertTrue(comparator.compare(a4C, a7C) < 0);
    }

    @Test
    public void testJokersVsTrump()
    {
        assertTrue(comparator.compare(aHJo, a4D) > 0);
        assertTrue(comparator.compare(a4D, aHJo) < 0);
    }

    @Test
    public void testHighBower()
    {
        assertTrue(comparator.compare(aJD, a4C) > 0);
        assertTrue(comparator.compare(a4C, aJD) < 0);
    }

    @Test
    public void testLowBower()
    {
        Card.BySuitComparator comparator = new Card.BySuitComparator(Card.Suit.DIAMONDS);
        assertTrue(comparator.compare(aJH, aAH) > 0 );
        assertTrue(comparator.compare(aAH, aJH) < 0 );
    }

    @Test
    public void testNonBowerJacks()
    {
        assertTrue(comparator.compare(aJC, aAC) < 0 );
        assertTrue(comparator.compare(aAC, aJC) > 0 );
    }
}
