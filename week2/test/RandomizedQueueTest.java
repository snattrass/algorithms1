import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class RandomizedQueueTest
{
    @Test
    public void testIsEmpty() throws Exception
    {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testSize() throws Exception
    {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("first");
        assertThat("size is 1", queue.size(), is(1));

        queue.enqueue("second");
        assertThat("size is 2", queue.size(), is(2));

        queue.enqueue("third");
        assertThat("size is 3", queue.size(), is(3));

        queue.dequeue();
        assertThat("size is 2", queue.size(), is(2));

        queue.dequeue();
        assertThat("size is 1", queue.size(), is(1));

        queue.dequeue();
        assertThat("size is 0", queue.size(), is(0));
    }

    @Test(expected = NullPointerException.class)
    public void testEnqueueWithNullThrowsException() throws Exception
    {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue(null);
    }

    @Test
    public void testEnqueue() throws Exception
    {
        int initialSize = 0;
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("first");

        assertThat("size has increased by one", queue.size(), is(++initialSize));
    }

    @Test(expected = NoSuchElementException.class)
    public void testDequeueFromEmptyDeque() throws Exception
    {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.dequeue();
    }

    @Test
    public void testDequeueDecreasesSize() throws Exception
    {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        int randomEnqueues = StdRandom.uniform(50);
        for (int i = 0; i < randomEnqueues; i++) {
            queue.enqueue("element number " + i);
        }

        int sizeBefore = queue.size();

        int randomDequeues = StdRandom.uniform(randomEnqueues);
        for (int i = 0; i < randomDequeues; i++) {
            queue.dequeue();
        }

        int sizeAfter = queue.size();

        int expectedDifference =  randomEnqueues - randomDequeues;
        assertThat("size difference is " + expectedDifference + " after " + randomEnqueues + " enqueues followed by " + randomDequeues + " dequeues", expectedDifference, is(sizeAfter));
    }

    @Test
    public void testDequeueDoesNotOutputNull() throws Exception
    {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        int randomEnqueues = StdRandom.uniform(10);
        for (int i = 0; i < randomEnqueues; i++) {
            queue.enqueue("element number " + i);
        }

        int randomDequeues = StdRandom.uniform(randomEnqueues);
        for (int i = 0; i < randomDequeues; i++) {
            assertNotNull(queue.dequeue());
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testSampleFromEmptyDeque() throws Exception
    {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.sample();
    }

    @Test
    public void testSampleDoesNotDecreaseSize() throws Exception
    {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("first");
        queue.enqueue("second");
        queue.enqueue("third");

        int sizeBefore = queue.size();

        queue.sample();
        queue.sample();

        int sizeAfter = queue.size();

        assertTrue("size remains the same", sizeAfter == sizeBefore);
    }

    @Test
    public void testIterator() throws Exception
    {

    }
}
