import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * The {@code PercolationStats} class represents a
 * <em>PercolationStats data type</em>.
 * It supports the <em>mean</em>, <em>stddev</em>, <em>confidenceLo</em>
 * operation and a <em>confidenceHi</em> operation.
 * <ul>
 * <li><em>mean</em> returns the mean of percolation thresholds.
 * Percolation threshold is calculated using the ratio of number of
 * open sites required for a system to percolate to the
 * total number of sites in the grid {@code n^2}.
 * This is performed {@code t} times and then an average of
 * all the percolation thresholds is taken and returned.
 * <li><em>stddev</em> returns the standard deviation of percolation thresholds.
 * <li><em>confidenceLo</em> returns the low endpoint of 95% confidence interval.
 * <li><em>confidenceHi</em>() returns the high endpoint of 95% confidence interval.
 * </ul>
 *
 * @author Ajay Sharma
 */

public class PercolationStats {

    // This stores the size of the grid passed as input.
    private int gridSize = 0;
    // This stores the number of times the experiment needs to be performed.
    private int numTrials = 0;
    // This is the percolation data type used for percolation operations.
    private Percolation percolation = null;
    // This stores the percolation thresholds for each trial.
    private double[] thresholds;

    /**
     * Initializes an empty PercolationStats data structure with gridSize
     * {@code n}, number of trials to {@code numTrials}.
     * <p>
     *
     * @param n      the size representing the n*n grid.
     * @param trials representing the number of times
     *               experiment needs to be performed.
     * @throws IllegalArgumentException if {@code n < 0} {@code trials < 0}
     *                                  </p>
     */
    public PercolationStats(int n, int trials) {
        validateInputs(n, trials);
        this.gridSize = n;
        this.numTrials = trials;
        thresholds = new double[trials];

        populateThresholdValues();
    }

    /**
     * Returns the mean of percolation thresholds
     * for {@code n*n} grid calculated {@code numTrials} times.
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /**
     * Returns the standard deviation of percolation thresholds
     * for {@code n*n} grid calculated {@code numTrials} times.
     */
    public double stddev() {
        if (this.numTrials <= 1) {
            return Double.NaN;
        }

        return StdStats.stddev(this.thresholds);
    }

    /**
     * Returns the low  endpoint of 95% confidence interval
     * for {@code n*n} grid calculated {@code numTrials} times.
     */
    public double confidenceLo() {
        return this.mean() - ((1.96 * this.stddev()) / Math.sqrt(this.numTrials));
    }

    /**
     * Returns the high  endpoint of 95% confidence interval
     * for {@code n*n} grid calculated {@code numTrials} times.
     */
    public double confidenceHi() {
        return this.mean() + ((1.96 * this.stddev()) / Math.sqrt(this.numTrials));
    }

    public static void main(String[] args) {
        PercolationStats percolationStats =
                new PercolationStats(Integer.parseInt(args[0]),
                        Integer.parseInt(args[1]));
        System.out.println("mean                       " +
                "= " + percolationStats.mean());
        System.out.println("stddev                     " +
                "= " + percolationStats.stddev());
        System.out.format("95%% confidence interval    " +
                        "= %s, %s%n", percolationStats.confidenceLo(),
                percolationStats.confidenceHi());
    }

    /*
        Validates the inputs provided to the PercolationStats object.
     */
    private void validateInputs(int size, int trials) {
        if (size <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "Grid size and Number of trials can't be less than zero");
        }
    }

    /*
       Initializes the Percolation object and populates
       the threshold array for n*n grid, numTrials number of times.
    */
    private void populateThresholdValues() {
        for (int i = 1; i <= this.numTrials; i++) {

            this.percolation = new Percolation(this.gridSize);
            int openSites = 0;
            while (!this.percolation.percolates()) {

                int x = StdRandom.uniform(1, this.gridSize + 1);
                int y = StdRandom.uniform(1, this.gridSize + 1);

                if (!this.percolation.isOpen(x, y)) {
                    this.percolation.open(x, y);
                    openSites++;
                }
            }

            double threshold = openSites / Math.pow(this.gridSize, 2);
            thresholds[i - 1] = threshold;
        }
    }
}
