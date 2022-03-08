/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
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

    public boolean checkNull(Point[] array) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            if (array[i] == null) return true;
        }
        return false;
    }

    public boolean checkDuplicates(Point[] array) {
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
        String filename = args[0];

        In in = new In(filename);
        int n = in.readInt();
        Point[] points = new Point[n];

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(-32768, 32768);
        StdDraw.setYscale(-32768, 32768);
        StdDraw.show();
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        StdDraw.setPenColor(Color.RED);
        LineSegment xAxis = new LineSegment(new Point(-32767, 0), new Point(32767, 0));
        xAxis.draw();
        LineSegment yAxis = new LineSegment(new Point(0, -32767), new Point(0, 32767));
        yAxis.draw();

        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(Color.BLACK);
        // read points from file
        for (int i = 0; i < n; i++) {
            int x, y;
            x = in.readInt();
            y = in.readInt();

            Point p = new Point(x, y);
            p.draw();
            points[i] = p;
        }

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
