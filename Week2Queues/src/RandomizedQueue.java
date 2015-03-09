import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    public RandomizedQueue() {                // construct an empty randomized queue

    }
    public boolean isEmpty() {                // is the queue empty?
        return false;
    }
    public int size() {                        // return the number of items on the queue
        return 0;
    }
    public void enqueue(Item item) {          // add the item
        if (item == null) {
            throw new NullPointerException();
        } else {

        }

    }
    public Item dequeue() {                    // remove and return a random item
        if (size() == 0) {
            throw new NoSuchElementException();
        } else {

        }
        return null;
    }
    public Item sample() {                     // return (but do not remove) a random item
        if (size() == 0) {
            throw new NoSuchElementException();
        } else {

        }
        return null;
    }
    public Iterator<Item> iterator() {        // return an independent iterator over items in random order
        Iterator iterator = new Iterator() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {

                }
                return null;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return iterator;
    }
    public static void main(String[] args) {   // unit testing
        
    }
}