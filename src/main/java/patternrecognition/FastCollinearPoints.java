package patternrecognition;

import java.util.Arrays;

/**
 * Created by ajay on 24/10/16.
 */
public class FastCollinearPoints {

    private static final LineSegment[] NO_LINE_SEGMENT = {};
    private int numberOfSegments = 0;
    private Point[] pointSet;
    private LineSegment[] collinearLineSegArr;

    public FastCollinearPoints(Point[] points) {
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

    private boolean isSegAlreadyIncluded(int startPt, int currentPt) {
        double currentPointSlope = pointSet[currentPt].slopeTo(pointSet[startPt]);
        for (int j = 0; j < startPt; j++) {
            if (pointSet[currentPt].slopeTo(pointSet[j]) == currentPointSlope) {
                return true;
            }
        }
        return false;
    }

    public LineSegment[] segments() {

        if (collinearLineSegArr == null) {
            collinearLineSegArr = new LineSegment[pointSet.length];
            for (int k = 0; k < pointSet.length - 3; k++) {
                if (k > 0) {
                    Arrays.sort(pointSet);
                }
                Point point = pointSet[k];

                if (k < pointSet.length - 4) {
                    Arrays.sort(pointSet, k + 1, pointSet.length,
                            point.slopeOrder());
                }
                int i = k + 1;

                while (i < pointSet.length - 2) {
                    double slope = point.slopeTo(pointSet[i]);
                    if (slope ==
                            point.slopeTo(pointSet[i + 1])
                            &&
                            slope ==
                                    point.slopeTo(pointSet[i + 2])) {

                        int lastPt = -1;

                        if (i + 2 == pointSet.length - 1) {
                            lastPt = i + 2;
                        } else {

                            int start = i + 2;
                            int end = pointSet.length - 1;

                            int mid;

                            int low = start;
                            int high = end;
                            while (low <= high) {
                                mid = low + ((high - low) / 2);
                                double slopeMid = point.slopeTo(pointSet[mid]);
                                if (slopeMid == slope) {
                                    low = mid + 1;
                                    lastPt = mid;
                                } else {
                                    high = mid - 1;
                                    lastPt = mid - 1;
                                }
                            }
                        }

                        i = lastPt + 1;

                        if (isSegAlreadyIncluded(k, lastPt)) {
                            continue;
                        }

                        collinearLineSegArr[numberOfSegments++] =
                                new LineSegment(pointSet[k], pointSet[lastPt]);


                        if (numberOfSegments == collinearLineSegArr.length) {
                            resize(2 * collinearLineSegArr.length);
                        }

                    } else {
                        i++;
                    }
                }
            }
        }

        int arrIndex = 0;
        LineSegment[] finalCollinearLineSegs = numberOfSegments == 0
                ? NO_LINE_SEGMENT : new LineSegment[numberOfSegments];
        for (LineSegment collinearLineSeg : collinearLineSegArr) {
            if (collinearLineSeg == null) {
                break;
            }
            finalCollinearLineSegs[arrIndex++] = collinearLineSeg;
        }

        return finalCollinearLineSegs;
    }

}
