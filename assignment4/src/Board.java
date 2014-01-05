
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * Date: 12/11/13
 * Time: 1:37 PM
 */
public class Board {

    private int[][] goal;
    private int[][] boardContent;
    private int dimension;

    /**
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     *
     * @param blocks
     */
    public Board(int[][] blocks) {
        dimension = blocks.length;
        boardContent = new int[dimension][dimension];
        goal = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                boardContent[i][j] = blocks[i][j];
                goal[i][j] = i * dimension + j + 1;
            }
        }
        goal[dimension-1][dimension-1] = 0;
    }

    /**
     * board dimension N
     *
     * @return
     */
    public int dimension() {
        return dimension;
    }

    /**
     * number of blocks out of place
     *
     * @return
     */
    public int hamming() {
        int count = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (boardContent[i][j] != goal[i][j] && boardContent[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     *
     * @return
     */
    public int manhattan() {
        int manhattanDistanceSum = 0; //sum of the steps of blocks to their positions
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int val = boardContent[i][j];
                if (val != 0) {
                    int targetX = (val - 1) / dimension;
                    int targetY = (val - 1) % dimension;
                    int dx = i - targetX; // x-distance to expected coordinate
                    int dy = j - targetY; // y-distance to expected coordinate
                    manhattanDistanceSum += Math.abs(dx) + Math.abs(dy);
                }

            }
        }
        return manhattanDistanceSum;
    }

    /**
     * is this board the goal board?
     *
     * @return
     */
    public boolean isGoal() {
        boolean isSame = true;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (boardContent[i][j] != goal[i][j]) {
                    isSame = false;
                }
            }
        }

        return isSame;
    }

    /**
     * a board obtained by exchanging two adjacent blocks in the same row
     *
     * @return
     */
    public Board twin() {
        int[][] twin = boardContent.clone();
        if (dimension > 1) {
            if (twin[0][0] != 0) {
                int buff = twin[0][0];
                twin[0][0] = twin[0][1];
                twin[0][1] = buff;
            } else {
                int buff = twin[1][0];
                twin[1][0] = twin[1][1];
                twin[1][1] = buff;
            }
            return new Board(twin);
        } else return null;
    }

    /**
     * does this board equal y?
     *
     * @param y
     * @return
     */
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null || y.getClass() != this.getClass()) {
            return false;
        }
        Board comparingTo = (Board) y;
        if (this.dimension != comparingTo.dimension) {
            return false;
        }
        boolean isEqual = true;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (comparingTo.boardContent[i][j] != this.boardContent[i][j]) {
                    isEqual = false;
                }
            }
        }
        return isEqual;

    }

    /**
     * all neighboring boards
     *
     * @return
     */
    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<Board>();
        int i=0,j=0;
        for (int k = 0; k < dimension; k++) {
            for (int l = 0; l < dimension; l++) {
                if (boardContent[k][l] == 0) {
                 i=k;
                 j=l;
                }
            }
        }

        if (i + 1 < dimension) {   //copy by hands
            int[][] buff = createCopy(boardContent);
            buff[i][j] = buff[i + 1][j];
            buff[i + 1][j] = 0;
            boards.push(new Board(buff));
        }
        if (i - 1 >= 0) {
            int[][] buff = createCopy(boardContent);
            buff[i][j] = buff[i - 1][j];
            buff[i - 1][j] = 0;
            boards.push(new Board(buff));
        }
        if (j + 1 < dimension) {
            int[][] buff = createCopy(boardContent);
            buff[i][j] = buff[i][j + 1];
            buff[i][j + 1] = 0;
            boards.push(new Board(buff));
        }
        if (j - 1 >= 0) {
            int[][] buff = createCopy(boardContent);
            buff[i][j] = buff[i][j - 1];
            buff[i][j - 1] = 0;
            boards.push(new Board(buff));
        }
        return boards;

    }

    private int[][] createCopy(int[][] arr1){
        int[][] arr = new int[arr1.length][arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1.length; j++) {
                arr[i][j] = arr1[i][j];
            }
        }
        return arr;
    }

    /**
     * string representation of the board (in the output format specified below)
     *
     * @return
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(dimension);
        builder.append("\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                builder.append(boardContent[i][j]);
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
