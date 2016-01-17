package comp303.fivehundred.util;

/**
 * Created by Zaz on 2016-01-17.
 */

import org.junit.Test;

import java.util.Comparator;

import static comp303.fivehundred.util.AllCards.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestBySuitNoTrumpComparator
{

    Comparator<Card> comparator = new Card.BySuitNoTrumpComparator();

    @Test
    public void testCompareIdenticalCards()
    {
        assertEquals( comparator.compare(a4D, a4D), 0);
        assertTrue( comparator.compare(aHJo, aLJo) > 0);
        assertTrue( comparator.compare(aLJo, aHJo) < 0);

        assertTrue( comparator.compare(aHJo, aAH) > 0);
        assertTrue( comparator.compare(aAH, aHJo) < 0);

        assertTrue( comparator.compare(aAD, aAS) > 0);
        assertTrue( comparator.compare(aKD, aQD) > 0);
    }
}

