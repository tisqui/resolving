/**
 * Created with IntelliJ IDEA.
 * User: Intellectsoft_user
 * Date: 11/29/13
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Percolation {

    private final PercolationImpl perc;

    public Percolation(int N) {
        perc = new PercolationImpl(N);
    }

    public void open(int i, int j) {
        perc.open(i - 1, j - 1);
    }

    public boolean isOpen(int i, int j) {
        return perc.isOpen(i - 1, j - 1);
    }

    public boolean isFull(int i, int j) {
        return perc.isFull(i - 1, j - 1);
    }

    public boolean percolates() {
        return perc.percolates();
    }


    private static class PercolationImpl {

        private WeightedQuickUnionUF set;
        private int count;
        private int side;
        private boolean[] opened;
        private final int top;
        private boolean percolated;
        private boolean[] connectedToBottom;


        // create N-by-N grid, with all sites blocked
        public PercolationImpl(int N) {

            if (N < 0) {
                throw new IllegalArgumentException("N <=0 ");
            }

            side = N;
            count = N * N + 2;
            opened = new boolean[count];
            connectedToBottom = new boolean[count];

            top = count - 2;

            for (int i = 0; i < count; i++) {
                opened[i] = false;
            }
            opened[top] = true;

            set = new WeightedQuickUnionUF(count);

        }

        private void checkArguments(int i, int j) {
            if (i < 0 || i >= side || j < 0 || j >= side) {
                throw new IndexOutOfBoundsException();
            }

        }

        private int getNumber(int i, int j) {
            checkArguments(i, j);
            return (side * i + j);
        }

        // open site (row i, column j) if it is not already
        public void open(int i, int j) {
            checkArguments(i, j);
            if (!isOpen(i, j)) {

                if (i == side - 1) {
                    connectedToBottom[set.find(getNumber(i, j))] = true;
                }

                opened[getNumber(i, j)] = true;

                setUnion(i, j, i - 1, j);
                setUnion(i, j, i + 1, j);
                setUnion(i, j, i, j - 1);
                setUnion(i, j, i, j + 1);

            }

        }

        private void setUnion(int i, int j, int i1, int j1) {
            if (i1 >= 0 && j1 >= 0 && i1 < side && j1 < side && isOpen(i1, j1)) {

                union(getNumber(i, j), getNumber(i1, j1));
            }
            if (i == 0) {
                union(getNumber(i, j), top);
            }

        }

        private void union(int a, int b) {
            boolean isConnectedToTop = set.connected(top, a)
                    || set.connected(top, b);

            int aCmp = set.find(a);
            int bCmp = set.find(b);
            boolean isConnectedToBottom = connectedToBottom[aCmp]
                    || connectedToBottom[bCmp];

            set.union(a, b);

            if (isConnectedToBottom) {
                connectedToBottom[aCmp] = false;
                connectedToBottom[bCmp] = false;
                connectedToBottom[set.find(a)] = true;
            }

            if (isConnectedToBottom && isConnectedToTop) {
                percolated = true;
            }

        }

        public boolean isOpen(int i, int j)    // is site (row i, column j) open?
        {
            checkArguments(i, j);
            return opened[getNumber(i, j)];
        }


        public boolean isFull(int i, int j)    // is site (row i, column j) full?
        {
            checkArguments(i, j);
            return set.connected(top, getNumber(i, j));
        }

        public boolean percolates()            // does the system percolate?
        {
            return percolated;
        }


    }
}
