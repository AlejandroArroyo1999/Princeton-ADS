/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private MinPQ<SearchNode> posSolutions = new MinPQ<SearchNode>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable()

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()

    // test client (see below)
    public static void main(String[] args) {
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

        public int compareTo(SearchNode that) {
            return Integer.compare(this.nofMoves, that.nofMoves);
        }
    }

}
