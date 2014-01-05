import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Intellectsoft_user
 * Date: 12/5/13
 * Time: 10:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Point implements Comparable<Point> {
    /**
     * compare points by slope to this point
     */
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
    private final int x;    //x coordinate
    private final int y;    //y coordinate

    private class SlopeOrder implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            if (slopeTo(p1) == slopeTo(p2)) {
                return 0;
            } else if (slopeTo(p1) > slopeTo(p2)) {
                return +1;
            } else {
                return -1;
            }
        }
    }

    /**
     * construct the point (x, y)
     *
     * @param x
     * @param y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * draw this point
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * draw the line segment from this point to that point
     *
     * @param that
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * string representation
     *
     * @return
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * is this point lexicographically smaller than that point?
     * the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that
     * @return
     */
    public int compareTo(Point that) {

        if (Point.this.x == that.x && Point.this.y == that.y) {
            return 0;
        } else if (Point.this.y < that.y || (Point.this.y
                == that.y && Point.this.x < that.x)) {
            return -1;
        } else {
            return 1;
        }

    }

    /**
     * the slope between this point and that point
     * (y1 − y0) / (x1 − x0)
     *
     * @param that
     * @return
     */
    public double slopeTo(Point that) {
        double slope;
        int xDiff = x - that.x;
        int yDiff = y - that.y;

        if (xDiff == 0 && yDiff == 0) {
            slope = 0;
        } else if (xDiff == 0) {
            if (yDiff >= 0) {
                slope = Double.POSITIVE_INFINITY;
            } else {
                slope = Double.NEGATIVE_INFINITY;
            }
        } else {
            slope = (double) yDiff / xDiff;
        }

        return slope;
    }
}
