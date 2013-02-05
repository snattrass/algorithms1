/**
 * Test client for Percolation using the Monte Carlo simulation to estimate the
 * percolation threshold of a N-by-N grid.
 * Usage: PercolationStats grid-size(N) number-of-percolations(T)
 *
 * @author Simon Nattrass
 */
public class PercolationStats
{
    private double[] percolationThresholds;

    /**
     * Perform T independent computational experiments on an N-by-N grid
     */
    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be > 0");
        }

        percolationThresholds = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);
            double openSites = 0;

            while (!percolation.percolates())
            {
                int row = StdRandom.uniform(N) + 1;
                int col = StdRandom.uniform(N) + 1;
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    openSites++;
                }
            }

            percolationThresholds[i] = openSites / (N * N);
        }
    }

    /**
     * Return the sample mean of percolation threshold
     */
    public double mean()
    {
        return StdStats.mean(percolationThresholds);
    }

    /**
     * Return the sample standard deviation of percolation threshold
     */
    public double stddev()
    {
        return StdStats.stddev(percolationThresholds);
    }

    /**
     * Main entry point for the simulation.
     */
    public static void main(String[] args)
    {
        int N = new Integer(args[0]).intValue();
        int T = new Integer(args[1]).intValue();
        PercolationStats stats = new PercolationStats(N, T);

        System.out.println("Mean                    = " + stats.mean());
        System.out.println("Stddev                  = " + stats.stddev());

        double from = stats.mean() - (1.96 *  stats.stddev() / Math.sqrt(T));
        double to = stats.mean() + (1.96 *  stats.stddev() / Math.sqrt(T));
        System.out.println("95% confidence interval = " + from + ", " + to);
    }
}
