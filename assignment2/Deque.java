// import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node
    {
        private Item item;
        private Node next;
        private Node prev;
    }

    private Node head, tail;
    private int count;

    public Deque() { // construct an empty deque
        head = null;
        tail = null;
        count = 0;
    }

    public boolean isEmpty() { // is the deque empty?
        return (count == 0);
    }

    public int size() { // return the number of items on the deque
        return count;
    }

    public void addFirst(Item item) { // add the item to the front
        if (item == null) throw new NullPointerException();

        Node newHead = new Node();

        newHead.item = item;       // store the item

        if (head != null) {         // list is not empty
            newHead.next = head;    // point this node's next to current head
            head.prev = newHead;    // point current head's prev to this node
            newHead.prev = null;    // this node has no prev
            head = newHead;         // move head to this node

        } else {                    // this is the only node
            head = newHead;         // head and tail are both this node
            tail = newHead;
            newHead.prev = null;    // this node has no next or prev
            newHead.next = null;
        }

        count++;
    }

    public void addLast(Item item) { // add the item to the end
        if (item == null) throw new NullPointerException();

        Node newTail = new Node();

        newTail.item = item;        // store the item

        if (tail != null) {          // list is not empty
            newTail.prev = tail;    // point this node's prev to current tail
            tail.next = newTail;    // point current tail's next to this node
            newTail.next = null;    // this node has no next
            tail = newTail;         // move tail to this node

        } else {                    // this is the only node
            head = newTail;         // head and tail are both this node
            tail = newTail;
            newTail.prev = null;    // this node has no next or prev
            newTail.next = null;    
        }

        count++;
    }

    public Item removeFirst() { // remove and return the item from the front
        if (head == null) throw new java.util.NoSuchElementException();

        Item item = head.item;      // store first node item for return

        head = head.next;         // advance the head pointer one element

        if (head != null) {     // there's still an element in the list
            head.prev = null;      // remove reference to old head

        } else {                // we've remove the last node
            tail = null;
        }

        count--;
        
        return item;
    }

    public Item removeLast() { // remove and return the item from the end
        if (tail == null) throw new java.util.NoSuchElementException();

        Item item = tail.item;      // store last node item for return

        tail = tail.prev;           // back up the tail pointer one element

        if (tail != null) {         // there's still an element in the list
            tail.next = null;       // remove reference to old tail

        } else {                    // we've remove the last node
            head = null;
        }

        count--;
        
        return item;
    }

    public Iterator<Item> iterator() { // return an iterator over items in order from front to end
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() { 
            throw new UnsupportedOperationException();
        } // not suported

        public Item next()
        {
            if (current == null)
                throw new java.util.NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) { // unit testing
        Deque<Integer> tD = new Deque<Integer>();

        // In in = new In(args[0]);

        // while (!in.isEmpty()) {
        while (true) {

            // String command = in.readString();
            String command = StdIn.readString();
            int inItem;

            switch (command) {
                case "ie":
                    StdOut.println("isEmpty: " + tD.isEmpty());
                    break;
                case "sz":
                    StdOut.println("size: " + tD.size());
                    break;
                case "af":
                    inItem = StdIn.readInt();
                    StdOut.println("addFirst: " + inItem);
                    tD.addFirst(inItem);
                    break;
                case "al":
                    inItem = StdIn.readInt();
                    StdOut.println("addLast: " + inItem);
                    tD.addLast(inItem);
                    break;
                case "rf":
                    StdOut.println("removeFirst: " + tD.removeFirst());
                    break;
                case "rl":
                    StdOut.println("removeLast: " + tD.removeLast());
                    break;
                case "it":
                    for (int i : tD)
                        StdOut.println(i);
                    break;
            }
        }

    }
}
