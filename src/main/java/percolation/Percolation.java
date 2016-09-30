package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by ajay on 9/30/16.
 */
public class Percolation {

    int gridSize = 0;
    int[][] grid ;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n)  {
        gridSize = n;
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= n; j++) {
                grid[i][j] = n*(i-1) + j;
            }
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j)     {

    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {

    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j)  {

    }

    // does the system percolate?
    public boolean percolates()  {

    }

    // test client (optional)
    public static void main(String[] args) {

    }

    private int xyTo1D(int x, int y) {
        return this.gridSize*(x-1) + y;
    }
}
