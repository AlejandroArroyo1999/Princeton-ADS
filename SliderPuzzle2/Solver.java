/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode currentOrig;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> posibOrig = new MinPQ<SearchNode>();
        MinPQ<SearchNode> posibCopy = new MinPQ<SearchNode>();

        currentOrig = new SearchNode(initial, null, 0);
        SearchNode currentCopy = new SearchNode(initial.twin(), null, 0);
        SearchNode previousOrig = currentOrig.getPreviousNode();
        SearchNode previousCopy = currentOrig.getPreviousNode();
        while (!currentOrig.getCurrent().isGoal() && !currentCopy.getCurrent().isGoal()) {
            for (Board i : currentOrig.getCurrent().neighbors()) {
                if (previousOrig != null) {
                    if (!previousOrig.getCurrent().equals(i))
                        posibOrig
                                .insert(new SearchNode(i, currentOrig, currentOrig.getMoves() + 1));
                }
                else {
                    posibOrig
                            .insert(new SearchNode(i, currentOrig, currentOrig.getMoves() + 1));
                }
            }

            for (Board i : currentCopy.getCurrent().neighbors()) {
                if (previousCopy != null) {
                    if (!previousCopy.getCurrent().equals(i))
                        posibCopy
                                .insert(new SearchNode(i, currentCopy, currentCopy.getMoves() + 1));
                }
                else {
                    posibCopy
                            .insert(new SearchNode(i, currentCopy, currentCopy.getMoves() + 1));
                }
            }

            currentOrig = posibOrig.delMin();
            currentCopy = posibCopy.delMin();
            previousOrig = currentOrig.getPreviousNode();
            previousCopy = currentCopy.getPreviousNode();
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return currentOrig.getCurrent().isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return currentOrig.getMoves();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        SearchNode temp = new SearchNode(currentOrig.getCurrent(),
                                         currentOrig.getPreviousNode(),
                                         currentOrig.getMoves());
        Stack<Board> result = new Stack<Board>();
        do {
            result.push(temp.getCurrent());
            temp = temp.getPreviousNode();
        } while (temp != null);
        return result;
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

    public class SearchNode implements Comparable<SearchNode> {
        private Board current;
        private int priority;
        private int moves;
        private SearchNode previousNode;

        public SearchNode(Board c, SearchNode p, int m) {
            current = c;
            previousNode = p;
            moves = m;
            priority = m + c.manhattan();
        }

        public Board getCurrent() {
            return current;
        }

        public SearchNode getPreviousNode() {
            return previousNode;
        }

        public int getMoves() {
            return moves;
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(priority, that.priority);
        }
    }
}
