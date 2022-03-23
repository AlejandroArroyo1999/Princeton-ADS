/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

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
        Board tmp = this;
        tmp.swap(tmp.posx, tmp.posy, tmp.posx - 1, tmp.posy);
        result.add(tmp);
        tmp = this;
        tmp.swap(tmp.posx, tmp.posy, tmp.posx + 1, tmp.posy);
        result.add(tmp);
        tmp = this;
        tmp.swap(tmp.posx, tmp.posy, tmp.posx, tmp.posy - 1);
        result.add(tmp);
        tmp = this;
        tmp.swap(tmp.posx, tmp.posy, tmp.posx, tmp.posy + 1);
        result.add(tmp);
        return result;
    }

    /*
    // a board that is obtained by exchanging any pair of tiles
    public Board twin()

     */
    private void swap(int ax, int ay, int bx, int by) {
        int tmp = this.square[ax][ay];
        this.square[ax][ay] = this.square[bx][by];
        this.square[bx][by] = tmp;
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
