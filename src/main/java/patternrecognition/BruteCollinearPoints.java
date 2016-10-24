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
