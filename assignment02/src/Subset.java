
/**
 * Created with IntelliJ IDEA.
 * User: Intellectsoft_user
 * Date: 12/3/13
 * Time: 12:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class Subset {
    public static void main(String[] args) {
        int k = 0;
        int length = 0;
        String[] str;

        if (args.length > 0) {
            try {
                k = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + " must be an integer");
            }
        }
        str = StdIn.readAllStrings();
        length = str.length;

        if (k >= 0 && k <= length) {
            RandomizedQueue<String> randQ = new RandomizedQueue<String>();
            for (String s : str) {
                randQ.enqueue(s);
            }

            for (int i = 0; i < k; i++) {
                System.out.println(randQ.dequeue());
            }
        } else {
            System.out.println("K value is out of bounds");
        }

    }
}
