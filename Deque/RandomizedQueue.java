/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int n;

    // construct an empty randomized queue

    public RandomizedQueue() {
        array = (Item[]) new Object[1];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (n == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Argument is null");
        if (n == array.length) resize(2 * array.length);
        array[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("No items to remove");
        int position = StdRandom.uniform(n);
        Item item = array[position];
        if (position == n - 1) array[n - 1] = null;
        else {
            array[position] = array[n - 1];
            array[n - 1] = null;
        }
        n--;
        if (n > 0 && n == array.length / 4) resize(array.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("No items to sample");
        int position = StdRandom.uniform(n);
        Item item = array[position];
        return item;
    }

    // resize function
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            copy[i] = array[i];
        array = copy;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int i = n;
        private int[] order = StdRandom.permutation(n);

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException("Can't remove");
        }

        public Item next() {
            if (i == 0) throw new NoSuchElementException("No items to sample");
            return array[order[--i]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> collection = new RandomizedQueue<String>();
        int action;
        String item;
        while (!StdIn.isEmpty()) {
            action = StdIn.readInt();
            if (action == 1) {
                item = StdIn.readString();
                collection.enqueue(item);
            }
            else if (action == 2) {
                StdOut.println(collection.dequeue());
            }
            else if (action == 3) {
                StdOut.println(collection.sample());
            }
            else break;
        }
        for (String s : collection)
            StdOut.println(s);
    }
}
