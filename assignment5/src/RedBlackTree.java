/**
 * Created with IntelliJ IDEA.
 * Date: 12/18/13
 * Time: 10:31 PM
 */
public class RedBlackTree {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private static class Node implements Comparable<Node> {
        private Point2D point;
        private Node left; //left subtree
        private Node right; //right subtree
        boolean color;

        public int compareTo(Node n) {
            return point.compareTo(n.point);
        }

        public Node(Point2D p, boolean c){
            this.point = p;
            left = null;
            right = null;
            color = c;
        }
    }



    /**
     * Check if the color of the link is red
     */
    private boolean isRed(Node n) {
        if (n == null) {  //null links should be black
            return BLACK;
        } else {
            return n.color == RED;
        }
    }


    /**
     * if the red link is leading to the right - we need to rotate the subtree to
     * the left
     *
     * @param n top of the subtree
     * @return updated top of the subtree
     */
    private Node rotateSubtreeLeft(Node n) {
        //rotate only the red link to the right
        assert isRed(n.right);
        Node r = n.right;
        n.right = r.left;
        r.left = n;
        r.color = n.color;
        n.color = RED;

        return r;
    }

    /**
     * rotate left the subtree (needed for some operations)
     *
     * @param n top of the subtree
     * @return updated top of the subtree
     */
    private Node rotateSubtreeRight(Node n) {
        //rotate only if the left link is red
        assert isRed(n.left);
        Node l = n.left;
        n.left = l.right;
        l.right = n;
        l.color = n.color;
        n.color = RED;

        return l;
    }

    /**
     * if the left and right childs links from the top are red - we need to flip
     * the colors and make them black, link to the top - red
     *
     * @param n
     */
    private void flipColors(Node n) {
        //top link should not be red
        assert !isRed(n);
        //both right and left links from the top are red
        assert isRed(n.left);
        assert isRed(n.right);

        n.color = RED; //set the top link red
        n.left.color = BLACK;
        n.right.color = BLACK; //change color of the both links from top

    }

    private Node put(Node n, Point2D point) {
        //start with inserting at bottom and color red
        if (n == null) {
            return new Node(point, RED);
        }
        int cmp = point.compareTo(n.point);
        if (cmp < 0) {
            n.left = put(n.left, point);
        } else if (cmp > 0) {
            n.right = put(n.right, point);
        } else if (cmp == 0) {
            n.point = point;
        }

        //lean left
        if(isRed(n.right) && !isRed(n.left)){
            n = rotateSubtreeLeft(n);
        }
        //balance 4-node
        if(isRed(n.left) && isRed(n.left.left)){
            n = rotateSubtreeRight(n);
        }
        //we have 4-node, need to split
        if(isRed(n.left) && isRed(n.right)){
            flipColors(n);
        }

        return n;
    }
}
