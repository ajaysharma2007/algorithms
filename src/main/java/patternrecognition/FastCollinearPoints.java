package patternrecognition;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by ajay on 24/10/16.
 */
public class FastCollinearPoints {

    private static int numberOfSegments = 0;
    private Point[] pointSet;
    private LineSegment[] collinearLineSegArr;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("Please supply the points.");
        }
        for (Point point : points) {
            if (point == null) {
                throw new NullPointerException("None of the " +
                        "supplied points can be null.");
            }
        }
        this.pointSet = points;
        collinearLineSegArr = new LineSegment[pointSet.length];
    }     // finds all line segments containing 4 or more points

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("/Users/ajay/Downloads/Coursera/Algorithms Part I" +
                "/Week-3/1-Merge Sort/Assignment/collinear/input6.txt");
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

    private void resize(int capacity) {
        LineSegment[] newItemArr = new LineSegment[capacity];
        for (int i = 0; i < this.collinearLineSegArr.length; i++) {
            newItemArr[i] = collinearLineSegArr[i];
        }
        collinearLineSegArr = newItemArr;
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        for (int k = 0; k < pointSet.length - 3; k++) {
            Point point = pointSet[k];
            Arrays.sort(pointSet, k + 1, pointSet.length - 1, point.slopeOrder());
            int i = k + 1;

            boolean isContinue = false;
            for (int j = 0; j < k; j++) {
                if (point.slopeTo(pointSet[j]) == point.slopeTo(pointSet[i])) {
                    isContinue = true;
                }
            }

            if (isContinue) {
                continue;
            }

            while (i < pointSet.length - 2) {
                if (point.slopeTo(pointSet[i]) ==
                        point.slopeTo(pointSet[i + 1])
                        &&
                        point.slopeTo(pointSet[i]) ==
                                point.slopeTo(pointSet[i + 2])) {

                    int count = i + 3;
                    while (point.slopeTo(pointSet[i]) ==
                            point.slopeTo(pointSet[count])
                            &&
                            count < pointSet.length) {
                        count++;
                    }
                    i = count;

                    collinearLineSegArr[numberOfSegments++] =
                            new LineSegment(pointSet[i], pointSet[--count]);

                    if (numberOfSegments == collinearLineSegArr.length) {
                        resize(2 * collinearLineSegArr.length);
                    }

                }
            }
        }

        LineSegment[] finalCollinearLineSegs = null;
        int arrIndex = 0;
        finalCollinearLineSegs = numberOfSegments == 0 ? null
                : new LineSegment[numberOfSegments];
        for (LineSegment collinearLineSeg : collinearLineSegArr) {
            if (collinearLineSeg == null) {
                break;
            }
            finalCollinearLineSegs[arrIndex++] = collinearLineSeg;
        }

        return finalCollinearLineSegs;
    }
}
