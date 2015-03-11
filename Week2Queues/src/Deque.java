import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;          // size of the stack
    private Node pre;       // sentinel before first item
    private Node post;     // sentinel after last item

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public Deque() {                           // construct an empty deque
        pre = new Node();
        post = new Node();
        pre.next = post;
        post.prev = pre;
        N = 0;
    }
    public boolean isEmpty() {                // is the deque empty?
        return N == 0;
    }
    public int size() {                         // return the number of items on the deque
        return N;
    }
    public void addFirst(Item item) {         // add the item to the front
        if (item == null) {
            throw new NullPointerException();
        } else {
            Node first = pre.next;
            Node x = new Node();
            x.item = item;
            x.prev = pre;
            x.next = first;
            pre.next = x;
            first.prev = x;
            N++;
        }
    }
    public void addLast(Item item) {          // add the item to the end
        if (item == null) {
            throw new NullPointerException();
        } else {
            Node last = post.prev;
            Node x = new Node();
            x.item = item;
            x.next = post;
            x.prev = last;
            post.prev = x;
            last.next = x;
            N++;
        }
    }
    public Item removeFirst() {               // remove and return the item from the front
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = pre.next.item;
        Node newPre = new Node();
        newPre.next = pre.next.next;
        pre.next.next.prev = newPre;
        pre = null;
        pre = newPre;
        N--;

        return item;
    }
    public Item removeLast() {                // remove and return the item from the end
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = post.prev.item;
        Node newPost = new Node();
        newPost.prev = post.prev.prev;
        post.prev.prev.next = newPost;
        post = null;
        post = newPost;
        N--;

        return item;
    }

    private class DequeIterator implements Iterator {
        private Node current = pre.next;
        private int index = 0;
        @Override
        public boolean hasNext() {
            return index < N;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            index++;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator iterator() {        // return an iterator over items in order from front to end
        return new DequeIterator();
    }

    public static void main(String[] args) {   // unit testing
    Deque<Integer> integers = new Deque<Integer>();
        System.out.println(integers);
        integers.addFirst(1);
        System.out.println(integers);
        integers.addFirst(2);
        System.out.println(integers);
        integers.addFirst(3);
        System.out.println(integers);
        integers.addLast(4);
        System.out.println(integers);
        integers.addLast(5);
        System.out.println(integers);
        System.out.println("             ");

        for (int items : integers) {
            System.out.println(items);
        }

        System.out.println("removing first: " + integers.removeFirst());
        System.out.println(integers);
        System.out.println("removing last: " + integers.removeLast());
        System.out.println(integers);
        integers.removeLast();
        System.out.println(integers);
        integers.removeLast();
        integers.removeLast();
        System.out.println(integers);
    }
}
