import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Intellectsoft_user
 * Date: 11/29/13
 * Time: 10:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class PercolationStats {

    private double[] results;
    private int experimentsNumber;
    private double mean;
    private double dev;
    private double loverBound;
    private double highBound;
    private int numberOfCells;

    /**
     * @param N
     * @param T
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N or T <=0");
        }

        results = new double[T];
        experimentsNumber = T;
        numberOfCells = N * N;
        for (int i = 0; i < T; i++) {
            results[i] = experiment(N);
        }

        mean = mean();
        dev = stddev();
        loverBound = confidenceLo();
        highBound = confidenceHi();

    }

    private double experiment(int n) {
        Percolation testPerc = new Percolation(n);
        Random rand = new Random();
        int openedCount = 0;

        while (!testPerc.percolates()) {
            int i = rand.nextInt(n) + 1;
            int j = rand.nextInt(n) + 1;
            if (!testPerc.isOpen(i, j)) {
                testPerc.open(i, j);
                openedCount++;
            }
        }

        return ((double) openedCount / numberOfCells);

    }

    /**
     * sample mean of percolation threshold
     *
     * @return
     */
    public double mean() {
//        double sum = 0;
//
//        for (int i = 0; i < results.length; i++) {
//          sum+=results[i];
//        }
        //return (sum/experimentsNumber);
        return StdStats.mean(results);
    }

    /**
     * // sample standard deviation of percolation threshold
     *
     * @return
     */

    public double stddev() {
//       double sum = 0;
//        for (int i = 0; i < results.length; i++) {
//            sum+=((results[i] - mean) * (results[i] - mean));
//        }
//
//        return (sum/(experimentsNumber-1));

        return StdStats.stddev(results);
    }

    /**
     * // returns lower bound of the 95% confidence interval
     *
     * @return
     */
    public double confidenceLo() {
        return (mean - ((1.96 * dev) / Math.sqrt(experimentsNumber)));
    }

    /**
     * // returns upper bound of the 95% confidence interval
     *
     * @return
     */
    public double confidenceHi() {
        return (mean + ((1.96 * dev) / Math.sqrt(experimentsNumber)));
    }

    /**
     * test client, described below
     *
     * @return
     */
    public static void main(String[] args) {
        int N;
        int T;
        if (args.length > 0) {
            try {
                N = Integer.parseInt(args[0]);
                T = Integer.parseInt(args[1]);
                PercolationStats expStats = new PercolationStats(N, T);
                System.out.printf("mean = %.10f%n", expStats.mean);
                System.out.printf("stddev = %.10f%n", expStats.dev);
                System.out.printf("95%% confidence interval = %.10f, %.10f%n",
                        expStats.loverBound, expStats.highBound);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + " must be an integer");
            }
        }
    }

}
