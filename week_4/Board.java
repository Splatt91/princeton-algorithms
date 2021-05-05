/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

import java.util.LinkedList;
import java.util.List;

public class Board {

    private final int[][] tiles;
    private int blankRow;
    private int blankCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException();
        }

        this.tiles = copyOf(tiles);
        int n = tiles.length;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0) {
                    blankRow = row;
                    blankCol = col;
                }
            }
        }
    }

    private int[][] copyOf(int[][] tilesToCopy) {
        int n = tilesToCopy.length;
        int[][] copy = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                copy[row][col] = tilesToCopy[row][col];
            }
        }
        return copy;
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int n = this.tiles.length;
        builder.append(n).append(System.lineSeparator());
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                builder.append(String.format("%2d ", tiles[row][col]));
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return this.tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;

        int n = this.tiles.length;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int tile = this.tiles[row][col];
                if ((tile != 0) && (manhattan(row, col) != 0)) {
                    ++hamming;
                }
            }
        }

        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanSum = 0;
        int n = dimension();

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int tile = this.tiles[row][col];
                if (tile > 0) {
                    manhattanSum += manhattan(row, col);
                }
            }
        }

        return manhattanSum;
    }

    private int manhattan(int row, int col) {
        int destVal = this.tiles[row][col] - 1;
        int destRow = destVal / dimension();
        int destCol = destVal % dimension();
        return Math.abs(destRow - row) + Math.abs(destCol - col);
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        else if (this == y) {
            return true;
        }
        else if (this.getClass() != y.getClass()) {
            return false;
        }
        else {
            Board that = (Board) y;
            int nThis = this.dimension();
            int nThat = that.dimension();

            if (nThis != nThat) {
                return false;
            }

            for (int row = 0; row < nThis; row++) {
                for (int col = 0; col < nThis; col++) {
                    if (this.tiles[row][col] != that.tiles[row][col]) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    private void swap(int[][] v, int rowA, int colA, int rowB, int colB) {
        int swap = v[rowA][colA];
        v[rowA][colA] = v[rowB][colB];
        v[rowB][colB] = swap;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] cloned = copyOf(this.tiles);
        if (blankRow != 0) {
            swap(cloned, 0, 0, 0, 1);
        }
        else {
            swap(cloned, 1, 0, 1, 1);
        }
        return new Board(cloned);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new LinkedList<>();
        int n = this.tiles.length;

        if (blankRow > 0) {
            int[][] north = copyOf(this.tiles);
            swap(north, blankRow, blankCol, blankRow - 1, blankCol);
            neighbors.add(new Board(north));
        }
        if (blankRow < (n - 1)) {
            int[][] south = copyOf(this.tiles);
            swap(south, blankRow, blankCol, blankRow + 1, blankCol);
            neighbors.add(new Board(south));
        }
        if (blankCol > 0) {
            int[][] west = copyOf(this.tiles);
            swap(west, blankRow, blankCol, blankRow, blankCol - 1);
            neighbors.add(new Board(west));
        }
        if (blankCol < (n - 1)) {
            int[][] east = copyOf(this.tiles);
            swap(east, blankRow, blankCol, blankRow, blankCol + 1);
            neighbors.add(new Board(east));
        }
        return neighbors;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            Board initial = new Board(tiles);
            System.out.println("hamming: " + initial.hamming());
            System.out.println("manhattan: " + initial.manhattan());

            for (Board neighbour : initial.neighbors()) {
                System.out.println("neighbour:");
                System.out.println(neighbour);
            }

            System.out.println(initial);
            System.out.println(initial.twin());
        }
    }
}
