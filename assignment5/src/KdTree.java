/**
 * Created with IntelliJ IDEA.
 * Date: 12/18/13
 * Time: 2:49 AM
 */
public class KdTree {

    private static final boolean VERTICAL = true;
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
     * check if the tree is empty
     */
    private boolean isEmpty() {
        return (root == null);
    }

    /**
     * find the tree size
     */
    private int size() {
        return size;
    }

    /**
     * add the point p to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p != null) {
            root = insert(root, p, VERTICAL);
        }
    }

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
     * does the set contain the point p?
     *
     * @param p
     * @return
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
     * draw all of the points to standard draw
     */
    public void draw() {
        draw(root, VERTICAL);
    }

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
     * all points in the set that are inside the rectangle
     *
     * @param rect
     * @return
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
     * a nearest neighbor in the set to p; null if set is empty
     *
     * @param p
     * @return
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
