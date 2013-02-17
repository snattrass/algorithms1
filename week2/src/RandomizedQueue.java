import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A randomized queue where items are enqueued at the end, but chosen at random
 * to dequeue
 *
 * @author Simon Nattrass
 */
public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] q;
    private int head = 0;
    private int tail = 0;

    /**
     * Construct an empty queue
     */
    public RandomizedQueue()
    {
        q = (Item[]) new Object[1];
    }

    /**
     * Is the queue empty?
     */
    public boolean isEmpty()
    {
        return size() == 0;
    }

    /**
     * Return ths number if items in the queue
     */
    public int size()
    {
        return tail - head;
    }

    /**
     * Add the item at the tail of queue
     */
    public void enqueue(Item item)
    {
        if (null == item) {
            throw new NullPointerException();
        }

        int numberOfItems = size();
        if (numberOfItems == q.length) {
            resize(2 * q.length);
        }

        q[tail++] = item;
    }

    /**
     * Return (and remove) a random item from the queue
     */
    public Item dequeue()
    {
        if (size() == 0) {
            throw new NoSuchElementException();
        }

        int numberOfItems = size();
        if (numberOfItems > 0 && numberOfItems == q.length / 4) {
            resize(q.length / 2);
        }

        StdRandom.shuffle(q, head, tail - 1);

        Item item = q[head];
        q[head++] = null;
        return item;
    }

    /**
     * Return (but do not remove) a random item from the queue
     */
    public Item sample()
    {
        if (size() == 0) {
            throw new NoSuchElementException();
        }

        StdRandom.shuffle(q, head, tail);
        return q[head];
    }

    /**
     * Return an independent iterator over items in random order
     */
    public Iterator<Item> iterator()
    {
        return new RandomRandomizedQueueIterator();
    }

    /**
     * Resize the array representing the queue
     */
    private void resize(int capacity)
    {
        int numberOfItems = size();
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < numberOfItems; i++) {
            copy[i] = q[i + head];
        }
        q = copy;
        head = 0;
        tail = numberOfItems;
    }

    /**
     * Iterator for the queue iterating over the elements in a random order
     */
    private class RandomRandomizedQueueIterator implements Iterator<Item>
    {
        private int[] randomIndexes;    // indices of the queue in random order
        private int index = 0;

        public RandomRandomizedQueueIterator()
        {
            // initialize the indices array
            randomIndexes = new int[size()];
            for (int i = 0; i < randomIndexes.length; i++) {
                randomIndexes[i] = i + head;
            }

            // ... and shuffle to randomize
            StdRandom.shuffle(randomIndexes);
        }

        /**
         * Do we have more items to iterate over?
         */
        public boolean hasNext()
        {
            return index < randomIndexes.length;
        }

        /**
         * Not supported
         */
        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        /**
         * Move to the next (random) item in the iteration
         */
        public Item next()
        {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return q[ randomIndexes[index++] ];
        }
    }
}
