package eight_puzzle;

import edu.princeton.cs.algs4.MinPQ;

/**
 * Created by ajay on 11/8/16.
 */
public class Solver {

    private Board initialBoard;
    private int moves = 0;

    public Solver(Board initial) {
        this.initialBoard = initial;
    }        // find a solution to the initial board (using the A* algorithm)

    private void solveBoard() {
        MinPQ<Board>
    }
    public boolean isSolvable()            // is the initial board solvable?

    public int moves() {

        return -1;
    }                 // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {

        return null;
    }      // sequence of boards in a shortest solution; null if unsolvable

    public static void main(String[] args) // solve a slider puzzle (given below)
}
