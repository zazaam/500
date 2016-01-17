package comp303.fivehundred.util;

import org.junit.Test;

import static comp303.fivehundred.util.AllCards.a4C;
import static comp303.fivehundred.util.AllCards.a4D;
import static comp303.fivehundred.util.AllCards.a6D;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class TestCardList
{

    @Test(expected=GameUtilException.class)
    public void testEmptyCardListGetFirst()
    {
        CardList lList = new CardList();
        lList.getFirst();
    }

    @Test(expected=GameUtilException.class)
    public void testEmptyCardListGetLast()
    {
        CardList lList = new CardList();
        lList.getLast();
    }

    @Test
    public void testGetFirstAndLast()
    {
        CardList lList = new CardList();
        lList.add(a4D);
        lList.add(a6D);
        lList.sort(new Card.ByRankComparator());
        assertEquals(a4D, lList.getFirst());
        assertEquals(a6D, lList.getLast());
    }

    @Test
    public void testAdd()
    {
        CardList lList = new CardList();
        lList.add(a4C);
        assertEquals(1, lList.size());
        assertTrue(lList.getFirst().equals(a4C));
    }

    @Test
    public void testRemove()
    {
        CardList lList = new CardList();
        lList.add(a4C);
        lList.remove(a4C);
        assertTrue(lList.size() == 0);
    }

    @Test
    public void testDoubleAdd()
    {
        CardList lCards = new CardList();
        lCards.add(a4C);
        lCards.add(a4C);
        assertEquals(1, lCards.size());

    }

    @Test
    public void testCloneDistinct()
    {
        CardList list = new CardList();
        CardList clone = list.clone();
        assertTrue( list != clone );
    }

    @Test
    public void testSort()
    {
        CardList lList = new CardList();

        lList.add(a4D);
        lList.add(a6D);

        CardList aList = lList.sort(new Card.ByRankComparator());
        assertNotSame(lList, aList);
        assertEquals(a4D, aList.getFirst());
        assertEquals(a4D, lList.getFirst());
    }
}

