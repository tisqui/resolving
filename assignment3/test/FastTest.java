import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Intellectsoft_user
 * Date: 12/9/13
 * Time: 11:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class FastTest {
    @Test
    public void testSort() throws Exception {
       Integer[] arr = new Integer[]{1,3,5,2};
       Fast.sort(arr);
       Assert.assertArrayEquals(new Integer[]{1,2,3,5}, arr);
    }

    @Test
    public void testSort1() throws Exception {
        Integer[] arr = new Integer[]{1};
        Fast.sort(arr);
        Assert.assertArrayEquals(new Integer[]{1}, arr);
    }

    @Test
    public void testSortSamr() throws Exception {
        Integer[] arr = new Integer[]{1,1,1,0};
        Fast.sort(arr);
        Assert.assertArrayEquals(new Integer[]{0,1,1,1}, arr);
    }

    @Test
    public void testSortPoint() throws Exception {
        Integer[] arr = new Integer[]{1,1,1,0};
        Fast.sort(arr);
        Assert.assertArrayEquals(new Integer[]{0,1,1,1}, arr);
    }
}
