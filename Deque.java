/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first, last;
    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> before;
    }

    // construct an empty deque
    public Deque() {
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Argument is null");
        Node<Item> oldfirst = first;
        if (isEmpty()) {
            first = new Node<Item>();
            first.item = item;
            last = first;
        }
        else {
            first = new Node<Item>();
            first.item = item;
            oldfirst.before = first;
            first.next = oldfirst;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Argument is null");
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else {
            oldlast.next = last;
            last.before = oldlast;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("No items to remove");
        Item item = first.item;
        if (first.next == null) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
        }
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("No items to remove");
        Item item = last.item;
        if (last.before == null) {
            first = null;
            last = null;
        }
        else {
            last = last.before;
        }
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Can't remove");
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more items to return");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> collection = new Deque<String>();
        int action;
        String item;
        while (!StdIn.isEmpty()) {
            action = StdIn.readInt();
            if (action == 1) {
                item = StdIn.readString();
                collection.addFirst(item);
            }
            else if (action == 2) {
                item = StdIn.readString();
                collection.addLast(item);
            }
            else if (action == 3) {
                StdOut.println(collection.removeFirst());
            }
            else if (action == 4) {
                StdOut.println(collection.removeLast());
            }
        }
        for (String s : collection)
            StdOut.println(s);
    }

}

