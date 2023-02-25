package ADT;

public class Node {
    private final Vector item;
    Node previous;
    Node next;

    public Node(Vector item) {
        this.item = item;
    }

    public Vector getItem() {
        return this.item;
    }
}
