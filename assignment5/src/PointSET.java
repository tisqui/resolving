/**
 * Created with IntelliJ IDEA.
 * Date: 12/18/13
 * Time: 1:01 AM
 */

public class PointSET {

    private SET<Point2D> points;

    /**
     * construct an empty set of points
     */
    public PointSET() {
         points = new SET<Point2D>();
    }

    /**
     * is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size(){
        return points.size();
    }

    /**
     * add the point p to the set (if it is not already in the set)
     * @param p
     */
    public void insert(Point2D p){
        if(!contains(p)){
            points.add(p);
        }
    }

    /**
     * does the set contain the point p?
     * @param p
     * @return
     */
    public boolean contains(Point2D p){
       return points.contains(p);
    }

    /**
     * draw all of the points to standard draw
     */
    public void draw(){
        for (Point2D point : points) {
            point.draw();
        }
    }

    /**
     * all points in the set that are inside the rectangle
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect){
        Queue<Point2D> res = new Queue<Point2D>();
        for (Point2D p : points) {
             if(rect.contains(p)){
                 res.enqueue(p);
             }
        }

        return res;
    }

    /**
     * a nearest neighbor in the set to p; null if set is empty
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p){
        Point2D closest = null;

        for (Point2D point : points) {
             double curr = p.distanceSquaredTo(point);
             if (closest == null){
               closest = point;
             }else if(p.distanceSquaredTo(point) < p.distanceSquaredTo(closest)){
                 closest = point;
             }
        }
        return closest;
    }

    /**
     * visualizer
     */

    public static void main(String[] args) {
        StdDraw.show(0);
        PointSET pointset = new PointSET();
        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                System.out.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                pointset.insert(p);
                StdDraw.clear();
                pointset.draw();
            }
            StdDraw.show(50);
        }

    }
}
