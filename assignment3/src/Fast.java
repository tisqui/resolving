import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Intellectsoft_user
 * Date: 12/6/13
 * Time: 1:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class Fast {

    private int num;
    private Point[] points;
    private Point[] sorted;

    protected static <T extends Comparable<T>> void sort(T[] p) {
        sort(p, 0, p.length - 1);
    }

    /**
     * sort by the lexicographically small
     *
     * @param p
     * @param lo
     * @param hi
     * @param <T>
     */
    private static <T extends Comparable<T>> void sort(T[] p, int lo, int hi) {
        Comparator<T> comp = new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.compareTo(o2);
            }
        };
        sort(p, lo, hi, comp);
    }

    private static <T> void sort(T[] p, Comparator<T> comparator) {
        sort(p, 0, p.length - 1, comparator);
    }

    private static <T> void sort(T[] p, int lo, int hi, Comparator<T> comparator) {
        if (hi <= lo) return;
        int lt = lo;
        int gt = hi;

        T v = p[lo];
        int i = lo;
        while (i <= gt) {
            int cmp = comparator.compare(p[i], v);
            if (cmp < 0) {
                exch(p, lt++, i++);
            } else if (cmp > 0) {
                exch(p, i, gt--);
            } else {
                i++;
            }
        }
        sort(p, lo, lt - 1, comparator);
        sort(p, gt + 1, hi, comparator);
    }

    private static void exch(Object[] p, int i, int j) {
        Object buff = p[i];
        p[i] = p[j];
        p[j] = buff;
    }


    private void readTextFile(String aFileName) throws IOException {
        In in = new In(aFileName);
        num = in.readInt();
        points = new Point[num];
        for (int i = 0; i < num; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
    }

    private void findPoints() {

        if (points.length == 0) {
            return;
        }
        //Arrays.copyOf(points, points.length);
        sort(points);
        for (int i = 0; i < points.length - 3; i++) {
            Point point = points[i];
            sorted = Arrays.copyOfRange(points, i + 1, points.length);
            sort(sorted, point.SLOPE_ORDER);


            int start = 0;
            double sl = point.slopeTo(sorted[0]);

            for (int k = 1; k <= sorted.length; k++) {
                if (k == sorted.length
                        || point.slopeTo(sorted[k]) != sl) {
                    if (k - start >= 3) {
                        sort(sorted, start, k - 1);
                        System.out.print(point);
                        for (int j = start; j < k; j++) {
                            System.out.print(" -> ");
                            System.out.print(sorted[j]);
                        }

                        System.out.println();
                    }
                    if (k < sorted.length) {
                        start = k;
                        sl = point.slopeTo(sorted[k]);
                    }
                }

            }

        }

    }

    public static void main(String[] args) {
        String fileName = "";

        long t = System.nanoTime();

        if (args.length > 0) {
            try {
                fileName = args[0].toString();
            } catch (NumberFormatException e) {
                //..
            }
        }

        Fast fast = new Fast();
        try {
            fast.readTextFile(fileName);
            fast.findPoints();
        } catch (IOException e) {
            //e.printStackTrace();
        }

        System.out.println(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t));
    }
}
