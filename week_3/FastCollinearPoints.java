import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {

    private final LineSegment[] lineSegments;

    public FastCollinearPoints(
            Point[] points) { // finds all line segments containing 4 or more points

        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }

        int n = points.length;
        Point[] sortedPoints = Arrays.copyOf(points, n);
        Arrays.sort(sortedPoints);
        for (int i = 0; i < n - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        List<LineSegment> list = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            Point p = points[i];
            Point[] pointsBySlope = Arrays.copyOf(points, n);
            Arrays.sort(pointsBySlope, p.slopeOrder());
            int j = 1;
            while (j < n) {
                double slopeRef = p.slopeTo(pointsBySlope[j]);
                LinkedList<Point> candidates = new LinkedList<>();
                candidates.add(p);
                do {
                    candidates.add(pointsBySlope[j]);
                    ++j;
                } while ((j < n) && (p.slopeTo(pointsBySlope[j]) == slopeRef));

                Collections.sort(candidates);
                if ((candidates.size() >= 4)) {
                    if (p == candidates.getFirst()) {
                        list.add(new LineSegment(candidates.getFirst(), candidates.getLast()));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
