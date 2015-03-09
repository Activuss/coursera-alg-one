import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    public Deque() {                           // construct an empty deque

    }
    public boolean isEmpty() {                // is the deque empty?
        return false;
    }
    public int size() {                         // return the number of items on the deque
        return 0;
    }
    public void addFirst(Item item) {         // add the item to the front
        if (item == null) {
            throw new NullPointerException();
        } else {

        }
    }
    public void addLast(Item item) {          // add the item to the end
        if (item == null) {
            throw new NullPointerException();
        } else {

        }
    }
    public Item removeFirst() {               // remove and return the item from the front
        return null;
    }
    public Item removeLast() {                // remove and return the item from the end
        return null;
    }
    public Iterator<Item> iterator() {        // return an iterator over items in order from front to end
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
