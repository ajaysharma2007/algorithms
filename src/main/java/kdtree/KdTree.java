package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ajay on 11/29/16.
 */
public class KdTree {
    private static final String LEFT = "LEFT";
    private static final String RIGHT = "RIGHT";
    private static final String HORIZONTAL = "HORIZONTAL";
    private static final String VERTICAL = "VERTICAL";
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

        double nodeX = node.p.x();
        double nodeY = node.p.y();

        double rectXMin = rect.xmin();
        double rectXMax = rect.xmax();

        double rectYMin = rect.ymin();
        double rectYMax = rect.ymax();

        if (currentDim == 0) {
            if (nodeX < rectXMin) {
                range(rect, node.rb, pointsInRange, depth + 1);
            } else if (nodeX >= rectXMax) {
                range(rect, node.lb, pointsInRange, depth + 1);
            } else {

                if (rectYMin < nodeY && nodeY < rectYMax) {
                    pointsInRange.enqueue(node.p);
                }
                range(rect, node.lb, pointsInRange, depth + 1);
                range(rect, node.rb, pointsInRange, depth + 1);
            }
        } else {
            if (nodeY < rectYMin) {
                range(rect, node.rb, pointsInRange, depth + 1);
            } else if (nodeY >= rectYMax) {
                return range(rect, node.lb, pointsInRange, depth + 1);
            } else {
                if (rectXMin < nodeX && nodeX < rectXMax) {
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
        Set<Node> xSortedNodes = new TreeSet<>(Node.X_ORDER);
        Point2D initialPoint = new Point2D(0, 0);
        Point2D xFinalPoint = new Point2D(1, 0);
        Point2D yInitialPoint = new Point2D(0, 1);
        Point2D finalPoint = new Point2D(1, 1);

        Node initialHorizontalNode = new Node(initialPoint, null, null);
        initialHorizontalNode.startPoint = initialPoint;
        initialHorizontalNode.endPoint = xFinalPoint;

        Node finalHorizontalNode = new Node(yInitialPoint, null, null);
        finalHorizontalNode.startPoint = yInitialPoint;
        finalHorizontalNode.endPoint = finalPoint;

        Node initialVerticalNode = new Node(initialPoint, null, null);
        initialVerticalNode.startPoint = initialPoint;
        initialVerticalNode.endPoint = yInitialPoint;

        Node finalVerticalNode = new Node(xFinalPoint, null, null);
        finalVerticalNode.startPoint = xFinalPoint;
        finalVerticalNode.endPoint = finalPoint;

        xSortedNodes.add(initialVerticalNode);
        xSortedNodes.add(finalVerticalNode);
        Set<Node> ySortedNodes = new TreeSet<>(Node.Y_ORDER);
        ySortedNodes.add(initialHorizontalNode);
        ySortedNodes.add(finalHorizontalNode);
        draw(root, 0, initialPoint, xSortedNodes, ySortedNodes, RIGHT);
    }

    private void draw(Node n, int depth, Point2D previousPoint, Set<Node> xSortedNodes, Set<Node> ySortedNodes, String branchDir) {
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
            if (branchDir.equals(RIGHT)) {
                double yEndPoint = getHigher(ySortedNodes, n, VERTICAL).y();
                StdDraw.line(n.p.x(), previousPoint.y(), n.p.x(), yEndPoint);
                n.startPoint = new Point2D(n.p.x(), previousPoint.y());
                n.endPoint = new Point2D(n.p.x(), yEndPoint);
            } else {
                double yEndPoint = getLower(ySortedNodes, n, VERTICAL).y();
                StdDraw.line(n.p.x(), previousPoint.y(), n.p.x(), yEndPoint);
                n.startPoint = new Point2D(n.p.x(), previousPoint.y());
                n.endPoint = new Point2D(n.p.x(), yEndPoint);
            }
            xSortedNodes.add(n);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            if (branchDir.equals(RIGHT)) {
                double xEndPoint = getHigher(xSortedNodes, n, HORIZONTAL).x();
                StdDraw.line(previousPoint.x(), n.p.y(), xEndPoint, n.p.y());
                n.startPoint = new Point2D(previousPoint.x(), n.p.y());
                n.endPoint = new Point2D(xEndPoint, n.p.y());
            } else {
                double xEndPoint = getLower(xSortedNodes, n, HORIZONTAL).x();
                StdDraw.line(previousPoint.x(), n.p.y(), xEndPoint, n.p.y());
                n.startPoint = new Point2D(previousPoint.x(), n.p.y());
                n.endPoint = new Point2D(xEndPoint, n.p.y());
            }
            ySortedNodes.add(n);
        }

        if (n.lb != null) {
            draw(n.lb, depth + 1, n.p, xSortedNodes, ySortedNodes, LEFT);
        }

        if (n.rb != null) {
            draw(n.rb, depth + 1, n.p, xSortedNodes, ySortedNodes, RIGHT);
        }
    }

    private Point2D getHigher(Set<Node> nodes, Node referenceNode, String lineDir) {
        TreeSet<Node> point2DNodes = ((TreeSet<Node>) nodes);
        Node higherPointNode = point2DNodes.higher(referenceNode);
        if (lineDir.equals(HORIZONTAL)) {
            while (true) {
                if (higherPointNode.startPoint.y() <= referenceNode.p.y() && referenceNode.p.y() <= higherPointNode.endPoint.y() ||
                        higherPointNode.endPoint.y() <= referenceNode.p.y() && referenceNode.p.y() <= higherPointNode.startPoint.y()) {
                    return higherPointNode.p;
                }
                higherPointNode = point2DNodes.higher(higherPointNode);
            }

        } else {
            while (true) {
                if (higherPointNode.startPoint.x() <= referenceNode.p.x() && referenceNode.p.x() <= higherPointNode.endPoint.x() ||
                        higherPointNode.endPoint.x() <= referenceNode.p.x() && referenceNode.p.x() <= higherPointNode.startPoint.x()) {
                    return higherPointNode.p;
                }
                higherPointNode = point2DNodes.higher(higherPointNode);
            }
        }
    }

    private Point2D getLower(Set<Node> nodes, Node referenceNode, String lineDir) {
        TreeSet<Node> point2DNodes = ((TreeSet<Node>) nodes);
        Node lowerPointNode = point2DNodes.lower(referenceNode);
        if (lineDir.equals(HORIZONTAL)) {
            while (true) {
                if (lowerPointNode.startPoint.y() <= referenceNode.p.y() && referenceNode.p.y() <= lowerPointNode.endPoint.y() ||
                        lowerPointNode.endPoint.y() <= referenceNode.p.y() && referenceNode.p.y() <= lowerPointNode.startPoint.y()) {
                    return lowerPointNode.p;
                }
                lowerPointNode = point2DNodes.lower(lowerPointNode);
            }
        } else {
            while (true) {
                if (lowerPointNode.startPoint.x() <= referenceNode.p.x() && referenceNode.p.x() <= lowerPointNode.endPoint.x() ||
                        lowerPointNode.endPoint.x() <= referenceNode.p.x() && referenceNode.p.x() <= lowerPointNode.startPoint.x()) {
                    return lowerPointNode.p;
                }
                lowerPointNode = point2DNodes.lower(lowerPointNode);
            }
        }
    }

    private static class Node {
        public static final Comparator<Node> X_ORDER = new Node.XOrder();
        public static final Comparator<Node> Y_ORDER = new Node.YOrder();

        private Point2D p;
        private Node lb;
        private Node rb;
        private Point2D startPoint;
        private Point2D endPoint;

        private Node(Point2D point2D, Node leftBranch, Node rightBranch) {
            this.p = point2D;
            this.lb = leftBranch;
            this.rb = rightBranch;
        }

        private static class XOrder implements Comparator<Node> {
            public int compare(Node p, Node q) {
                if (p.p.x() < q.p.x()) return -1;
                if (p.p.x() > q.p.x()) return +1;
                return 0;
            }
        }

        private static class YOrder implements Comparator<Node> {
            public int compare(Node p, Node q) {
                if (p.p.y() < q.p.y()) return -1;
                if (p.p.y() > q.p.y()) return +1;
                return 0;
            }
        }

    }
}
