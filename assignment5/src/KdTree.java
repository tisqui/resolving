/**
 * Coursera Algorithms course assignment implementation.
 * Data type to represent a set of points in the unit square (all points have x- and y-coordinates between 0 and 1)
 * using a 2d-tree to support efficient range search (find all of the points contained in a query rectangle)
 * and nearest neighbor search (find a closest point to a query point).
 * Mutable data type KdTree, 2d-tree which is a generalization of a BST to two-dimensional keys.
 *
 * <p>This implementation should support {@code insert()} and {@code contains()} in time proportional to the logarithm
 * of the number of points in the set in the worst case; support {@code nearest()} and {@code range()} in time
 * proportional to the number of points in the set.</p>
 *
 * <p><strong>Please note that according to assignment requirements </strong> KdTree is created assuming that the
 * {@code insert()}, {@code contains()}, and {@code nearest()} methods in KdTree are passed points with x- and
 * y-coordinates between 0 and 1. Also assumed that the {@code range()} method in KdTree is passed a rectangle
 * that lies in the unit box. </p>
 *
 * @author  Galina Kostetskaya
 *
 */
public class KdTree {

    private static final boolean VERTICAL = true; //type of node orientation
    private static final boolean HORIZONTAL = false;

    private Node root;
    private int size;


    private class Node {
        private Point2D point;
        private Node left; //left subtree
        private Node right; //right subtree
        private RectHV rect;//the axis-aligned rectangle corresponding to this node


        public Node(Point2D p) {
            this.point = p;
        }

        public Node(Point2D p, RectHV r) {
            this.point = p;
            this.rect = r;
        }
    }

    /**
     * Check if the tree is empty.
     * @return boolean, if the tree is empty
     */
    private boolean isEmpty() {
        return (root == null);
    }

    /**
     * Find the tree size. Returns null if the tree is empty.
     * @return number of the elements in the tree
     */
    private int size() {
        return size;
    }

    /**
     * Add the point p to the set (if it is not already in the set)
     * @param p Point that we are inserting
     */
    public void insert(Point2D p) {
        if (p != null) {
            root = insert(root, p, VERTICAL);
        }
    }

    /**
     * Insert the point into the tree, counting hte orientation (vertical/horizontal) starting at the top as orientation
     * we pass as the parameter.
     * @param x The node which is processed next
     * @param p Point we are inserting
     * @param orientation The orientation of the next point processed (x)
     * @return node of the tree
     */
    private Node insert(Node x, Point2D p, boolean orientation) {
        if (x == null) {
            return new Node(p, getRectHV(p.x(), p.y(), orientation));
        }
        if (orientation == HORIZONTAL) {  //compare y coordinate
            if (p.y() < x.point.y()) {  //point is lower
                x.left = insert(x.left, p, VERTICAL);
            } else if (p.y() > x.point.y()) {  //point is higher
                x.right = insert(x.right, p, VERTICAL);
            } else {
                x.right = insert(x.right, p, VERTICAL);
            }
        } else {  //orientation == VERTICAL, compare x coordinate
            if (p.x() < x.point.x()) {
                x.left = insert(x.left, p, HORIZONTAL);
            } else if (p.x() > x.point.x()) {
                x.right = insert(x.right, p, HORIZONTAL);
            } else {
                x.right = insert(x.right, p, HORIZONTAL);
            }
        }
        return x;
    }

