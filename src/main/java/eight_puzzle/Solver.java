package eight_puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by ajay on 11/8/16.
 */
public class Solver {

    private Board initialBoard;
    private int moves = 0;
    private Queue<Board> solutionQueue;

    public Solver(Board initial) {
        this.initialBoard = initial;
    }

    public static void main(String[] args) {
        In in = new In("/home/ajay/Downloads/algorithms/8puzzle/puzzle3x3-unsolvable.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }


    private Comparator<Board> getBoardComparator() {
        return new Comparator<Board>() {
            @Override
            public int compare(Board o1, Board o2) {
                if (o1.hamming() > o2.hamming()) {
                    return 1;
                } else if (o1.hamming() < o2.hamming()) {
                    return -1;
                }
                return 0;
            }
        };
    }

    public boolean isSolvable() {

        moves = 0;
        solutionQueue = new Queue<>();

        MinPQ<Board> boardQueue = new MinPQ<>(getBoardComparator());
        MinPQ<Board> twinQueue = new MinPQ<>(getBoardComparator());

        boardQueue.insert(this.initialBoard);
        twinQueue.insert(this.initialBoard.twin());

        Board smallestOrgBoard = boardQueue.delMin();
        Board smallestTwinBoard = twinQueue.delMin();

        Board preOrgBoard = null;
        Board preTwinBoard = null;

        solutionQueue.enqueue(smallestOrgBoard);

        while (!smallestOrgBoard.isGoal() && !smallestTwinBoard.isGoal()) {

            Iterator<Board> orgIterator = smallestOrgBoard.neighbors().iterator();
            while (orgIterator.hasNext()) {
                Board neighbourBoard = orgIterator.next();
                if (!neighbourBoard.equals(preOrgBoard)) {
                    boardQueue.insert(neighbourBoard);
                }
            }
            preOrgBoard = smallestOrgBoard;
            smallestOrgBoard = boardQueue.delMin();
            solutionQueue.enqueue(smallestOrgBoard);
            moves++;

            Iterator<Board> twinIterator = smallestTwinBoard.neighbors().iterator();
            while (twinIterator.hasNext()) {
                Board neighbourBoard = twinIterator.next();
                if (!neighbourBoard.equals(preTwinBoard)) {
                    twinQueue.insert(neighbourBoard);
                }
            }
            preTwinBoard = smallestTwinBoard;
            smallestTwinBoard = twinQueue.delMin();

        }

        if (smallestTwinBoard.isGoal()) {
            moves = -1;
            solutionQueue = null;
            return false;
        }

        return true;
    }


    public int moves() {
        isSolvable();
        return moves;
    }

    public Iterable<Board> solution() {
        if (isSolvable()) {
            return solutionQueue;
        }
        return null;
    }
}
