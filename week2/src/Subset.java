/**
 * Test Client for RandomizeQueue which reads in a sequence of N strings from
 * standard input and prints out exactly k of them, uniformly at random.
 *
 *  @author Simon Nattrass
 */
public class Subset
{
    public static void main(String[] args)
    {
        RandomizedQueue<String> q = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }

        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}
