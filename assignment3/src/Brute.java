import java.io.IOException;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Intellectsoft_user
 * Date: 12/5/13
 * Time: 11:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Brute {

    private Point[] points;
    private int num;


    private void findPoints() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        Point[] res = new Point[4];

        for (int i = 0; i < points.length; i++) {
            points[i].draw();
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        double sij = points[i].slopeTo(points[j]);
                        double sik = points[i].slopeTo(points[k]);
                        double sil = points[i].slopeTo(points[l]);

                        if (sij == sik && sij == sil) {
                            res[0] = points[i];
                            res[1] = points[j];
                            res[2] = points[k];
                            res[3] = points[l];

                            Arrays.sort(res);


                            System.out.println(res[0] + " -> "
                                    + res[1] + " -> " + res[2] + " -> " + res[3]);

                            res[0].drawTo(res[1]);
                            res[1].drawTo(res[2]);
                            res[2].drawTo(res[3]);
                        }
                    }
                }
            }
        }
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

    public static void main(String[] args) {
        String fileName = "";

        if (args.length > 0) {
            try {
                fileName = args[0].toString();
            } catch (NumberFormatException e) {
                //..
            }
        }

        Brute br = new Brute();
        try {
            br.readTextFile(fileName);
            br.findPoints();
        } catch (IOException e) {
            //e.printStackTrace();
        }

    }
}
