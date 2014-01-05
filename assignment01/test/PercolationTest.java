import junit.framework.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: Intellectsoft_user
 * Date: 11/29/13
 * Time: 11:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class PercolationTest {
    @org.junit.Test
    public void testOpen() throws Exception {
        Percolation percSet = new Percolation(100);
        percSet.open(1, 1);
        percSet.open(1, 2);
        percSet.open(2, 2);

        Assert.assertTrue("Is not opened", percSet.isOpen(1, 1));
    }

    @org.junit.Test(expected = IndexOutOfBoundsException.class)
    public void testBoundsCheck() throws Exception {
        Percolation percSet = new Percolation(3);
        percSet.open(4,4);

    }

    @org.junit.Test(expected = IndexOutOfBoundsException.class)
    public void testZeroBoundsCheck() throws Exception {
        Percolation percSet = new Percolation(3);
        percSet.open(0,0);

    }

    @org.junit.Test
    public void testIsFull() throws Exception {

    }

    @org.junit.Test
    public void testPercolates() throws Exception {
        Percolation percSet = new Percolation(3);
        Assert.assertFalse("Is not percolated", percSet.isOpen(1,1));
        Assert.assertFalse("Is not percolated", percSet.percolates());
        percSet.open(1, 1);
        Assert.assertTrue("Is not percolated", percSet.isOpen(1,1));
        Assert.assertTrue("Is not percolated", percSet.isFull(1,1));
        Assert.assertFalse("Is not percolated", percSet.percolates());
        percSet.open(1, 2);
        Assert.assertTrue("Is not percolated", percSet.isFull(1,1));
        Assert.assertFalse("Is not percolated", percSet.percolates());
        percSet.open(2, 2);
        Assert.assertFalse("Is not percolated", percSet.percolates());
        percSet.open(2, 3);
        Assert.assertFalse("Is not percolated", percSet.percolates());
        percSet.open(3, 3);
        Assert.assertTrue("Is not percolated", percSet.percolates());
    }

    @org.junit.Test
    public void testNotPercolates() throws Exception {
        Percolation percSet = new Percolation(3);
        percSet.open(1, 1);
        percSet.open(1, 2);
        percSet.open(2, 3);
        percSet.open(3, 3);
        Assert.assertFalse("Is percolated", percSet.percolates());
    }

    @org.junit.Test
    public void testFile2() throws Exception {
        Percolation percSet = new Percolation(2);
        percSet.open(1, 1);
        percSet.open(2, 2);
        percSet.open(1, 2);
        Assert.assertTrue("Is not percolated", percSet.percolates());
    }

    @org.junit.Test
    public void testFile4() throws Exception {
        Percolation percSet = new Percolation(4);
        percSet.open(4, 1);
        percSet.open(3, 1);
        percSet.open(2, 1);
        percSet.open(1, 1);
        Assert.assertTrue("Is not percolated", percSet.percolates());
        percSet.open(1, 4);
        percSet.open(2, 4);
        percSet.open(4, 4);
        percSet.open(3, 4);
        Assert.assertTrue("Is not percolated", percSet.percolates());
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testOutOfB() throws Exception {
       new Percolation(-2);

    }


}
