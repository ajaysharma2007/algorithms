package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by ajay on 11/29/16.
 */
public class KdTree {
    private int size = 0;
    private int treeDim = 2;
    private Node root = null;

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }

        return false;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Point to be inserted can't be null");
        }

        root = insert(root, p, 0);

    }

    private Node insert(Node n, Point2D p, int depth) {
        if (n == null) {
            size++;
            return new Node(p, null, null);
        }

        if (p.equals(n.p)) {
            return n;

        }
        int currentDim = depth % treeDim;

        if (currentDim == 0) {
            if (p.x() < n.p.x()) {
                n.lb = insert(n.lb, p, depth + 1);
            } else {
                n.rb = insert(n.rb, p, depth + 1);
            }
        } else {
            if (p.y() < n.p.y()) {
                n.lb = insert(n.lb, p, depth + 1);
            } else {
                n.rb = insert(n.rb, p, depth + 1);
            }
        }

        return n;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Point to be searched can't be null");
        }

        return contains(root, p, 0);
    }

    private boolean contains(Node n, Point2D p, int depth) {
        if (n == null) {
            return false;
        }

        if (n.p.equals(p)) {
            return true;
        }

        int currentDim = depth % treeDim;

        if (currentDim == 0) {
            if (p.x() < n.p.x()) {
                return contains(n.lb, p, depth + 1);
            } else {
                return contains(n.rb, p, depth + 1);
            }
        } else {
            if (p.y() < n.p.y()) {
                return contains(n.lb, p, depth + 1);
            } else {
                return contains(n.rb, p, depth + 1);
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException("Reference rect can't be null");
        }
        Queue<Point2D> pointsInRange = new Queue<>();
        return range(rect, root, pointsInRange, 0);
    }

    private Iterable<Point2D> range(RectHV rect, Node node,
                                    Queue<Point2D> pointsInRange, int depth) {
        if (node == null) {
            return pointsInRange;
        }

        int currentDim = depth % treeDim;

        if (currentDim == 0) {
            if (node.p.x() < rect.xmin()) {
                range(rect, node.lb, pointsInRange, depth + 1);
            } else if (node.p.x() >= rect.xmax()) {
                range(rect, node.rb, pointsInRange, depth + 1);
            } else {

                if (rect.ymin() < node.p.y() && node.p.y() < rect.ymax()) {
                    pointsInRange.enqueue(node.p);
                }
                range(rect, node.lb, pointsInRange, depth + 1);
                range(rect, node.rb, pointsInRange, depth + 1);
            }
        } else {
            if (node.p.y() < rect.ymin()) {
                range(rect, node.lb, pointsInRange, depth + 1);
            } else if (node.p.y() >= rect.ymax()) {
                return range(rect, node.rb, pointsInRange, depth + 1);
            } else {
                if (rect.xmin() < node.p.x() && node.p.x() < rect.xmax()) {
                    pointsInRange.enqueue(node.p);
                }
                range(rect, node.lb, pointsInRange, depth + 1);
                range(rect, node.rb, pointsInRange, depth + 1);
            }
        }
        return pointsInRange;
    }

    public Point2D nearest(Point2D queryPoint) {
        return nearest(root, queryPoint, 0, root.p);
    }

    private Point2D nearest(Node currNode, Point2D queryPoint,
                            int depth, Point2D min) {
        if (currNode == null) {
            return min;
        }

        if (currNode.p.distanceSquaredTo(queryPoint) <
                min.distanceSquaredTo(queryPoint)) {
            min = currNode.p;
        }

        int currentDim = depth % treeDim;

        if (currentDim == 0) {
            if (currNode.p.x() < queryPoint.x()) {
                min = nearest(currNode.lb, queryPoint, depth + 1, min);
                if (new Point2D(queryPoint.x(), 0).distanceSquaredTo(
                        new Point2D(currNode.p.x(), 0)) <
                        queryPoint.distanceSquaredTo(min)) {
                    min = nearest(currNode.rb, queryPoint, depth + 1, min);
                }
            } else {
                min = nearest(currNode.rb, queryPoint, depth + 1, min);
                if (new Point2D(queryPoint.x(), 0).distanceSquaredTo(
                        new Point2D(currNode.p.x(), 0)) <
                        queryPoint.distanceSquaredTo(min)) {
                    min = nearest(currNode.lb, queryPoint, depth + 1, min);
                }
            }
        } else {
            if (currNode.p.y() < queryPoint.y()) {
                min = nearest(currNode.lb, queryPoint, depth + 1, min);
                if (new Point2D(0, queryPoint.y()).distanceSquaredTo(
                        new Point2D(0, currNode.p.y())) <
                        queryPoint.distanceSquaredTo(min)) {
                    min = nearest(currNode.rb, queryPoint, depth + 1, min);
                }
            } else {
                min = nearest(currNode.rb, queryPoint, depth + 1, min);
                if (new Point2D(0, queryPoint.y()).distanceSquaredTo(
                        new Point2D(0, currNode.p.y())) <
                        queryPoint.distanceSquaredTo(min)) {
                    min = nearest(currNode.lb, queryPoint, depth + 1, min);
                }
            }
        }

        return min;
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(0, 0, 0, 1);
        StdDraw.line(0, 1, 1, 1);
        StdDraw.line(0, 0, 1, 0);
        StdDraw.line(1, 0, 1, 1);
        draw(root, 0, null);
    }

    private void draw(Node n, int depth, Point2D previousPoint) {
        if (n == null) {
            return;
        }

        int currentDim = depth % treeDim;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.p.draw();

        StdDraw.setPenRadius();
        if (currentDim == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            if (previousPoint == null) {
                StdDraw.line(n.p.x(), 0, n.p.x(), 1);
            } else {
                StdDraw.line();
            }
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line();
        }
    }

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rb;

        private Node(Point2D point2D, Node leftBranch, Node rightBranch) {
            this.p = point2D;
            this.lb = leftBranch;
            this.rb = rightBranch;
        }

        public void setRect(RectHV rect) {
            this.rect = rect;
        }
    }
}
