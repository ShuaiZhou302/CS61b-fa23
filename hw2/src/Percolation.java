import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    boolean[] grid;
    int virtualTop;
    int virtualBottom;
    WeightedQuickUnionUF wqf;
    WeightedQuickUnionUF backWash; // create a new one has no virtual bottom that it can distinguish backwash
    int openSites;
    int size;
    // 0 - block 1 - open full - use wdf joints to solve

    public Percolation(int N) {
        if (N < 0) {
            throw new java.lang.IllegalArgumentException("Out of bounds");
        }
        grid = new boolean[N * N];
        wqf = new WeightedQuickUnionUF(N * N + 2);
        backWash = new WeightedQuickUnionUF(N * N + 1);
        virtualTop = N * N;
        virtualBottom = N * N + 1;
        openSites = 0;
        size = N;
        initialize();
    }

    public void initialize() {
        for (int i = 0; i < size; i++) {
            backWash.union(virtualTop, i);
            wqf.union(virtualTop, i);
        } // connect the top with virtual top
        for (int i = size * (size - 1); i < size * size; i++) {
            wqf.union(virtualBottom, i);
        }
    }

    public void open(int row, int col) { // if they recall this method it may cause the openSites increase again
        validIndex(row, col);
        if (!(grid[xyTodigit(row, col)])) {  // if they recall this method it may cause the openSites increase again
            grid[xyTodigit(row, col)] = true; // open the block
            openSites += 1; // count the number of open sites
            connectAround(row, col); // check if there are open sites nearby and connect them together
        }
    }

    public boolean isOpen(int row, int col) {
        validIndex(row, col);
        return grid[xyTodigit(row, col)]; // check if it is
    }

    public boolean isFull(int row, int col) { // careful if you are block , you can't be full !!
        validIndex(row, col);
        return (isOpen(row, col) && backWash.connected(virtualTop, xyTodigit(row, col)));
        // check if it is opened and make sure it's not backwash
    }

    public int numberOfOpenSites() {
        return openSites; // return the sites
    }

    public boolean percolates() {
        if (size == 1) {
            return grid[0]; // when N = 1 is unique
        }
        return wqf.connected(virtualBottom, virtualTop); // N > 1
    }
    // Help method part
    public void connectAround(int row, int col) {
        int now = xyTodigit(row, col);
        for (int x: nearby(row, col)) {
            if ((x != -1) && grid[x]) {
                wqf.union(x, now);
                backWash.union(x, now);
                /*if (backWash.connected(virtualTop, x)) {
                    wqf.connected(now, virtualTop);
                    backWash.connected(now, virtualTop);
                }
                 */
            }
        }
    }

    public int[] nearby(int row, int col) {
        int[] nearby = {-1, -1, -1, -1}; // up , down, left, right
        if (row != 0) {
            nearby[0] = xyTodigit(row - 1, col);
        }
        if (row != size - 1) {
            nearby[1] = xyTodigit(row + 1, col);
        }
        if (col != 0) {
            nearby[2] = xyTodigit(row, col - 1);
        }
        if (col != size - 1) {
            nearby[3] = xyTodigit(row, col + 1);
        }
        return nearby;
    }

    public int xyTodigit(int row, int col) {
        return size * row + col;
    }

    public void validIndex(int row, int col) {
        if (row < 0 || col < 0 || row > size - 1 || col > size - 1) {
            throw new IndexOutOfBoundsException("Out of bounds!");
        }
    }

}
