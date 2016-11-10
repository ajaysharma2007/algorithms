import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by ajay on 11/8/16.
 */
public class Solver {

    private Board initialBoard;
    private Queue<Board> solutionQueue;
    private BoardInfo resultBoardInfo;

    private static class BoardInfo implements Comparable<BoardInfo> {
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
            int thisHamming = this.board.hamming();
            int thisManhattan = this.board.manhattan();
            int oHamming = o.board.hamming();
            int oManhattan = o.board.hamming();
            if (this.moves + thisHamming > o.moves + oHamming) {
                return 1;
            } else if (this.moves + thisHamming
                    < o.moves + oHamming) {
                return -1;
            } else if (this.moves + thisManhattan
                    > o.moves + oManhattan) {
                return 1;
            } else if (this.moves + thisManhattan
                    < o.moves + oManhattan) {
                return -1;
            }

            return 0;
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("Board can't be null.");
        }
        this.initialBoard = initial;
    }

    public static void main(String[] args) {
        In in = new In("/home/ajay/Downloads/algorithms/8puzzle/" +
                "puzzle2x2-unsolvable1.txt");
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

        if (solutionQueue != null) {
            return true;
        }
        solutionQueue = new Queue<>();

        MinPQ<BoardInfo> boardQueue = new MinPQ<>();
        MinPQ<BoardInfo> twinQueue = new MinPQ<>();

        BoardInfo initialBoardInfo =
                new BoardInfo(0, this.initialBoard, null);

        BoardInfo twinBoardInfo =
                new BoardInfo(0, this.initialBoard.twin(), null);

        boardQueue.insert(initialBoardInfo);
        twinQueue.insert(twinBoardInfo);

        BoardInfo smallestOrgBoardInfo = boardQueue.delMin();
        BoardInfo smallestTwinBoardInfo = twinQueue.delMin();

        solutionQueue.enqueue(smallestOrgBoardInfo.board);
        resultBoardInfo = smallestOrgBoardInfo;

        while (true) {
            if (smallestOrgBoardInfo.board.isGoal()) {
                return true;
            }
            if (smallestTwinBoardInfo.board.isGoal()) {
                resultBoardInfo.moves = -1;
                solutionQueue = null;
                return false;
            }
            for (Board neighbourBoard : smallestOrgBoardInfo.board.neighbors()) {
                if (!neighbourBoard.equals(smallestOrgBoardInfo.prevBoard)) {
                    boardQueue.insert(new BoardInfo(
                            smallestOrgBoardInfo.moves + 1, neighbourBoard, smallestOrgBoardInfo.board));
                }
            }
            smallestOrgBoardInfo = boardQueue.delMin();
            solutionQueue.enqueue(smallestOrgBoardInfo.board);
            resultBoardInfo = smallestOrgBoardInfo;

            for (Board neighbourBoard : smallestTwinBoardInfo.board.neighbors()) {
                if (!neighbourBoard.equals(smallestTwinBoardInfo.prevBoard)) {
                    twinQueue.insert(new BoardInfo(
                            smallestTwinBoardInfo.moves + 1,
                            neighbourBoard,
                            smallestTwinBoardInfo.board));
                }
            }
            smallestTwinBoardInfo = twinQueue.delMin();
        }
    }

    public int moves() {
        if (solutionQueue == null) {
            isSolvable();
        }
        return resultBoardInfo.moves;
    }

    public Iterable<Board> solution() {

        if (solutionQueue == null) {
            isSolvable();
        }
        return solutionQueue;
    }
}
