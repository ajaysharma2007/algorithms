package patternrecognition;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by ajay on 23/10/16.
 */
public class BruteCollinearPoints {

    private static int numberOfSegments = 0;
    private Point[] pointSet;
    private LineSegment[] collinearLineSegArr;

    public BruteCollinearPoints(Point[] points) {
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
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("/Users/ajay/Downloads/Coursera/Algorithms Part I" +
                "/Week-3/1-Merge Sort/Assignment/collinear/rs1423.txt");
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
        double[] slopes = new double[pointSet.length];
        Arrays.sort(pointSet);
        for (int i = 0; i < pointSet.length; i++) {
            for (int j = i + 1; j < pointSet.length; j++) {
                double slopeOneTwo = pointSet[i].slopeTo(pointSet[j]);
                for (int k = j + 1; k < pointSet.length; k++) {
                    double slopeTwoThree = pointSet[i].slopeTo(pointSet[k]);
                    if (slopeOneTwo == slopeTwoThree) {
                        for (int l = k + 1; l < pointSet.length; l++) {
                            double slopeThreeFour = pointSet[k].slopeTo(pointSet[l]);
                            if (slopeOneTwo == slopeThreeFour) {
                                if (numberOfSegments == collinearLineSegArr.length) {
                                    resize(2 * collinearLineSegArr.length);
                                }
                                collinearLineSegArr[numberOfSegments++] =
                                        new LineSegment(pointSet[i], pointSet[l]);
                            }
                        }
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