    /**
     * Get the rectangle for the node point coordinates x,y and node orientation.
     * @param x x coordinate of the node point
     * @param y y coordinate of the node point
     * @param orientation the horizontal/vertical orientation
     * @return Rectangle obj
     */
    private RectHV getRectHV(double x, double y, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return new RectHV(0, y,
                    1, y);
        } else {
            return new RectHV(x, 0,
                    x, 1);
        }
    }

    /**
     * Check if the set contains the point p
     *
     * @param p The point we want to find in the set
     * @return If the point is in the set
     */
    public boolean contains(Point2D p) {
        Point2D p1 = get(root, p, VERTICAL);
        if (p1 == null) {
            return false;
        }
        return (p.compareTo(p1) == 0);
    }


    private Point2D get(Node x, Point2D p, boolean orientation) {
        if (x == null) {
            return null;
        }
        if (orientation == HORIZONTAL) {
            if (p.y() < x.point.y()) {
                return get(x.left, p, VERTICAL);
            } else if (p.y() > x.point.y()) {
                return get(x.right, p, VERTICAL);
            } else {
                return x.point;
            }
        } else {   //orientation == VERTICAL
            if (p.x() < x.point.x()) {
                return get(x.left, p, HORIZONTAL);
            } else if (p.x() > x.point.x()) {
                return get(x.right, p, HORIZONTAL);
            } else {
                return x.point;
            }
        }
    }


    /**
     * Draw all of the points which are in the set at the current moment to standard draw.
     */
    public void draw() {
        draw(root, VERTICAL);
    }

    /**
     * Draw all the points rectangles starting from node x.
     * Red for vertical and blue for horizontal rectangles.
     * @param x Node we start the drawing from.
     * @param orientation Orientation of the node x.
     */
    private void draw(Node x, boolean orientation) {
        if (x == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        x.point.draw();

        if (orientation == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
        }

        StdDraw.setPenRadius();
        x.rect.draw();

        draw(x.right, !orientation);
        draw(x.left, !orientation);
    }


    /**
     * <p>Find all points in the set that are inside the rectangle.</p>
     * <p>Finds ind all points contained in a given query rectangle, start at the root and recursively search for
     * points in both subtrees using the following pruning rule: if the query rectangle does not intersect the
     * rectangle corresponding to a node, there is no need to explore that node (or its subtrees). A subtree is
     * searched only if it might contain a point contained in the query rectangle.</p>
     *
     * @param rect Rectangle in the bounds of which we want to find the points.
     * @return The set of the points which are inside the rectangle.
     */
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> r = new Queue<Point2D>();
        find(rect, root, r, VERTICAL);
        return r;
    }

    private void find(RectHV rect, Node n, Queue<Point2D> res, boolean orientation) {
        if (n == null) {
            return;
        }

        if (rect.contains(n.point)) {
            res.enqueue(n.point);
            find(rect, n.left, res, !orientation);
            find(rect, n.right, res, !orientation);
        } else {

            if (rect.intersects(n.rect)) { //search both if it intersects
                find(rect, n.left, res, !orientation);
                find(rect, n.right, res, !orientation);
            } else {

                if (orientation == VERTICAL) {

                    if (rect.xmax() < n.point.x()) {  //rectangle on the left
                        find(rect, n.left, res, HORIZONTAL);
                    } else { //rect is on the right
                        find(rect, n.right, res, HORIZONTAL);
                    }
                } else {  //orientation = HORIZONTAL
                    if (rect.ymax() < n.point.y()) {   //rect is on top
                        find(rect, n.left, res, VERTICAL);
                    } else { // rect is in bottom
                        find(rect, n.right, res, VERTICAL);
                    }
                }
            }
        }


    }

    /**
     * <p>Find a nearest neighbor in the set to the point p.</p>
     * <p>Returns null if set is empty.</p>
     * <p>Finds a closest point to a given query point, start at the root and recursively search in both subtrees
     * using the following pruning rule: if the closest point discovered so far is closer than the distance between
     * the query point and the rectangle corresponding to a node, there is no need to explore that node (or its
     * subtrees).</p>
     *
     * @param p The point for which we want to find the nearest
     * @return The point which is the nearest to the p.
     */
    public Point2D nearest(Point2D p) {
        if (root != null) {
          return  new MinFinder(p).getMin();
        } else {
            return null;
        }
    }

    private class MinFinder {

        private Point2D from;
        private Point2D res;

        public MinFinder(Point2D from) {
            this.from = from;
            this.res = root.point;
            findNearest(root, VERTICAL);
        }

        public Point2D getMin(){
            return res;
        }

        private void findNearest(Node n, boolean orientation) {
            if (n == null) {
                return;
            }
            if (from.distanceSquaredTo(n.point) < from.distanceSquaredTo(res)) {
                res = n.point;
            }
            if (orientation == VERTICAL) {
                if (from.x() < n.point.x()) {
                    findNearest(n.left, HORIZONTAL);
                } if (from.x() > n.point.x()) {
                    findNearest(n.right, HORIZONTAL);
                } else {
                    findNearest(n.left, HORIZONTAL);
                    findNearest(n.right, HORIZONTAL);
                }
            } else {
                findNearest(n.left, VERTICAL);
                findNearest(n.right, VERTICAL);
            }
        }
    }
}
