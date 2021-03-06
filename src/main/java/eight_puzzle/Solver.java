package eight_puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

/**
 * Created by ajay on 11/8/16.
 */
public class Solver {
    private Board initialBoard;
    private BoardInfo resultBoardInfo;

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("eight_puzzle.Board can't be null.");
        }
        this.initialBoard = initial;
        isSolvable();
    }

    public static void main(String[] args) {
        In in = new In("/home/ajay/Downloads/algorithms/8puzzle/" +
                "puzzle31.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        long time1 = System.currentTimeMillis();
        Solver solver = new Solver(initial);
        System.out.println(solver.moves());
        System.out.println(System.currentTimeMillis() - time1);
    }

    public boolean isSolvable() {

        if (resultBoardInfo != null && resultBoardInfo.moves != -1) {
            return true;
        }

        MinPQ<BoardInfo> boardQueue = new MinPQ<>();
        MinPQ<BoardInfo> twinQueue = new MinPQ<>();

        BoardInfo initialBoardInfo =
                new BoardInfo(0, this.initialBoard,
                        new BoardInfo(-1, null, null, -1, -1),
                        this.initialBoard.hamming(), this.initialBoard.manhattan());

        BoardInfo smallestOrgBoardInfo = initialBoardInfo;
        resultBoardInfo = smallestOrgBoardInfo;

        if (resultBoardInfo.board.isGoal()) {
            return true;
        }

        Board twinBoard = this.initialBoard.twin();
        BoardInfo twinBoardInfo =
                new BoardInfo(0, twinBoard, new BoardInfo(-1, null, null, -1, -1),
                        twinBoard.hamming(), this.initialBoard.manhattan());
        BoardInfo smallestTwinBoardInfo = twinBoardInfo;


        while (true) {

            if (smallestTwinBoardInfo.board.isGoal()) {
                resultBoardInfo.moves = -1;
                resultBoardInfo.board = null;
                resultBoardInfo.prevBoardInfo = null;
                return false;
            }

            for (Board neighbourBoard : smallestOrgBoardInfo.board.neighbors()) {
                if (!neighbourBoard.equals(
                        smallestOrgBoardInfo.prevBoardInfo.board)) {
                    boardQueue.insert(new BoardInfo(
                            smallestOrgBoardInfo.moves + 1,
                            neighbourBoard, smallestOrgBoardInfo,
                            neighbourBoard.hamming(), neighbourBoard.manhattan()));
                }
            }
            smallestOrgBoardInfo = boardQueue.delMin();

            resultBoardInfo = smallestOrgBoardInfo;
            if (resultBoardInfo.board.isGoal()) {
                return true;
            }

            for (Board neighbourBoard : smallestTwinBoardInfo.board.neighbors()) {
                if (!neighbourBoard.equals(
                        smallestTwinBoardInfo.prevBoardInfo.board)) {
                    twinQueue.insert(new BoardInfo(
                            smallestTwinBoardInfo.moves + 1,
                            neighbourBoard,
                            smallestTwinBoardInfo,
                            neighbourBoard.hamming(),
                            neighbourBoard.manhattan()));
                }
            }
            smallestTwinBoardInfo = twinQueue.delMin();
        }
    }

    public int moves() {
        if (resultBoardInfo == null) {
            isSolvable();
        }
        return resultBoardInfo.moves;
    }

    public Iterable<Board> solution() {

        if (resultBoardInfo == null) {
            isSolvable();
        }
        if (resultBoardInfo.moves == -1) {
            return null;
        }

        Stack<Board> solution = new Stack<>();
        BoardInfo currBoardInfo = resultBoardInfo;
        solution.push(currBoardInfo.board);
        while (currBoardInfo.prevBoardInfo.board != null) {
            solution.push(currBoardInfo.prevBoardInfo.board);
            currBoardInfo = currBoardInfo.prevBoardInfo;
        }

        return solution;
    }

    private static class BoardInfo implements Comparable<BoardInfo> {
        private int moves;
        private Board board;
        private BoardInfo prevBoardInfo;
        private int hamming = 0;
        private int manhattan = 0;

        private BoardInfo(int noOfMoves, Board bestBoard,
                          BoardInfo previousBoardInfo,
                          int hammingDist, int manhattanDist) {
            this.moves = noOfMoves;
            this.board = bestBoard;
            this.prevBoardInfo = previousBoardInfo;
            this.hamming = hammingDist;
            this.manhattan = manhattanDist;
        }

        @Override
        public int compareTo(BoardInfo o) {
            if (this.moves + this.manhattan > o.moves + o.manhattan) {
                return 1;
            } else if (this.moves + this.manhattan
                    < o.moves + o.manhattan) {
                return -1;
            } else if (this.moves + this.hamming > o.moves + o.hamming) {
                return 1;
            } else if (this.moves + this.hamming
                    < o.moves + o.hamming) {
                return -1;
            }
            return 0;
        }
    }
}
