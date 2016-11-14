import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by ajay on 11/8/16.
 */
public class Solver {

    private Board initialBoard;
    private BoardInfo resultBoardInfo;

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("Board can't be null.");
        }
        this.initialBoard = initial;
    }

    public static void main(String[] args) {
        In in = new In("/home/ajay/Downloads/algorithms/8puzzle/" +
                "puzzle07.txt");
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

    private void copyQueue(Queue<Board> source, Queue<Board> dest) {
        for (Board sourceBoardItem : source) {
            dest.enqueue(sourceBoardItem);
        }
    }

    public boolean isSolvable() {

        if (resultBoardInfo != null) {
            return true;
        }

        MinPQ<BoardInfo> boardQueue = new MinPQ<>();
        MinPQ<BoardInfo> twinQueue = new MinPQ<>();

        BoardInfo initialBoardInfo =
                new BoardInfo(0, this.initialBoard, null);

        Board twinBoard = this.initialBoard.twin();
        BoardInfo twinBoardInfo =
                new BoardInfo(0, twinBoard, null);

        boardQueue.insert(initialBoardInfo);
        twinQueue.insert(twinBoardInfo);

        BoardInfo smallestOrgBoardInfo = boardQueue.delMin();
        BoardInfo smallestTwinBoardInfo = twinQueue.delMin();

        smallestOrgBoardInfo.solution = new Queue<>();
        smallestOrgBoardInfo.solution.enqueue(smallestOrgBoardInfo.board);

        resultBoardInfo = smallestOrgBoardInfo;

        while (true) {
            if (smallestOrgBoardInfo.board.isGoal()) {
                return true;
            }
            if (smallestTwinBoardInfo.board.isGoal()) {
                resultBoardInfo.moves = -1;
                resultBoardInfo.solution = null;
                return false;
            }
            for (Board neighbourBoard : smallestOrgBoardInfo.board.neighbors()) {
                if (!neighbourBoard.equals(
                        smallestOrgBoardInfo.prevBoardInfo.board)) {
                    boardQueue.insert(new BoardInfo(
                            smallestOrgBoardInfo.moves + 1,
                            neighbourBoard, smallestOrgBoardInfo));
                }
            }
            smallestOrgBoardInfo = boardQueue.delMin();
            smallestOrgBoardInfo.solution = new Queue<>();
            copyQueue(smallestOrgBoardInfo.prevBoardInfo.solution,
                    smallestOrgBoardInfo.solution);
            smallestOrgBoardInfo.solution.enqueue(smallestOrgBoardInfo.board);

            resultBoardInfo = smallestOrgBoardInfo;

            for (Board neighbourBoard : smallestTwinBoardInfo.board.neighbors()) {
                if (!neighbourBoard.equals(
                        smallestTwinBoardInfo.prevBoardInfo.board)) {
                    twinQueue.insert(new BoardInfo(
                            smallestTwinBoardInfo.moves + 1,
                            neighbourBoard,
                            smallestTwinBoardInfo));
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
        return resultBoardInfo.solution;
    }

    private static class BoardInfo implements Comparable<BoardInfo> {
        private int moves;
        private Board board;
        private BoardInfo prevBoardInfo;
        private Queue<Board> solution;

        private BoardInfo(int noOfMoves, Board bestBoard,
                          BoardInfo previousBoardInfo) {
            this.moves = noOfMoves;
            this.board = bestBoard;
            this.prevBoardInfo = previousBoardInfo;
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
}
