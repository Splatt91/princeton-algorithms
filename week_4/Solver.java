/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Solver {
    private boolean isSolvable;
    private final int moves;
    private final LinkedList<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> pqTwin = new MinPQ<>();
        pq.insert(new SearchNode(initial, 0, null));
        pqTwin.insert(new SearchNode(initial.twin(), 0, null));
        while (true) {
            SearchNode searchNode = pq.delMin();
            if (searchNode.board.isGoal()) {
                this.isSolvable = true;
                this.moves = searchNode.moves;
                this.solution = new LinkedList<>();
                while (searchNode != null) {
                    solution.add(0, searchNode.board);
                    searchNode = searchNode.prev;
                }
                break;
            }
            for (Board neighour : searchNode.board.neighbors()) {
                if (searchNode.prev != null &&
                        searchNode.prev.board.equals(neighour)) {
                    continue;
                }
                pq.insert(new SearchNode(neighour, searchNode.moves + 1, searchNode));
            }
            searchNode = pqTwin.delMin();
            if (searchNode.board.isGoal()) {
                isSolvable = false;
                moves = -1;
                solution = null;
                break;
            }
            for (Board neighour : searchNode.board.neighbors()) {
                if (searchNode.prev != null &&
                        searchNode.prev.board.equals(neighour)) {
                    continue;
                }
                pqTwin.insert(new SearchNode(neighour, searchNode.moves + 1, searchNode));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return solution;
    }

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        SearchNode prev;
        int priority;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.priority = this.board.manhattan() + moves;
        }

        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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
