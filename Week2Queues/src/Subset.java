public class Subset {
    public static void main(String[] args) {
        /*Write a client program Subset.java that takes a command-line integer k; reads in a sequence of N strings from
        standard input using StdIn.readString(); and prints out exactly k of them, uniformly at random. Each item from
        the sequence can be printed out at most once. You may assume that 0 ≤ k ≤ N, where N is the number of string
        on standard input.*/

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        int numberOfItems;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.length() > 0) {
                randomizedQueue.enqueue(item);
            } else {
                break;
            }
        }

        numberOfItems = Integer.parseInt(args[0]);
        for (int i = 0; i<numberOfItems; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}
