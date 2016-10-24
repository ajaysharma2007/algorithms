package patternrecognition;

import java.util.Arrays;

/**
 * Created by ajay on 23/10/16.
 */
public class BruteCollinearPoints {

    private static final LineSegment[] NO_LINE_SEGMENT = {};
    private int numberOfSegments = 0;
    private Point[] pointSet;
    private LineSegment[] collinearLineSegArr;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("Please supply the points.");
        }
        pointSet = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            if (point == null) {
                throw new NullPointerException("None of the " +
                        "supplied points can be null.");
            }
            pointSet[i] = point;
        }
        Arrays.sort(pointSet);

        for (int i = 1; i < pointSet.length; i++) {
            if (pointSet[i - 1].compareTo(pointSet[i]) == 0) {
                throw new IllegalArgumentException("Can't supply duplicate points.");
            }
        }
    }

    private void resize(int capacity) {
        LineSegment[] newItemArr = new LineSegment[capacity];
        for (int i = 0; i < this.collinearLineSegArr.length; i++) {
            newItemArr[i] = collinearLineSegArr[i];
        }
        collinearLineSegArr = newItemArr;
    }

    public int numberOfSegments() {
        if (collinearLineSegArr == null) {
            segments();
        }
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        if (collinearLineSegArr == null) {
            collinearLineSegArr = new LineSegment[pointSet.length];
            for (int i = 0; i < pointSet.length - 3; i++) {
                for (int j = i + 1; j < pointSet.length - 2; j++) {
                    double slopeOneTwo = pointSet[i].slopeTo(pointSet[j]);
                    for (int k = j + 1; k < pointSet.length - 1; k++) {
                        double slopeTwoThree = pointSet[i].slopeTo(pointSet[k]);
                        if (slopeOneTwo == slopeTwoThree) {
                            for (int l = k + 1; l < pointSet.length; l++) {
                                double slopeThreeFour =
                                        pointSet[k].slopeTo(pointSet[l]);
                                if (slopeOneTwo == slopeThreeFour) {
                                    collinearLineSegArr[numberOfSegments++] =
                                            new LineSegment(pointSet[i],
                                                    pointSet[l]);
                                    if (numberOfSegments ==
                                            collinearLineSegArr.length) {
                                        resize(2 * collinearLineSegArr.length);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        int arrIndex = 0;
        LineSegment[] finalCollinearLineSegs = (numberOfSegments == 0)
                ? NO_LINE_SEGMENT
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
