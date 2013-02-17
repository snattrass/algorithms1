import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.junit.matchers.JUnitMatchers.everyItem;

public class DequeTest
{
    @Test
    public void testIsEmpty() throws Exception
    {
        Deque<String> deque = new Deque<String>();
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testSize() throws Exception
    {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("first");
        assertThat("size is 1", deque.size(), is(1));

        deque.addFirst("second");
        assertThat("size is 2", deque.size(), is(2));
    }

    @Test(expected = NullPointerException.class)
    public void testAddFirstWithNullThrowsException() throws Exception
    {
        Deque<String> deque = new Deque<String>();
        deque.addFirst(null);
    }

    @Test
    public void testAddFirst() throws Exception
    {
        int initialSize = 0;
        Deque<String> deque = new Deque<String>();
        deque.addFirst("first");

        assertThat("size has increased by one", deque.size(), is(++initialSize));
    }

    @Test(expected = NullPointerException.class)
    public void testAddLastFirstWithNullThrowsException() throws Exception
    {
        Deque<String> deque = new Deque<String>();
        deque.addLast(null);
    }

    @Test
    public void testAddLast() throws Exception
    {
        int initialSize = 0;
        Deque<String> deque = new Deque<String>();
        deque.addLast("last");

        assertThat("size has increased by one", deque.size(), is(++initialSize));
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveFirstFromEmptyDeque() throws Exception
    {
        Deque<String> deque = new Deque<String>();
        deque.removeFirst();
    }

    @Test
    public void testRemoveFirst() throws Exception
    {
        Deque<String> deque = new Deque<String>();
        deque.addLast("second");
        deque.addLast("third");
        deque.addFirst("first");

        assertThat("first is first", deque.removeFirst(), is("first"));
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveLastFromEmptyDeque() throws Exception
    {
        Deque<String> deque = new Deque<String>();
        deque.removeLast();
    }

    @Test
    public void testRemoveLast() throws Exception
    {
        Deque<String> deque = new Deque<String>();
        deque.addLast("second");
        deque.addLast("third");
        deque.addFirst("first");

        assertThat("third is last", deque.removeLast(), is("third"));
    }

    @Test
    public void testIterator() throws Exception
    {
        Deque<String> deque = new Deque<String>();
        deque.addLast("second number is 2");
        deque.addLast("third number is 3");
        deque.addFirst("first number is 1");

        int n = 1;
        for(String s : deque) {
            assertThat("string contains a number", s, containsString(new Integer(n++).toString()));
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemove() throws Exception
    {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("first number is 1");
        deque.iterator().remove();
    }
}
