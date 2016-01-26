package distinguish;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by zzt on 1/26/16.
 * <p>
 * Usage:
 */
public class TrieTree<T extends Comparable> {

    private Node pointer;

    public void add(T data) {
        addUnder(pointer, data);
    }

    private void addUnder(Node root, T data) {
        if (root == null) {
            root = new Node(data);
        } else {

        }
    }

    public boolean empty() {
        return pointer == null;
    }

    class Node {
        private T data;
        private TreeSet<Node> children = new TreeSet<>();

        public Node(T data) {
            this.data = data;
        }

        public TreeSet<Node> getChildren() {
            return children;
        }
    }
}
