/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class Board {
    private int[][] square;
    private int n;
    private int posx, posy;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.square = new int[tiles.length][tiles[0].length];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                this.square[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    this.posx = i;
                    this.posy = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.n + "\n");
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                result.append(this.square[i][j]);
                result.append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {
        int outPlace = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.square[i][j] != ((i * this.n) + j)) outPlace++;
            }
        }
        return outPlace;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                int pos = this.square[i][j];
                if (pos != ((i * this.n) + j)) {
                    while (pos > this.n) {
                        pos -= this.n;
                        distance++;
                    }
                    distance += pos;
                }
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.square[i][j] != ((i * this.n) + j)) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y.getClass() != this.getClass()) return false;
        Board x = (Board) y;
        if (this.n != x.n) return false;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.square[i][j] != x.square[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> result = new ArrayList<Board>();
        // Fill in the array
        Board tmp = this.swap(this.posx, this.posy, this.posx - 1, this.posy);
        if (tmp != null)
            result.add(tmp);
        tmp = this.swap(this.posx, this.posy, this.posx + 1, this.posy);
        if (tmp != null)
            result.add(tmp);
        tmp = this.swap(this.posx, this.posy, this.posx, this.posy - 1);
        if (tmp != null)
            result.add(tmp);
        tmp = this.swap(this.posx, this.posy, this.posx, this.posy + 1);
        if (tmp != null)
            result.add(tmp);
        return result;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board r = this;
        int[] Origin = new int[] { StdRandom.uniform(0, 3), StdRandom.uniform(0, 3) };
        int[] Final = new int[] { StdRandom.uniform(0, 3), StdRandom.uniform(0, 3) };
        while (Origin == Final || Origin[0] == Origin[1] || Final[0] == Final[1]) {
            Origin = new int[] { StdRandom.uniform(0, 3), StdRandom.uniform(0, 3) };
            Final = new int[] { StdRandom.uniform(0, 3), StdRandom.uniform(0, 3) };
        }

        return r.swap(Origin[0], Origin[1], Final[0], Final[1]);
    }


    private Board swap(int ax, int ay, int bx, int by) {
        if ((ax < 0) || (ay < 0) || (ax >= this.n) || (ay >= this.n) ||
                (bx < 0) || (by < 0) || (bx >= this.n) || (by >= this.n))
            return null;
        Board result = this;
        int tmp = result.square[ax][ay];
        result.square[ax][ay] = result.square[bx][by];
        result.square[bx][by] = tmp;
        return result;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int len = in.readInt();
        int[][] tiles = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) tiles[i][j] = in.readInt();
        }
        Board result = new Board(tiles);
        StdOut.println(result.toString());
    }

}
