import edu.princeton.cs.algs4.Queue;

/**
 * Created by ajay on 11/7/16.
 */
public class Board {

    private char[] boardArrangement;
    private int hammingDist = 0;
    private int manhattanDist = 0;
    private int blankIndex = -1;
    private Board twin;
    private Queue<Board> neighbours;

    private Board(char[] boardArrangement, int blankIdx) {
        this.boardArrangement = boardArrangement;
        this.blankIndex = blankIdx;
    }

    public Board(int[][] blocks) {
        boardArrangement = new char[blocks.length * blocks.length];
        int rowIndex = 0;
        for (int[] block : blocks) {
            int columnIndex = 0;
            for (int blockEntry : block) {
                int index = doubleToSingleIndex(rowIndex, columnIndex);
                boardArrangement[index] = (char) blocks[rowIndex][columnIndex++];
                if (blockEntry == 0) {
                    blankIndex = index;
                }
            }

            rowIndex++;
        }
    }

    public static void main(String[] args) {
        int[][] testBoardArr = {{5, 0, 4}, {2, 3, 8}, {7, 1, 6}};
        Board testBoard = new Board(testBoardArr);

        System.out.print("Test board toString : ");
        System.out.println(testBoard.toString());
        System.out.println();

        System.out.print("Dimension is : ");
        System.out.println(testBoard.dimension());
        System.out.println();

        System.out.print("Hamming value is : ");
        System.out.println(testBoard.hamming());
        System.out.println();

        System.out.print("Manhattan distance is : ");
        System.out.println(testBoard.manhattan());
        System.out.println();

        System.out.print("Is this a goal board : ");
        System.out.println(testBoard.isGoal());
        System.out.println();

        System.out.println("Neighbours : ");
        for (Board neighbour : testBoard.neighbors()) {
            System.out.println(neighbour.toString());
        }
        System.out.println();

    }

    private int doubleToSingleIndex(int rowIndex, int columnIndex) {
        return rowIndex * dimension() + columnIndex;
    }

    public int dimension() {
        return (int) Math.sqrt(boardArrangement.length);
    }

    public int hamming() {
        if (hammingDist != 0) {
            return hammingDist;
        }

        for (int i = 0; i < boardArrangement.length; i++) {
            if (boardArrangement[i] != 0 && boardArrangement[i] != i + 1) {
                hammingDist++;
            }
        }

        return hammingDist;
    }

    public int manhattan() {
        if (manhattanDist != 0) {
            return manhattanDist;
        }

        for (int i = 0; i < boardArrangement.length; i++) {
            if (boardArrangement[i] != 0) {
                int currentVal = boardArrangement[i];
                int dimension = dimension();

                int currentRowIndex = i / dimension;
                int currentColIndex = i % dimension;

                int correctRowIndex = (currentVal - 1) / dimension;
                int correctColIndex = (currentVal - 1) % dimension;

                int rowDistance = Math.abs(currentRowIndex - correctRowIndex);
                int colDistance = Math.abs(currentColIndex - correctColIndex);

                manhattanDist = manhattanDist + rowDistance + colDistance;
            }
        }

        return manhattanDist;
    }

    public boolean isGoal() {
        hamming();
        return hammingDist == 0;
    }

    public Board twin() {
        if (twin != null) {
            return this.twin;
        }
        int arrLength = this.boardArrangement.length;
        char[] twinBoard = new char[arrLength];

        for (int i = 0; i < arrLength; i++) {
            twinBoard[i] = this.boardArrangement[i];
        }

        if (blankIndex - 2 >= 0) {
            swapArrIndex(twinBoard, blankIndex - 1, blankIndex - 2);
        } else if (blankIndex + 2 < arrLength) {
            swapArrIndex(twinBoard, blankIndex + 1, blankIndex + 2);
        }

        twin = new Board(twinBoard, blankIndex);
        return twin;
    }

    private void swapArrIndex(char[] arr, int index1, int index2) {
        char temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    public boolean equals(Object y) {
        return y != null && this.toString().equals(y.toString());
    }

    public Iterable<Board> neighbors() {
        if (neighbours != null) {
            return this.neighbours;
        }
        neighbours = new Queue<>();
        int arrLength = this.boardArrangement.length;
        int dimension = dimension();
        int[] neighbourIndex = {blankIndex - 1, blankIndex + 1,
                blankIndex - dimension, blankIndex + dimension};

        for (int c : neighbourIndex) {
            if (c >= 0 && c < arrLength) {
                char[] neighbourBoard = new char[arrLength];
                for (int i = 0; i < arrLength; i++) {
                    neighbourBoard[i] = this.boardArrangement[i];
                }
                swapArrIndex(neighbourBoard, c, blankIndex);
                neighbours.enqueue(new Board(neighbourBoard, c));
            }
        }

        return neighbours;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int n = dimension();
        int i = 1;
        s.append(n + "\n");
        for (char aBoardArrangement : boardArrangement) {
            s.append(String.format("%2d ", (int) aBoardArrangement));
            if (i % n == 0) {
                s.append("\n");
            }
            i++;
        }
        return s.toString();
    }
}
