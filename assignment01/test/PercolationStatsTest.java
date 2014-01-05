import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Intellectsoft_user
 * Date: 11/29/13
 * Time: 11:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class PercolationStatsTest {
    @Test
    public void testMain() throws Exception {

        PercolationStats.main(new String[] {"200", "100"});
      //  PercolationStats.main(new String[] {"2", "1000"});

    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testOutOfBN() throws Exception {
        new PercolationStats(-2,10);

    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testOutOfBT() throws Exception {
        new PercolationStats(10,-1);

    }
}
