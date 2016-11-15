// import edu.princeton.cs.algs4.In;
// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;
    private int capacity;

    public RandomizedQueue() {                 // construct an empty randomized queue
        size = 0;
        capacity = 1;
        queue = (Item[]) new Object[capacity];
    }

    public boolean isEmpty() {                 // is the queue empty?
        return size == 0;
    }

    public int size() {                        // return the number of items on the queue
        return size;
    }

    public void enqueue(Item item) {           // add the item
        if (item == null) throw new java.lang.NullPointerException();

        if (size + 1 > capacity) resizeLarger();    // we're about to overfill the array

        queue[size++] = item;
    }

    private void resizeLarger() {
        capacity *= 2;      // double the capacity

        Item[] newQueue = (Item[]) new Object[capacity];

        // copy all items to the new queue
        int index = 0;
        for (Item i : queue)
            newQueue[index++] = i;

        queue = newQueue;
    }

    public Item dequeue() {                    // remove and return a random item
        if (isEmpty()) throw new java.util.NoSuchElementException();

        int i = StdRandom.uniform(size);    // pick a random int from 0 to size-1

        Item ret = queue[i];        // save the item at random location for return
        queue[i] = queue[--size];     // move last element to random location, filling the hole
        queue[size] = null;         // blank out last element

        if (capacity / 4 > size)   // we're at 1/4 full or less
            resizeSmaller();        
        
        return ret;
    }

    private void resizeSmaller() {
        capacity /= 2;      // halve the capacity

        Item[] newQueue = (Item[]) new Object[capacity];

        // copy all items to the new queue
        int index = 0;
        for (int i = 0; i < capacity; i++) 
            newQueue[index++] = queue[i];

        queue = newQueue;
    }

    public Item sample() {                     // return (but do not remove) a random item
        if (isEmpty()) throw new java.util.NoSuchElementException();

        return queue[StdRandom.uniform(size)];
    }

    public Iterator<Item> iterator() {         // return an independent iterator over items in random order
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current = 0;
        private int[] shuffle = new int[size];

        public boolean hasNext() {
            if (current == 0) {
                for (int i = 0; i < size; i++)
                    shuffle[i] = i;     // initialize the shuffle to sequential

                StdRandom.shuffle(shuffle);     // shuffle the shuffle array
            }

            return current < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == 0) {
                for (int i = 0; i < size; i++)
                    shuffle[i] = i;     // initialize the shuffle to sequential

                StdRandom.shuffle(shuffle);     // shuffle the shuffle array
            }

            if (current >= size || size() == 0) throw new java.util.NoSuchElementException();

            return queue[shuffle[current++]]; // return element at the current random index and advance to the next random index
        }
    }



    public static void main(String[] args) {  // unit testing
    }
}
