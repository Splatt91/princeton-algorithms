/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class QuickSortDijkstra3Way {


    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }

        int i = lo;
        int lt = lo;
        int gt = hi;
        Comparable v = a[lo];

        while (i <= gt) {
            int cmp = a[i].compareTo(v);

            if (cmp < 0) {
                exch(a, i++, lt++);
            }
            else if (cmp > 0) {
                exch(a, i, gt--);
            }
            else {
                i++;
            }
        }

        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[] { 3, 5, 2, 7, 2, 12, 3, 1 };
        sort(a);
        for (int elem : a) {
            System.out.println(elem);
        }
    }
}
