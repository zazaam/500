package comp303.fivehundred.util;

/**
 * Created by Zaz on 2016-01-17.
 */

import org.junit.Test;

import static comp303.fivehundred.util.AllCards.*;
import static comp303.fivehundred.util.AllCards.aQS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestByRankComparator
{
    Card.ByRankComparator comparator = new Card.ByRankComparator();

    @Test
    public void testCompareJokers()
    {
        assertTrue(comparator.compare(aHJo, aLJo) > 0);
        assertTrue(comparator.compare(aLJo, aHJo) < 0);

    }

    @Test
    public void testCompareJokerNonJoker()
    {
        assertTrue(comparator.compare(aHJo, aAH) > 0);
        assertTrue(comparator.compare(aAH, aHJo) < 0);
    }

    @Test
    public void testCompareNonJokers()
    {
        assertTrue(comparator.compare(aAH, aQH) > 0);
        assertTrue(comparator.compare(aQS, aKS) < 0);
        assertTrue(comparator.compare(aQH, aQS) > 0);
    }
}

