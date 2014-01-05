import junit.framework.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * Date: 12/18/13
 * Time: 11:30 PM
 */
public class KdTreeTest {
    @Test
    public void testInsert() throws Exception {
        KdTree kd = new KdTree();
        Point2D p1 = new Point2D(0.1, 0.2);
        Point2D p2 = new Point2D(0.3, 0.4);

        kd.insert(p1);
        Assert.assertTrue("Do not contain the added point", kd.contains(p1));
        kd.insert(p2);
        Assert.assertTrue("Do not contain the added point", kd.contains(p2));

    }

    @Test
    public void testNearest() throws Exception {
        KdTree kd = new KdTree();
        Point2D p1 = new Point2D(0.206107, 0.095492);
        Point2D p2 = new Point2D(0.975528, 0.654508);
        Point2D p3 = new Point2D(0.024472, 0.345492);
        Point2D p4 = new Point2D(0.793893, 0.095492);
        Point2D p5 = new Point2D(0.793893, 0.904508);
        Point2D p6 = new Point2D(0.975528, 0.345492);
        Point2D p7 = new Point2D(0.206107, 0.904508);
        Point2D p8 = new Point2D(0.500000, 0.000000);
        Point2D p9 = new Point2D(0.024472, 0.654508);
        Point2D p10 = new Point2D(0.500000, 1.000000);

        kd.insert(p1);
        kd.insert(p2);
        kd.insert(p3);
        kd.insert(p4);
        kd.insert(p5);
        kd.insert(p6);
        kd.insert(p7);
        kd.insert(p8);
        kd.insert(p9);
        kd.insert(p10);

        Assert.assertEquals(0, p1.compareTo(kd.nearest(new Point2D(0,0))));
        Assert.assertEquals(0, p5.compareTo(kd.nearest(new Point2D(1,1))));

    }

    @Test
    public void testRange() throws Exception {
        KdTree kd = new KdTree();
        Point2D p1 = new Point2D(0.0, 0.1);
        Point2D p2 = new Point2D(0.3, 0.4);
        kd.insert(p1);
        kd.insert(p2);
        //TODO create the test for range function

    }

    @Test
    public void testContains() throws Exception {
        KdTree kd = new KdTree();
        Point2D p1 = new Point2D(0.0, 0.1);
        Point2D p2 = new Point2D(0.3, 0.4);
        kd.insert(p1);
        kd.insert(p2);
        Assert.assertTrue("Failed the contains test", kd.contains(p1));
        Assert.assertTrue("Failed the contains test", kd.contains(p1));

    }

    @Test
    public void testDraw() throws Exception {
        StdDraw.show(0);
        KdTree kd = new KdTree();
        Point2D p1 = new Point2D(0.206107, 0.095492);
        Point2D p2 = new Point2D(0.975528, 0.654508);
        Point2D p3 = new Point2D(0.024472, 0.345492);
        Point2D p4 = new Point2D(0.793893, 0.095492);
        Point2D p5 = new Point2D(0.793893, 0.904508);
        Point2D p6 = new Point2D(0.975528, 0.345492);
        Point2D p7 = new Point2D(0.206107, 0.904508);
        Point2D p8 = new Point2D(0.500000, 0.000000);
        Point2D p9 = new Point2D(0.024472, 0.654508);
        Point2D p10 = new Point2D(0.500000, 1.000000);

        kd.insert(p1);
        kd.insert(p2);
        kd.insert(p3);
        kd.insert(p4);
        kd.insert(p5);
        kd.insert(p6);
        kd.insert(p7);
        kd.insert(p8);
        kd.insert(p9);
        kd.insert(p10);

        StdDraw.clear();
        kd.draw();
        StdDraw.show(400000);
    }
}
