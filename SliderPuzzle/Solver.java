/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.List;

public class Solver {
    private LinkedList<Board> seqOrig = new LinkedList<Board>();
    private LinkedList<Board> seqCopy = new LinkedList<Board>();
    private int moves = 0;
    private boolean solvable = false;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> posibOrig = new MinPQ<SearchNode>();
        MinPQ<SearchNode> posibCopy = new MinPQ<SearchNode>();

        posibOrig.insert(new SearchNode(initial, moves++, null));
        posibCopy.insert(new SearchNode(initial.twin(), moves++, null));

        SearchNode tmpOrig = posibOrig.delMin();
        SearchNode tmpCopy = posibCopy.delMin();

        while (!(tmpOrig.End() && tmpCopy.End())) {
            for (Board i : tmpOrig.NextPossibilities())
                posibOrig.insert(new SearchNode(i, moves++, tmpOrig.current));
            for (Board i : tmpCopy.NextPossibilities())
                posibCopy.insert(new SearchNode(i, moves++, tmpCopy.current));
            seqOrig.add(tmpOrig.current);
            seqCopy.add(tmpCopy.current);
            tmpOrig = posibOrig.delMin();
            tmpCopy = posibCopy.delMin();
            solvable = tmpOrig.End();
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) return moves;
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) return seqOrig;
        return null;
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

    private class SearchNode implements Comparable<SearchNode> {
        private Board current;
        private int nofMoves;
        private Board previous;

        public SearchNode(Board c, int n, Board p) {
            this.current = c;
            this.nofMoves = n;
            this.previous = p;
        }

        public Board[] NextPossibilities() {
            Iterable<Board> r = this.current.neighbors();
            Board[] result = new Board[((List<Board>) r).size() - 1];
            int f = 0;
            for (Board i : r) {
                if (!i.equals(this.previous)) {
                    break;
                }
                result[f++] = i;
            }
            return result;
        }

        public boolean End() {
            return this.current.isGoal();
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(this.nofMoves + this.current.manhattan(),
                                   that.nofMoves + that.current.manhattan());
        }
    }

}
