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
    private int gridSize;
    private int numberOfExperiments;

    /**
     * Perform T independent computational experiments on an N-by-N grid
     */
    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be > 0");
        }

        gridSize = N;
        numberOfExperiments = T;

        percolationThresholds = new double[numberOfExperiments];
        for (int i = 0; i < numberOfExperiments; i++) {
            Percolation percolation = new Percolation(gridSize);
            double openSites = 0;

            while (!percolation.percolates())
            {
                int row = StdRandom.uniform(gridSize) + 1;
                int col = StdRandom.uniform(gridSize) + 1;
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    openSites++;
                }
            }

            percolationThresholds[i] = openSites / (gridSize * gridSize);
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
     * Return the lower bound of the 95% confidence interval
     */
    public double confidenceLo()
    {
        return mean() - (1.96 *  stddev() / Math.sqrt(numberOfExperiments));
    }

    /**
     * Return the upper bound of the 95% confidence interval
     */
    public double confidenceHi()
    {
       return mean() + (1.96 *  stddev() / Math.sqrt(numberOfExperiments));
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
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", "
                                                        + stats.confidenceHi());
    }
}
