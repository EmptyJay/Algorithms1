import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        int numReturn = Integer.parseInt(args[0]);

        String[] words = StdIn.readAllStrings();

        for (int i = 0; i < words.length; i++) {
            rq.enqueue(words[i]);
        }

        for (int i = 0; i < numReturn; i++) {
            StdOut.println(rq.dequeue());
        }

    }
}
