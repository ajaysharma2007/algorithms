package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by ajay on 01/10/16.
 */
public class PercolationStats {

    int gridSize = 0;
    int numTrials = 0;
    Percolation percolation = null;
    double[] thresholds;


    public PercolationStats(int n, int trials) {
        validateInputs(n, trials);
        this.gridSize = n;
        this.numTrials = trials;
        thresholds = new double[trials];

        populateThresholdValues();
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        if (this.numTrials <= 1) {
            return Double.NaN;
        }

        return StdStats.stddev(this.thresholds);
    }

    public double confidenceLo() {
        return this.mean() - ((1.96 * this.stddev()) / Math.sqrt(this.numTrials));
    }

    public double confidenceHi() {
        return this.mean() + ((1.96 * this.stddev()) / Math.sqrt(this.numTrials));
    }

    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                       = " + percolationStats.mean());
        System.out.println("stddev                     = " + percolationStats.stddev());
        System.out.format("95%% confidence interval    = %s, %s%n", percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }

    private void validateInputs(int gridSize, int numTrials) {
        if (gridSize <= 0 || numTrials <= 0) {
            throw new IllegalArgumentException("Grid size and Number of trials can't be less than zero");
        }
    }

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
