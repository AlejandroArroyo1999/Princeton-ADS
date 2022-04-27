/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int[][] square;
    private final int n;
    private int blankX;
    private int blankY;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        square = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                square[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(n);
        result.append('\n');
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.append(' ');
                result.append(square[i][j]);
            }
            result.append('\n');
        }
        return result.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int displacedTiles = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int correctTile = (i * n) + j + 1;
                int tile = square[i][j];
                if ((tile != 0) && (correctTile != tile)) displacedTiles++;
            }
        }
        return displacedTiles;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int man = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = square[i][j];
                man += (tile / n) - i + (tile % n) - j;
            }
        }
        return man;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int correctTile = (i * n) + j + 1;
                int tile = square[i][j];
                if ((tile != 0) && (tile != correctTile)) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board yBoard = (Board) y;
        if (yBoard.n != n) return false;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (yBoard.square[i][j] != square[i][j]) return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> result = new Queue<Board>();
        swapUp(result);
        swapDown(result);
        swapRight(result);
        swapLeft(result);
        return result;
    }

    private void swapUp(Queue<Board> result) {
        if (blankX == 0) return;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == blankX) && (j == blankY)) tiles[i][j] = square[blankX - 1][j];
                else if ((i == blankX - 1) && (j == blankY)) tiles[i][j] = square[blankX][j];
                else tiles[i][j] = square[i][j];
            }
        }
        Board neighbor = new Board(tiles);
        result.enqueue(neighbor);
    }

    private void swapDown(Queue<Board> result) {
        if (blankX == n - 1) return;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == blankX) && (j == blankY)) tiles[i][j] = square[blankX + 1][j];
                else if ((i == blankX + 1) && (j == blankY)) tiles[i][j] = square[blankX][j];
                else tiles[i][j] = square[i][j];
            }
        }
        Board neighbor = new Board(tiles);
        result.enqueue(neighbor);
    }

    private void swapLeft(Queue<Board> result) {
        if (blankY == 0) return;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == blankX) && (j == blankY)) tiles[i][j] = square[i][blankY - 1];
                else if ((i == blankX) && (j == blankY - 1)) tiles[i][j] = square[i][blankY];
                else tiles[i][j] = square[i][j];
            }
        }
        Board neighbor = new Board(tiles);
        result.enqueue(neighbor);
    }

    private void swapRight(Queue<Board> result) {
        if (blankY == n - 1) return;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == blankX) && (j == blankY)) tiles[i][j] = square[i][blankY + 1];
                else if ((i == blankX) && (j == blankY + 1)) tiles[i][j] = square[i][blankY];
                else tiles[i][j] = square[i][j];
            }
        }
        Board neighbor = new Board(tiles);
        result.enqueue(neighbor);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int x1 = StdRandom.uniform(3);
        int y1 = StdRandom.uniform(3);
        int x2 = StdRandom.uniform(3);
        int y2 = StdRandom.uniform(3);
        while ((x1 != x2) || (y1 != y2)) {
            x1 = StdRandom.uniform(3);
            y1 = StdRandom.uniform(3);
            x2 = StdRandom.uniform(3);
            y2 = StdRandom.uniform(3);
        }
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) tiles[i][j] = square[i][j];
        int tmp = tiles[x1][y1];
        tiles[x1][y1] = tiles[x2][y2];
        tiles[x2][y2] = tmp;
        return new Board(tiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        for (Board i : initial.neighbors()) {
            StdOut.print(i.toString());
            StdOut.print(i.hamming());
            StdOut.print('\n');
        }
    }
}
