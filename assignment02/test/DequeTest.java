import junit.framework.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Intellectsoft_user
 * Date: 12/3/13
 * Time: 1:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class DequeTest {
    @Test
    public void testIsEmpty() throws Exception {
        Deque<String> d = new Deque<String>();
        Assert.assertTrue("Is not empty", d.isEmpty());
        d.addFirst("s1");
        Assert.assertFalse("Is empty", d.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        Deque<String> d = new Deque<String>();
        d.addFirst("s1");
        d.addFirst("s2");
        d.addFirst("s3");
        d.addFirst("s4");
        Assert.assertTrue("Size is not correct", d.size() == 4);
        Assert.assertFalse("Is empty", d.isEmpty());
        for (int i = 0; i < 4; i++) {
            System.out.println(d.removeLast());
        }
        Assert.assertTrue("Is not empty", d.isEmpty());
    }

    @Test
    public void testPerformance() throws Exception {
        long avTime = 0;
        int n = 1024000;
        for (int j = 0; j < 10; j++) {

            Deque<Integer> d = new Deque<Integer>();
            long time = 0;

            for (int i = 0; i < n / 2; i++) {
                Integer l = StdRandom.uniform(Integer.MAX_VALUE);
                time -= System.nanoTime();
                d.addFirst(l);
                d.addLast(l);
                time += System.nanoTime();
            }

            for (int i = 0; i < n / 2; i++) {
                Integer l = StdRandom.uniform(Integer.MAX_VALUE);
                time -= System.nanoTime();
                d.removeFirst();
                d.removeLast();
                time += System.nanoTime();
            }
           // System.out.println("Total time = " + TimeUnit.NANOSECONDS.toMillis(time) / 1000.0);
           // System.out.println("Average time = " + TimeUnit.NANOSECONDS.toMillis(time) / 1000.0 / n);
        avTime+=time;
        }
        System.out.println("Total average time = " + TimeUnit.NANOSECONDS.toMillis(avTime) / 1000.0 / 10);
    }

    @Test
    public void testAddLast() throws Exception {
        Deque<String> d = new Deque<String>();
        d.addLast("s1");
        d.addLast("s2");
        d.addFirst("s3");
        d.addFirst("s4");
        Assert.assertTrue("Size is not correct", d.size() == 4);
        Assert.assertFalse("Is empty", d.isEmpty());
        for (int i = 0; i < 4; i++) {
            System.out.println(d.removeFirst());
        }
        Assert.assertTrue("Is not empty", d.isEmpty());
    }

    @Test
    public void testRemoveFirst() throws Exception {

    }

    @Test
    public void testRemoveLast() throws Exception {

    }
}
