package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by ajay on 9/30/16.
 */
public class Percolation {

    private int gridSize = 0;
    private WeightedQuickUnionUF uf;
    private boolean[][] grid;
    private boolean percolates = false;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid grid size  i out of bounds");
        }
        gridSize = n;
        uf = new WeightedQuickUnionUF(getUnionSize());
        grid = new boolean[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i][j] = false;
            }
        }

    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        validateGridIndex(i, j);
        if (i > 1 && isOpen(i - 1, j)) {
            this.uf.union(xyTo1D(i, j), xyTo1D(i - 1, j));
        }

        if (i < this.gridSize && isOpen(i + 1, j)) {
            this.uf.union(xyTo1D(i, j), xyTo1D(i + 1, j));
        }

        if (j > 1 && isOpen(i, j - 1)) {
            this.uf.union(xyTo1D(i, j), xyTo1D(i, j - 1));
        }

        if (j < this.gridSize && isOpen(i, j + 1)) {
            this.uf.union(xyTo1D(i, j), xyTo1D(i, j + 1));
        }

        grid[i][j] = true;

        if (i == 1) {
            uf.union(0, xyTo1D(i, j));
        } else if (i == this.gridSize) {
            this.percolates = this.uf.connected(0, xyTo1D(i, j));
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validateGridIndex(i, j);
        return this.grid[i][j];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validateGridIndex(i, j);
        return this.uf.connected(0, xyTo1D(i, j));
    }

    // does the system percolate?
    public boolean percolates() {
        return this.percolates;
    }

    private int xyTo1D(int x, int y) {
        return this.gridSize * (x - 1) + y;
    }

    private void validateGridIndex(int rowIndex, int colIndex) {
        if (rowIndex <= 0 || rowIndex > this.gridSize) {
            throw new IndexOutOfBoundsException("row index is out of bounds");
        } else if (colIndex <= 0 || colIndex > this.gridSize) {
            throw new IndexOutOfBoundsException("column index is out of bounds");
        }
    }

    private int getUnionSize() {
        return (int) (Math.pow(this.gridSize, 2) + 1);
    }
}
