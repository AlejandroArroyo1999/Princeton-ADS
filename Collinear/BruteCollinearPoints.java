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

public class BruteCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        if (this.checkNull(points)) throw new IllegalArgumentException();
        ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        if (this.checkDuplicates(pointsCopy)) throw new IllegalArgumentException();
        int n = points.length;
        for (int p = 0; p < n - 3; p++) {
            for (int q = p + 1; q < n - 2; q++) {
                double first = pointsCopy[p].slopeTo(pointsCopy[q]);
                for (int r = q + 1; r < n - 1; r++) {
                    double second = pointsCopy[p].slopeTo(pointsCopy[r]);
                    for (int s = r + 1; s < n; s++) {
                        double third = pointsCopy[p].slopeTo(pointsCopy[s]);
                        if ((first == second) && (second == third)) {
                            LineSegment temp = new LineSegment(pointsCopy[p], pointsCopy[s]);
                            if (!segmentList.contains(temp)) {
                                segmentList.add(temp);
                            }
                        }
                    }
                }
            }
        }
        segments = segmentList.toArray(new LineSegment[segmentList.size()]);
    }

    private boolean checkNull(Point[] array) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            if (array[i] == null) return true;
        }
        return false;
    }

    private boolean checkDuplicates(Point[] array) {
        int n = array.length;
        Point temp = array[0];
        for (int i = 1; i < n; i++) {
            if (array[i] == temp) return true;
            temp = array[i];
        }
        return false;
    }

    public LineSegment[] segments() {
        return segments;
    }

    public int numberOfSegments() {
        return segments.length;
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
