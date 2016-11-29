package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

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
