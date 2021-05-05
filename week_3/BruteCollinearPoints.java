import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {

    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }

        int n = points.length;
        Arrays.sort(points);

        for (int i = 0; i < n - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        List<LineSegment> list = new LinkedList<>();
        for (int i = 0; i < n - 3; i++) {
            Point p = points[i];

            for (int ii = i + 1; ii < n - 2; ii++) {
                Point q = points[ii];
                double slopePq = p.slopeTo(q);

                for (int iii = ii + 1; iii < n - 1; iii++) {
                    Point r = points[iii];
                    double slopePr = p.slopeTo(r);
                    if (slopePq == slopePr) {

                        for (int iiii = iii + 1; iiii < n; iiii++) {
                            Point s = points[iiii];
                            double slopePs = p.slopeTo(s);
                            if (slopePq == slopePs) {
                                list.add(new LineSegment(p, s));
                            }
                        }
                    }
                }
            }
        }
        lineSegments = list.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() { // the number of line segments
        return lineSegments.length;
    }

    public LineSegment[] segments() { // the line segments
        return Arrays.copyOf(lineSegments, numberOfSegments());
    }

    /**
     * Simple client provided by Princeton University.
     */
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
