package COS285Lab4;
import java.util.*;

/**Creates a MyBinaryTree object that stores unsorted nodes in a binary tree.
 * @param <E> the data type of the elements in the tree
 * @author Abby Pitcairn
 * @version October 18, 2025
 */
public class MyBinaryTree<E extends Comparable<E>> {
    
    /**Root Node of the tree*/
    protected Node<E> root;
    
    /**Nested class for a Node object
     * @param <E> the data type of the element in the node
     */
    protected static class Node<E>{
        E data;
        Node<E> left;
        Node<E> right;
        
        Node(E data){
            this.data = data;
        }
    }
    
    /**Recursively searches for the next available insertion spot
     * and inserts a new node with the given value to that space
     * in the tree.
     * @param value - the value to insert into the tree.
     */
    public void insert(E value) {
        root = insertRecursively(root, value);
    }

    /** Recursive helper for insertion.
     * @param current - the current Node being evaluated.
     * @param value - the value to insert into the tree.
     */
    private Node<E> insertRecursively(Node<E> current, E value) {
        if (current == null) {
            return new Node<>(value);
        }
        if (value.compareTo(current.data) < 0) {
            current.left = insertRecursively(current.left, value);
        } else if (value.compareTo(current.data) > 0) {
            current.right = insertRecursively(current.right, value);
        }
        return current;
    }
    
    /** Helper function to quickly build a tree from a List of elements.
     * @param elements - a List of data type E to be added to the tree.
     */
    public void buildTree(List<E> elements) {
        for (E element : elements) {
            insert(element);
        }
    }

    /**
     * Method to perform a breadth-first search (BFS) print of the tree
     * @return A string representation of the tree in BFS order.
     */
    public String bfsPrint() {
        if (root == null) {
            return "";
        }
        
        StringBuilder result = new StringBuilder();
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            Node<E> current = queue.poll();
            result.append(current.data).append(" ");
            
            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }
        
        return result.toString().trim();
    }

    /**
     * Method to check if a target value exists in the tree
     * @param target - the value to search for
     * @return true if the value exists, false otherwise
     */
    public boolean contains(E target) {
        return containsRecursively(root, target);
    }
    private boolean containsRecursively(Node<E> current, E target) {
        if (current == null) {
            return false;
        }
        if (current.data.equals(target)) {
            return true;
        }
        return containsRecursively(current.left, target) || containsRecursively(current.right, target);
    }
}
