import java.lang.Math;

/**
 * A Binary Search Tree implementation.
 * The data type E must implement the Comparable interface.
 * @author Chris A, Chris B - modified from Abby Pitcairn and Behrooz Mansouri
 * @version November 4, 2025
 */
public class BinarySearchTree<E extends Comparable<E>> {

    // ---------------- Nested Class for a tree node ----------------
    protected static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;
        int height;

        public Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1; // A new node is initially a leaf with height 1
        }
    }
    // ---------------- End of Nested Class ----------------

    protected Node<E> root;

    /**
     * Constructs an empty binary search tree.
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Returns the height of a node.
     * @param N The node.
     * @return The height of the subtree rooted at N, or 0 if N is null.
     */
    protected int height(Node<E> N) {
        if (N == null)
            return 0;
        return N.height;
    }

    /**
     * Updates the height of a given node based on its children's heights.
     * @param n The node to update.
     */
    private void updateHeight(Node<E> n) {
        if (n != null) { // Added null check for safety
            n.height = 1 + Math.max(height(n.left), height(n.right));
        }
    }

    /**
     * Gets the balance factor of a node (height of left subtree - height of right subtree).
     * @param N The node.
     * @return The balance factor, or 0 if N is null.
     */
    private int getBalance(Node<E> N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    /**
     * Public method to add a new item to the tree.
     * @param data The data to insert.
     */
    public void add(E data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the tree");
        }
        root = add(root, data);
    }

    /**
     * Private recursive helper method to add an item.
     * @param node The current node in the traversal.
     * @param data The data to insert.
     * @return The (potentially new) root of the modified subtree.
     */
    private Node<E> add(Node<E> node, E data) {
        // 1. Base Case: Found the correct empty spot.
        if (node == null) {
            return new Node<>(data);
        }

        // 2. Recursive Step: Compare and go left or right.
        int compare = data.compareTo(node.data);

        if (compare < 0) {
            // Data is smaller, go left
            node.left = add(node.left, data);
        } else if (compare > 0) {
            // Data is larger, go right
            node.right = add(node.right, data);
        } else {
            // Data is equal --> we have a duplicate.
            // We return the original node unchanged.
            return node;
        }

        // 3. Update Height: After insertion, update the height of this node.
        updateHeight(node);

        // 4. Get Balance:
        // The getBalance() method isn't needed for a simple BST add,
        // but it would be used here if you were implementing rotations for an AVL tree.

        // 4. Return the (possibly updated) node to its parent.
        return node;
    }

    /**
     * Public method to print the tree's elements in ascending order (in-order traversal).
     */
    public void printInOrder() {
        System.out.println("In-order Traversal:");
        printInOrder(root);
        System.out.println(); // Add a newline at the end
    }

    /**
     * Private recursive helper method for in-order traversal.
     * @param node The current node in the traversal.
     */
    private void printInOrder(Node<E> node) {
        // Base Case:
        if (node == null) {
            return;
        }

        // 1. Visit left subtree
        printInOrder(node.left);

        // 2. Visit/print current node
        System.out.print(node.data + " ");

        // 3. Visit right subtree
        printInOrder(node.right);
    }
    /**
     * Public method to return summation of depths of all nodes in tree
     * @return int value for sum of depths of tree's nodes
     */
    public int sumDepths() {
        return sumDepths(root, 1);
    }
    /**
     * Private method to return summation of depths of current subtree
     * @param node The node of the current subtree
     * @param currentDepth The depth of the current node
     * @return The summation of depths of the current subtree
     */
    private int sumDepths(Node<E> node, int currentDepth){
        if(node == null){
            return 0;
        }
        return currentDepth + sumDepths(node.left, currentDepth + 1) + sumDepths(node.right, currentDepth + 1);
    }
    
    /**
     * Public method to print all node data in nodes that are skewed 2L
     */
    public void findAndPrint2LNodes() {
        findAndPrint2LNodes(root);
        System.out.println();
    }
    /**
     * Private method to print nodes that are skewed 2L
     * @param node Node currently being examined
     */
    private void findAndPrint2LNodes(Node<E> node){
        if(node == null){
            return;
        }
        if(getBalance(node) == 2){
            System.out.print(node.data + " ");
        }
        findAndPrint2LNodes(node.left);
        findAndPrint2LNodes(node.right);
    }
    /**
     * Public method for checking if tree is a valid BST
     * @return True/False for BST validity
     */
    public boolean isBST() {
        //assuming empty tree is valid BST 
        return isBST(root, null, null);
    }
    /**
     * Private method for checking if current node's subtree is valid BST
     * @param node Node currently being examined
     * @param min Minimum to compare to node.data
     * @param max Maximum to compare to node.data
     * @return True/False if tree is valid BST
     */
    private boolean isBST(Node<E> node, E min, E max){
        if(node == null){
            return true;
        }
        if(max != null && node.data.compareTo(max) >= 0){
            return false;
        }
        if(min != null && node.data.compareTo(min) <= 0){
            return false;
        }
        return isBST(node.left, min, node.data) && isBST(node.right, node.data, max);
    }
    /**
     * Public method for checking if tree is valid AVL tree
     * @return True if valid AVL tree, otherwise false
     */
    public boolean isAVL() {
        if(isBST() == false)
            return false;
        //tree is valid BST, check for correct balance
        return isAVL(root);
    }
    /**
     * Private method for checking if node's subtree is valid AVL tree
     * @param node Current node being examined
     * @return True if node's subtree is valid AVL, otherwise false
     */
    private boolean isAVL(Node<E> node){
        if(node == null)
            return true;
        int currBalance = getBalance(node);
        if(currBalance > 1 || currBalance < -1)
            return false;
        return isAVL(node.left) && isAVL(node.right);
    }
    
    
    // Main Method for Testing
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        
        // Add elements
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(60);
        bst.add(80);
        bst.add(10);

        // Print in order
        bst.printInOrder(); // Expected: 20 30 40 50 60 70 80 

        // Test height
        System.out.println("Height of root (50): " + bst.height(bst.root)); // Expected: 3
        System.out.println("Height of 30: " + bst.height(bst.root.left)); // Expected: 2
        System.out.println("Height of 80: " + bst.height(bst.root.right.right)); // Expected: 1

        // Test depth summation
        System.out.println("Depth summation: " + bst.sumDepths());

        // Test 2L finding
        System.out.print("2L Nodes: ");
        bst.findAndPrint2LNodes();

        // Test BST validation
        System.out.println("Valid BST: " + bst.isBST());

        // Test AVL validation
        System.out.println("Valid AVL (tree is currently balanced): " + bst.isAVL());
        System.out.println("Adding to tree to skew it leftward");
        bst.add(5);
        System.out.println("Valid AVL (tree should be less balanced): " + bst.isAVL());
    }
}
