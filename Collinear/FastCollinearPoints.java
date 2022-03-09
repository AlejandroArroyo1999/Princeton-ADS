/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        checkException(sortedPoints);

        int N = points.length;
        List<LineSegment> segmentList = new LinkedList<>();

        for (Point point : sortedPoints) {
            Point[] pointsBySlope = sortedPoints.clone();
            Arrays.sort(pointsBySlope, point.slopeOrder());

            int x = 1;
            while (x < N) {

                LinkedList<Point> candidates = new LinkedList<>();
                double tmpSlope = point.slopeTo(pointsBySlope[x]);
                do {
                    candidates.add(pointsBySlope[x++]);
                } while (x < N && point.slopeTo(pointsBySlope[x]) == tmpSlope);

                if (candidates.size() >= 3 && point.compareTo(candidates.peek()) < 0) {
                    Point max = candidates.removeLast();
                    segmentList.add(new LineSegment(point, max));
                }
            }
        }
        segments = segmentList.toArray(new LineSegment[0]);
    }

    public LineSegment[] segments() {
        return segments;
    }

    public int numberOfSegments() {
        return segments.length;
    }

    private void checkException(Point[] copy) {
        for (int i = 0; i < copy.length - 1; i++) {
            if (copy[i] == null)
                throw new java.lang.IllegalArgumentException();
            if (copy[i] == copy[i + 1])
                throw new java.lang.IllegalArgumentException();
        }
        if (copy[copy.length - 1] == null)
            throw new java.lang.IllegalArgumentException();
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
        StdDraw.setXscale(-200, 32768);
        StdDraw.setYscale(-200, 32768);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.RED);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.0005);


        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
