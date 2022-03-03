/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int returnValues = Integer.parseInt(args[0]);
        RandomizedQueue<String> obj = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String pos = StdIn.readString();
            obj.enqueue(pos);
        }
        for (int i = 0; i < returnValues; i++) {
            StdOut.println(obj.dequeue());
        }
    }
}
