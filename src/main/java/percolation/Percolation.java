package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * The {@code percolation.Percolation} class represents
 * a <em>percolation.Percolation data type</em>.
 * It supports the <em>isOpen</em>, <em>isFull</em>, <em>open</em> operation
 * and a <em>percolates</em> operation.
 * <ul>
 * <li><em>isOpen</em>(<em>i</em>, <em>j</em>) returns a boolean
 * indicating that the site represented by index <em>i</em> and <em>j</em>
 * is open or not.
 * <li><em>isFull</em>(<em>i</em>, <em>j</em>)
 * returns a boolean based on the condition that
 * if there is a connection between site represented
 * by index <em>i</em> and <em>j</em> and
 * any site represented by index <em>row {@code 1}, col {@code 1....n}</em>
 * <li><em>open</em>(<em>i</em>, <em>j</em>)
 * This opens a site represented by
 * index <em>i</em>, <em>j</em> by connecting it to
 * other adjacent open sites.
 * <li><em>percolates</em>() returns a boolean based on the condition that
 * if there is a connection between any site
 * represented by <em>row {@code 1}, col {@code 1....n}</em>
 * and any site represented by index <em>row {@code n}, col {@code 1....n}</em>
 * </ul>
 *
 * @author Ajay Sharma
 */

public class Percolation {

    // This stores the size of the grid passed as input.
    private int gridSize = 0;
    // This stores WeightedQuickUnionUF for union/find operations for percolation.
    private WeightedQuickUnionUF percolatesWQUF;
    // This stores WeightedQuickUnionUF for union/find operations for isFull op.
    private WeightedQuickUnionUF fullWQUF;
    // This stores the open/closed status of the sites.
    private boolean[][] grid;

    /**
     * Initializes an empty percolation.Percolation data
     * structure with {@code n^2 grid}.
     * <p>
     *
     * @param n the size representing the n*n grid.
     * @throws IllegalArgumentException if {@code n < 0}
     *                                  </p>
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size can't be less than zero.");
        }
        gridSize = n;
        percolatesWQUF = new WeightedQuickUnionUF(getUnionSize());
        fullWQUF = new WeightedQuickUnionUF(getUnionSize()-1);
        grid = new boolean[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i][j] = false;
            }
        }

    }

    /**
     * Opens the site containing site represented by {@code i}, {@code j} by
     * moving it to the component having the adjacent open sites.
     *
     * @param i the integer representing row  of the site
     * @param j the integer representing column  of the site
     * @throws IndexOutOfBoundsException unless
     *                  both {@code 0 < i <= n} and {@code 0 < j <= n}
     */
    public void open(int i, int j) {
        validateGridIndex(i, j);
        if (i > 1 && isOpen(i - 1, j)) {
            this.percolatesWQUF.union(xyTo1D(i, j), xyTo1D(i - 1, j));
            this.fullWQUF.union(xyTo1D(i, j), xyTo1D(i - 1, j));
        }

        if (i < this.gridSize && isOpen(i + 1, j)) {
            this.percolatesWQUF.union(xyTo1D(i, j), xyTo1D(i + 1, j));
            this.fullWQUF.union(xyTo1D(i, j), xyTo1D(i + 1, j));
        }

        if (j > 1 && isOpen(i, j - 1)) {
            this.percolatesWQUF.union(xyTo1D(i, j), xyTo1D(i, j - 1));
            this.fullWQUF.union(xyTo1D(i, j), xyTo1D(i, j - 1));
        }

        if (j < this.gridSize && isOpen(i, j + 1)) {
            this.percolatesWQUF.union(xyTo1D(i, j), xyTo1D(i, j + 1));
            this.fullWQUF.union(xyTo1D(i, j), xyTo1D(i, j + 1));
        }

        grid[i][j] = true;

        if (i == 1) {
            percolatesWQUF.union(0, xyTo1D(i, j));
            fullWQUF.union(0, xyTo1D(i, j));
        }

        if (i == this.gridSize) {
            percolatesWQUF.union(getUnionSize() - 1, xyTo1D(i, j));
        }
    }

    /**
     * Returns true if the site represented by row {@code i}, col {@code j} is open.
     * Open/Closed status is maintained in a 2D array.
     *
     * @param i the integer representing row  of the site
     * @param j the integer representing column  of the site
     * @return {@code true} if the site represented
     * by row {@code i}, col {@code j} is open.
     * {@code false} otherwise
     * @throws IndexOutOfBoundsException unless
     *              both {@code 0 < i <= n} and {@code 0 < j <= n}
     */
    public boolean isOpen(int i, int j) {
        validateGridIndex(i, j);
        return this.grid[i][j];
    }

    /**
     * Returns true if the site represented by row {@code i}, col {@code j} and
     * any site represented by
     * row {@code 1}, col {@code 1....n} are in same component.
     *
     * @param i the integer representing row  of the site
     * @param j the integer representing column  of the site
     * @return {@code true} site represented by row {@code i}, col {@code j}
     * is connected to any site represented by row {@code 1}, col {@code 1....n}.
     * {@code false} otherwise
     * @throws IndexOutOfBoundsException unless
     *            both {@code 0 < i <= n} and {@code 0 < j <= n}
     */
    public boolean isFull(int i, int j) {
        validateGridIndex(i, j);
        return this.fullWQUF.connected(0, xyTo1D(i, j));
    }

    /**
     * Returns true if the site represented by row  {@code 1}, col {@code 1....n} and
     * any site represented by
     * row {@code n}, col {@code 1....n} are in same component.
     *
     * @return {@code true} site represented by row {@code 1}, col {@code 1....n}
     * is connected to any site represented by row {@code n}, col {@code 1....n}.
     * {@code false} otherwise
     * @throws IndexOutOfBoundsException unless
     *             both {@code 0 < i <= n} and {@code 0 < j <= n}
     */
    public boolean percolates() {
        return this.percolatesWQUF.connected(0, getUnionSize() - 1);
    }

    /*
        Converts a 2 dimensional point to one dimensional point. This is used to map
        2d grid points to the 1d array es.
     */
    private int xyTo1D(int x, int y) {
        return this.gridSize * (x - 1) + y;
    }

    /*
        Validates the passed indexes for an array.
     */
    private void validateGridIndex(int rowIndex, int colIndex) {
        if (rowIndex <= 0 || rowIndex > this.gridSize) {
            throw new IndexOutOfBoundsException("row index is out of bounds");
        } else if (colIndex <= 0 || colIndex > this.gridSize) {
            throw new IndexOutOfBoundsException("column index is out of bounds");
        }
    }

    /*
        Provides the size of the weighted quick union array.
     */
    private int getUnionSize() {
        return (int) (Math.pow(this.gridSize, 2) + 2);
    }
}
