import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF sites;
    private boolean[] openedSites;
    private int numOfOpenedSites;
    private final int n;

    private final int virtualBottomId;
    private final int virtualTopId;
    private final int leftBound;
    private final int rightBound;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        this.sites = new WeightedQuickUnionUF(n * n + 2);
        this.openedSites = new boolean[n * n];
        for (int i = 0; i < (n * n); i++) {
            this.openedSites[i] = false;
        }
        this.numOfOpenedSites = 0;
        this.virtualBottomId = n * n;
        this.virtualTopId = n * n + 1;
        this.leftBound = 0;
        this.rightBound = n * n - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int i = convert2Dto1D(row, col);

        if (!this.openedSites[i]) {
            this.openedSites[i] = true;
            int neighbour;

            if (i % this.n != 0) {
                neighbour = i - 1;
                if (this.isInBound(neighbour) && this.openedSites[neighbour]) {
                    this.sites.union(i, neighbour);
                }
            }
            if (i % this.n != (this.n - 1)) {
                neighbour = i + 1;
                if (this.isInBound(neighbour) && this.openedSites[neighbour]) {
                    this.sites.union(i, neighbour);
                }
            }

            neighbour = i - this.n;
            if (this.isInBound(neighbour) && this.openedSites[neighbour]) {
                this.sites.union(i, neighbour);
            }

            neighbour = i + this.n;
            if (this.isInBound(neighbour) && this.openedSites[neighbour]) {
                this.sites.union(i, neighbour);
            }

            // Check top row
            if (i < this.n) {
                sites.union(i, this.virtualTopId);
            }

            // Check bottom row
            if (i >= ((this.n * this.n) - this.n)) {
                sites.union(i, this.virtualBottomId);
            }

            ++this.numOfOpenedSites;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int i = this.convert2Dto1D(row, col);
        return this.openedSites[i];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int i = this.convert2Dto1D(row, col);
        return this.sites.find(i) == this.sites.find(this.virtualTopId);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numOfOpenedSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.sites.find(this.virtualBottomId) == this.sites.find(this.virtualTopId);
    }

    private int convert2Dto1D(int row, int col) {
        if ((row <= 0) || (row > this.n)
                || (col <= 0) || (col > this.n)) {
            throw new IllegalArgumentException();
        }

        return this.n * (row - 1) + (col - 1);
    }

    private boolean checkNeighbour(int neighbour) {
        return this.isInBound(neighbour) && this.openedSites[neighbour];
    }

    private boolean isInBound(int i) {
        return (i >= this.leftBound) && (i <= this.rightBound);
    }

    // test client (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system

        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            System.out.println("Number of sites opened:" + perc.numOfOpenedSites);
            System.out.println("isFull(" + i + ", " + j + "):" + perc.isFull(i, j));
            System.out.println("isOpen(" + i + ", " + j + "):" + perc.isOpen(i, j));
        }
    }
}
