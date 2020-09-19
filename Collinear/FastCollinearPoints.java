import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    public FastCollinearPoints(Point[] points){
        checkNull(points);
        Point[] pointsorted = points.clone();
        Arrays.sort(pointsorted);
        checkDuplicate(points);
        final int N = pointsorted.length;
        final List<LineSegment> maxLineSegments = new LinkedList<>();
        for(int i=0;i<N;i++){
            Point p = pointsorted[i];
            Point[] sortedBySlope = pointsorted.clone();
            Arrays.sort(sortedBySlope, p.slopeOrder());
            int x = 1;
            while (x < N) {

                LinkedList<Point> c = new LinkedList<>();
                final double SLOPE_REF = p.slopeTo(sortedBySlope[x]);
                do {
                    c.add(sortedBySlope[x++]);
                } while (x < N && p.slopeTo(sortedBySlope[x]) == SLOPE_REF);

                if (c.size() >= 3
                        && p.compareTo(c.peek()) < 0) {
                    Point min = p;
                    Point max = c.removeLast();
                    maxLineSegments.add(new LineSegment(min, max));
                }
            }
        }
        lineSegments = maxLineSegments.toArray(new LineSegment[0]);
    }

    private void checkNull(Point[] points) {
        if (points == null) {
            throw new NullPointerException("The array is null.");
        }
        for (Point p : points) {
            if (p == null) {
                throw new NullPointerException(
                        "The array contains null element.");
            }
        }
    }

    private void checkDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate(s) found.");
            }
        }
    }

    /**
     * The number of line segments.
     */
    public int numberOfSegments() {
        return lineSegments.length;
    }

    /**
     * The line segments.
     */
    public LineSegment[] segments() {
        return lineSegments.clone();
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
