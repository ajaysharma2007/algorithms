package eight_puzzle;

/**
 * Created by ajay on 11/7/16.
 */
public class Board {

    private char[] boardArrangement;
    private int hammingDist = 0;
    private int manhattenDist = 0;

    public Board(int[][] blocks) {
        boardArrangement = new char[blocks.length * blocks.length];
        int rowIndex = 0;
        for (int[] block : blocks) {
            int columnIndex = 0;
            for (int blockEntry : block) {
                int index = doubleToSingleIndex(rowIndex, columnIndex);
                boardArrangement[index] = (char) blocks[rowIndex][columnIndex++];
            }

            rowIndex++;
        }
    }

    private int doubleToSingleIndex(int rowIndex, int columnIndex) {
        return rowIndex * 3 + columnIndex;
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
        if (manhattenDist != 0) {
            return manhattenDist;
        }

        for (int i = 0; i < boardArrangement.length; i++) {
            if (boardArrangement[i] != 0) {
                int currentVal = boardArrangement[i];
                int dimension = dimension();

                int currentRowIndex = i / dimension;
                int currentColIndex = i % dimension;

                int correctRowIndex = (currentVal - 1) / dimension;
                int correctColIndex = currentVal % dimension - 1;

            }
        }
    }

    public boolean isGoal() {

    }              // is this board the goal board?

    public Board twin() {

    }               // a board that is obtained by exchanging any pair of blocks

    public boolean equals(Object y) {

    }    // does this board equal y?

    public Iterable<Board> neighbors() {

    }  // all neighboring boards

    public String toString() {

    }          // string representation of this board (in the output format specified below)

    public static void main(String[] args) {

    } // unit tests (not graded)
}
