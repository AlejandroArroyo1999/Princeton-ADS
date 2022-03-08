/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();
        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);
        checkException(sortedPoints);
        Point[] copyPoints = Arrays.copyOf(points, points.length);
        ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();
        for (Point point : sortedPoints) {
            double a;
            for (Point pointCompare : copyPoints) {
                a = pointCompare.slopeTo(point);
            }
            Arrays.sort(copyPoints, point.slopeOrder());
            int count = 1;
            double tmp = point.slopeTo(copyPoints[0]);
            for (int i = 1; i < copyPoints.length; i++) {
                if (point.slopeTo(copyPoints[i]) == tmp) {
                    count++;
                }
                else {
                    if (count >= 3) {
                        LineSegment tmpLine;
                        if (point.compareTo(copyPoints[i - 1]) > 0) {
                            tmpLine = new LineSegment(copyPoints[i - 1], point);
                        }
                        else {
                            tmpLine = new LineSegment(point, copyPoints[i - 1]);
                        }
                        if (!segmentList.contains(tmpLine)) segmentList.add(tmpLine);
                    }
                    tmp = point.slopeTo(copyPoints[i]);
                    count = 1;
                }
            }
        }
        segments = segmentList.toArray(new LineSegment[segmentList.size()]);
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
