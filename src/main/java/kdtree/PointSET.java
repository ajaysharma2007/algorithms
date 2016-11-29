package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

/**
 * Created by ajay on 25/11/16.
 */
public class PointSET {

    private TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw  new NullPointerException("Point to be inserted can't be null");
        }
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw  new NullPointerException("Point to be searched can't be null");
        }
        return points.contains(p);
    }

    public void draw() {
        for (Point2D point2D : points) {
            point2D.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw  new NullPointerException("Reference rect can't be null");
        }
        TreeSet<Point2D> rangeSet = new TreeSet<>();
        double rectXMin = rect.xmin();
        for (Point2D point2D : points.subSet(
                points.ceiling(new Point2D(rectXMin, rect.ymin())),
                points.floor(new Point2D(rectXMin, rect.ymax())))) {
            double point2Dx = point2D.x();
            if (point2Dx > rectXMin && point2Dx < rect.xmax()) {
                rangeSet.add(point2D);
            }
        }
        return rangeSet;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw  new NullPointerException("Reference point can't be null");
        }
        if (this.isEmpty()) {
            return null;
        }

        Point2D nearestPoint = null;
        for (Point2D point2D : points) {
            if (nearestPoint == null) {
                nearestPoint = point2D;
                continue;
            }

            if (p.distanceSquaredTo(point2D) < p.distanceSquaredTo(nearestPoint)) {
                nearestPoint = point2D;
            }
        }

        return nearestPoint;

    }
}
