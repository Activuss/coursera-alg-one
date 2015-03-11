import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int N;            // number of elements on stack
    private int lastDeletedIndex;

    public RandomizedQueue() {                // construct an empty randomized queue
        a = (Item[]) new Object[2];

    }

    public boolean isEmpty() {                // is the queue empty?
        return N == 0;
    }

    public int size() {                        // return the number of items on the queue
        return N;
    }

    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        int copiedItems = 0;
        int counter = 0;
        while (copiedItems != N) {
            if (a[counter] != null) {
                temp[copiedItems++] = a[counter];
            }
            counter++;
        }
        a = temp;
    }

    public void enqueue(Item item) {          // add the item
        if (item == null) {
            throw new NullPointerException();
        }
        if (N == a.length) resize(2 * a.length);    // double size of array if necessary
        Item existedItem = a[N];
        if (existedItem == null) {
            a[N++] = item;                            // add item
        } else {
            a[lastDeletedIndex] = item;
        }

    }

    public Item dequeue() {                    // remove and return a random item
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        Item item;
        int index;
        do {
            index = StdRandom.uniform(N);
            item = a[index];
        } while (item == null);
        lastDeletedIndex = index;
        a[index] = null;                              // to avoid loitering
        N--;
        // shrink size of array if necessary
        if (N > 0 && N == a.length / 4) resize(a.length / 2);
        return item;
    }

    public Item sample() {                     // return (but do not remove) a random item
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        Item item;
        do {
            item = a[StdRandom.uniform(N)];
        } while (item == null);

        return item;
    }

    private class RandQueueIterator implements Iterator {

        private final int[] arrayIndex = new int[N];
        private int count = 0;

        public RandQueueIterator() {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != null) {
                    arrayIndex[count++] = i;
                }
            }
            StdRandom.shuffle(arrayIndex);

        }

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            int index = arrayIndex[--count];
            return a[index];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {        // return an independent iterator over items in random order
        return new RandQueueIterator();
    }

    public static void main(String[] args) {   // unit testing
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(4);
        randomizedQueue.enqueue(5);
        randomizedQueue.enqueue(6);
        randomizedQueue.enqueue(6);
        randomizedQueue.enqueue(6);
        randomizedQueue.enqueue(6);
        randomizedQueue.enqueue(6);
        randomizedQueue.enqueue(6);
        randomizedQueue.enqueue(6);
        randomizedQueue.enqueue(6);
        randomizedQueue.enqueue(6);
        randomizedQueue.enqueue(6);
        System.out.println(randomizedQueue.sample());
        System.out.println(randomizedQueue.sample());
        System.out.println(randomizedQueue.sample());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println("-------");
        for (int i : randomizedQueue) {
            System.out.println(i);
            System.out.println("--nested start-----");
            for (int j : randomizedQueue) {
                System.out.print(j);
            }
            System.out.println("---end----");
        }
    }
}