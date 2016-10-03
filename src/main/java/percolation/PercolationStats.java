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


    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        validateInputs(n, trials);
        this.gridSize = n;
        this.numTrials = trials;
        this.percolation = new Percolation(n);
        thresholds = new double[trials];
    }

    // sample mean of percolation threshold
    public double mean() {
        for (int i = 1; i <= this.numTrials; i++) {

            int openSites = 0;
            while (this.percolation.percolates()) {

                int x = StdRandom.uniform(1, this.gridSize + 1);
                int y = StdRandom.uniform(1, this.gridSize + 1);

                if (!this.percolation.isOpen(x, y)) {
                    this.percolation.open(x, y);
                    openSites++;
                }

                double threshold = openSites/Math.pow(this.gridSize, 2);
                thresholds[i-1] = threshold;
            }
        }

        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if(this.numTrials <= 1) {
            return Double.NaN;
        }


    }

//    // low  endpoint of 95% confidence interval
//    public double confidenceLo() {
//
//    }
//
//    // high endpoint of 95% confidence interval
//    public double confidenceHi() {
//
//    }

    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));



    }

    private void validateInputs(int gridSize, int numTrials) {
        if (gridSize <= 0 || numTrials <= 0) {
            throw new IllegalArgumentException("Grid size and Number of trials can't be less than zero");
        }
    }
}
