import java.util.*;

public class AVLTree<T extends Comparable<T>> {
    Node<T> root;

    public AVLTree() {
        root = null;
    }

    public AVLTree(Node<T> node) {
        root = node;
    }

    public T Maximum() {
        Node<T> local = root;
        if (local == null)
            return null;
        while (local.getRight() != null)
            local = local.getRight();
        return local.getData();
    }

    public T Minimum() {
        Node<T> local = root;
        if (local == null)
            return null;
        while (local.getLeft() != null) {
            local = local.getLeft();
        }
        return local.getData();
    }

    private int depth(Node<T> node) {
        if (node == null)
            return 0;
        return node.getDepth();
    }

    /** Insert data to the AVL tree
     *
     * @param data - a value to insert
     */
    public Node<T> insert(T data) {
        root = insert(root, data);
        switch (balanceNumber(root)) {
            case 1:
                root = rotateLeft(root);
                break;
            case -1:
                root = rotateRight(root);
                break;
            default:
                break;
        }
        return root;
    }

    /** Insert data to the specific node in the AVL tree
     *
     * @param node - a node where to insert
     * @param data - a value to insert
     */
    public Node<T> insert(Node<T> node, T data) {
        if (node == null)
            return new Node<>(data);
        if (node.getData().compareTo(data) > 0) {
            node = new Node<>(node.getData(), insert(node.getLeft(), data), node.getRight());
        } else if (node.getData().compareTo(data) < 0) {
            node = new Node<>(node.getData(), node.getLeft(), insert(node.getRight(), data));
        }

        switch (balanceNumber(node)) {
            case 1:
                node = rotateLeft(node);
                break;
            case -1:
                node = rotateRight(node);
                break;
            default:
                return node;
        }
        return node;
    }

    /** Balance the depth of the AVL tree
     */
    private int balanceNumber(Node<T> node) {
        int L = depth(node.getLeft());
        int R = depth(node.getRight());
        if (L - R >= 2)
            return -1;
        else if (L - R <= -2)
            return 1;
        return 0;
    }

    /** Perform the left rotation
     */
    private Node<T> rotateLeft(Node<T> node) {
        Node<T> q = node;
        Node<T> p = q.getRight();
        Node<T> c = q.getLeft();
        Node<T> a = p.getLeft();
        Node<T> b = p.getRight();
        q = new Node<>(q.getData(), c, a);
        p = new Node<>(p.getData(), q, b);
        return p;
    }

    /** Perform the right rotation
     */
    private Node<T> rotateRight(Node<T> node) {
        Node<T> q = node;
        Node<T> p = q.getLeft();
        Node<T> c = q.getRight();
        Node<T> a = p.getLeft();
        Node<T> b = p.getRight();
        q = new Node<>(q.getData(), b, c);
        p = new Node<>(p.getData(), a, q);
        return p;
    }

    /** Iterative method to find a value in the AVL tree
     *
     * @param data - a value to be found in the AVL tree
     * @return - an information where is data
     */
    public String i_search(T data) {
        Node<T> local = root;
        while (local != null) {
            if (local.getData().compareTo(data) == 0)
                return "\nData: " + data + "; level: " + local.level;
            else if (local.getData().compareTo(data) > 0)
                local = local.getLeft();
            else
                local = local.getRight();
        }
        return "\nNo " + data + " in AVL tree";
    }

    /** Recursive method to find a value in the AVL tree
     *
     * @param data - a value to be found in the AVL tree
     * @return - an information where is data
     */
    public String r_search(T data) {
        return search(data, root);
    }

    public String search(T data, Node<T> local) {
        if (local != null) {
            if (local.getData().compareTo(data) == 0)
                return "\nData: " + data + "; level: " + (local.level);
            else if (local.getData().compareTo(data) > 0)
                return search(data, local.getLeft());
            else if (local.getData().compareTo(data) < 0)
                return search(data, local.getRight());
        }

        return "\nNo " + data + " in AVL tree";
    }

    /** Check if tree is AVL
     *
     * @param node - a node to be checked
     * @return - return boolean
     */
    public boolean isAVL(Node<T> node) {
        if (node == null)
            return true;
        else if (node.getRight() == null && node.getLeft() != null)
            return node.getData().compareTo(node.getLeft().getData()) > 0
                    && isAVL(node.getLeft())
                    && balanceNumber(node) == 0;
        else if (node.getRight() != null && node.getLeft() == null)
            return node.getData().compareTo(node.getRight().getData()) < 0
                    && isAVL(node.getRight())
                    && balanceNumber(node) == 0;
        else if (node.getRight() != null && node.getLeft() != null)
            return node.getData().compareTo(node.getLeft().getData()) > 0
                    && isAVL(node.getLeft())
                    && node.getData().compareTo(node.getRight().getData()) < 0
                    && isAVL(node.getRight())
                    && balanceNumber(node) == 0;
        return true;
    }

    public void PrintTree() {
        root.level = 0;
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            System.out.println(node);
            int level = node.level;
            Node<T> left = node.getLeft();
            Node<T> right = node.getRight();
            if (left != null) {
                left.level = level + 1;
                queue.add(left);
            }
            if (right != null) {
                right.level = level + 1;
                queue.add(right);
            }
        }
    }

    /** Constructor for AVLTree, where tree is created from ArrayList<T> items
     */
    public AVLTree (ArrayList<T> items) {
        create(items);
    }

    /** Create a tree from ArrayList
     *
     * @param items - ArrayList of objects which transforms into a tree
     */
    private void create (ArrayList<T> items) {
        if (items.size() == 0) { throw new IllegalArgumentException("The list is empty."); }

        root = new Node<>(items.get(0));

        final Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        final int half = items.size() / 2;

        for (int i = 0; i < half; i++) {
            if (items.get(i) != null) {
                final Node<T> current = queue.poll();
                int left = 2 * i + 1;
                int right = 2 * i + 2;

                if (items.get(left) != null) {
                    current.setLeft(new Node<>(items.get(left)));
                    queue.add(current.getLeft());
                }
                if (right < items.size() && items.get(right) != null) {
                    current.setRight(new Node<>(items.get(right)));
                    queue.add(current.getRight());
                }
            }
        }
    }

}