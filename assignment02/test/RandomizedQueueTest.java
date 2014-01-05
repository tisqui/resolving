import junit.framework.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: Intellectsoft_user
 * Date: 12/3/13
 * Time: 1:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class RandomizedQueueTest {
    @Test
    public void testIsEmpty() throws Exception {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        Assert.assertTrue("Is not empty", rq.isEmpty());
        rq.enqueue("s1");
        Assert.assertFalse("Is empty", rq.isEmpty());
        rq.dequeue();
        Assert.assertTrue("Is not empty", rq.isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        Assert.assertTrue("size is not correct1", rq.size() == 0);
        rq.enqueue("s1");
        Assert.assertTrue("size is not correct2", rq.size() == 1);
    }

    @Test
    public void testEnqueue() throws Exception {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("s1");
        rq.enqueue("s2");
        Assert.assertTrue("size is not correct1", rq.size() == 2);

    }

    @Test
    public void testDequeue() throws Exception {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("AA");
        rq.enqueue("BB");
        rq.enqueue("BB");
        rq.enqueue("BB");
        rq.enqueue("BB");
        rq.enqueue("BB");
        rq.enqueue("CC");
        rq.enqueue("CC");
        for (int i = 0; i < 8; i++) {
            System.out.println(rq.dequeue());
        }
        Assert.assertTrue("size is not correct1", rq.size() == 0);
    }

    @Test
    public void testDequeue2() throws Exception {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("A");
        rq.enqueue("B");
        rq.enqueue("C");
        rq.enqueue("D");
        rq.enqueue("E");
        rq.enqueue("F");
        rq.enqueue("G");
        rq.enqueue("H");
        rq.enqueue("I");
        for (int i = 0; i < 3; i++) {
            System.out.println(rq.dequeue());
        }
        Assert.assertTrue("size is not correct1", rq.size() == 6);
    }

    @Test
    public void testSample() throws Exception {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("A");
        rq.enqueue("B");
        rq.enqueue("C");
        rq.enqueue("D");
        for (int i = 0; i < 3; i++) {
            System.out.println(rq.sample());
        }
        Assert.assertTrue("size is not correct1", rq.size() == 4);

    }


    @Test
    public void testRandom() throws Exception {
        String[] keys = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        Map<String, Integer> counters = new TreeMap<String, Integer>();
        for (String key : keys) {
            counters.put(key, 0);
        }
        for (int i = 0; i < 1000; i++) {
            RandomizedQueue<String> rq = new RandomizedQueue<String>();
            for (String key : keys) {
                rq.enqueue(key);
            }
            for (int j = 0; j < 2; j++) {
                String str = rq.dequeue();
                counters.put(str, counters.get(str) + 1);
            }

        }
        System.out.println(counters);

    }

    @Test
    public void testRandomIter() throws Exception {
        String[] keys = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        Map<String, Integer> counters = new TreeMap<String, Integer>();
        for (String key : keys) {
            counters.put(key, 0);
        }
        for (int i = 0; i < 1000; i++) {
            RandomizedQueue<String> rq = new RandomizedQueue<String>();
            for (String key : keys) {
                rq.enqueue(key);
            }


            Iterator<String> iterator = rq.iterator();
            for (int j = 0; j < 1; j++) {
                String str = iterator.next();
                counters.put(str, counters.get(str) + 1);
            }

        }
        System.out.println(counters);

    }

}
