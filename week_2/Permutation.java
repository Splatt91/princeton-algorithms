
import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        String strSeq = StdIn.readString();
        randomizedQueue.enqueue(strSeq);
        while (!StdIn.isEmpty()) {
            strSeq = StdIn.readString();
            randomizedQueue.enqueue(strSeq);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(randomizedQueue.dequeue());
        }
    }
}
