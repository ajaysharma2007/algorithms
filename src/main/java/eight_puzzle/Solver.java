package eight_puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * Created by ajay on 11/8/16.
 */
public class Solver {

    private class BoardInfo implements Comparable<BoardInfo> {
        private int moves;
        private Board board;
        private Board prevBoard;

        private BoardInfo(int noOfMoves, Board bestBoard, Board previousBoard) {
            this.moves = noOfMoves;
            this.board = bestBoard;
            this.prevBoard = previousBoard;
        }

        @Override
        public int compareTo(BoardInfo o) {
            if (this.moves + this.board.hamming() > o.moves + o.board.hamming()) {
                return 1;
            } else if (this.moves + this.board.hamming() < o.moves + o.board.hamming()) {
                return -1;
            } else if (this.moves + this.board.manhattan() > o.moves + o.board.manhattan()) {
                return 1;
            } else if (this.moves + this.board.manhattan() < o.moves + o.board.manhattan()) {
                return -1;
            }

            return 0;
        }
    }

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

    public boolean isSolvable() {

        moves = 0;
        solutionQueue = new Queue<>();

        MinPQ<BoardInfo> boardQueue = new MinPQ<>();
        MinPQ<BoardInfo> twinQueue = new MinPQ<>();

        BoardInfo initialBoardInfo = new BoardInfo(this.moves, this.initialBoard, null);

        BoardInfo twinBoardInfo = new BoardInfo(this.moves, this.initialBoard.twin(), null);

        boardQueue.insert(initialBoardInfo);
        twinQueue.insert(twinBoardInfo);

        BoardInfo smallestOrgBoardInfo = boardQueue.delMin();
        BoardInfo smallestTwinBoardInfo = twinQueue.delMin();

        solutionQueue.enqueue(smallestOrgBoardInfo.board);

        while (!smallestOrgBoardInfo.board.isGoal() && !smallestTwinBoardInfo.board.isGoal()) {

            moves++;
            for (Board neighbourBoard : smallestOrgBoardInfo.board.neighbors()) {
                if (!neighbourBoard.equals(smallestOrgBoardInfo.prevBoard)) {
                    boardQueue.insert(new BoardInfo(this.moves, neighbourBoard, smallestOrgBoardInfo.board));
                }
            }
            smallestOrgBoardInfo = boardQueue.delMin();
            solutionQueue.enqueue(smallestOrgBoardInfo.board);

            for (Board neighbourBoard : smallestTwinBoardInfo.board.neighbors()) {
                if (!neighbourBoard.equals(smallestTwinBoardInfo.prevBoard)) {
                    twinQueue.insert(new BoardInfo(this.moves, neighbourBoard, smallestTwinBoardInfo.board));
                }
            }
            smallestTwinBoardInfo = twinQueue.delMin();
        }

        if (smallestTwinBoardInfo.board.isGoal()) {
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
