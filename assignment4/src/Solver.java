import java.util.Comparator;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * Date: 12/11/13
 * Time: 11:44 PM
 */
public class Solver {

    private Board initialBoard;
    private MinPQ<SearchNode> solverQueue;
    private Queue<Board> solutionTrace;
    private int movesToSolution;
    boolean isSolvable;

    private class SearchNode {
        private Board currentBoard;
        private Board previousBoard;
        private int numberOfMoves;

        public SearchNode(Board b) {
            currentBoard = b;
            previousBoard = null;
            numberOfMoves = 0;
        }

    }

    /**
     * find a solution to the initial board (using the A* algorithm)
     *
     * @param initial
     */
    public Solver(Board initial) {
       initialBoard = initial;

        Comparator comp = new Comparator<SearchNode>() {

            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.currentBoard.hamming() == o2.currentBoard.hamming()) {
                    return 0;
                } else if (o1.currentBoard.hamming() < o2.currentBoard.hamming()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        };

        solutionTrace = new Queue<Board>();

        solverQueue = new MinPQ<SearchNode>(comp);
        SearchNode first = new SearchNode(initialBoard);
        solutionTrace.enqueue(initialBoard);
        solverQueue.insert(first);


        SearchNode deleting = solverQueue.delMin();

        isSolvable = true;

        //TODO check if the board can be solved
        /**
         * To apply the fact, run the A* algorithm simultaneously on two puzzle
         * instances—one with the initial board and one with the initial
         * board modified by swapping a pair of adjacent blocks in the same
         * row. Exactly one of the two will lead to the goal board.
         */

        do{
            Iterable<Board> neighbors = deleting.currentBoard.neighbors();
            for (Board board : neighbors) {

                if (!board.equals(deleting.previousBoard)) {
                    SearchNode temp = new SearchNode(board);
                    temp.previousBoard = deleting.currentBoard;
                    temp.numberOfMoves = deleting.numberOfMoves + 1;
                    solverQueue.insert(temp);

                }
                //System.out.println(board);
            }
            if (!solverQueue.isEmpty()) {
                deleting = solverQueue.delMin();
                solutionTrace.enqueue(deleting.currentBoard);
            }

        }while (deleting.currentBoard.isGoal() != true && !solverQueue.isEmpty());

        movesToSolution = deleting.numberOfMoves;

    }

    /**
     * find the solution
     */
    private void searchForBoardSolution(Queue<Board> solution, MinPQ<SearchNode> solver){

    }

    /**
     * is the initial board solvable?
     *
     * @return
     */
    public boolean isSolvable() {
        return isSolvable;
    }

    /**
     * min number of moves to solve initial board; -1 if no solution
     *
     * @return
     */
    public int moves() {

        return movesToSolution;
    }

    /**
     * sequence of boards in a shortest solution; null if no solution
     *
     * @return
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return solutionTrace;

    }

    /**
     * solve a slider puzzle (given below)
     *
     * @param args
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
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

}
