package ADT;

import java.util.Iterator;

public class DoublyLinkedList {
    private Node head, tail, current;
    private int size;
    public final int capacity;

    public DoublyLinkedList(int capacity) {
        this.size = 0;
        this.capacity = capacity;
    }

    public void addNode(Vector item) {
        Node newNode = new Node(item);

        if(head == null) {
            current = head = tail = newNode;
            size = 1;
        }
        else {
            if (size == capacity) cutFirst();
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
            size ++;
        }
    }

    public Node get() {
        return current;
    }

    public Iterator<Node> iterator() {
        this.current = this.head;

        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public Node next() {
                return current = current.next;
            }
        };
    }

    public void cutFirst() {
        size--;
        head = head.next;
        head.previous = null;
    }

    public void cutLast() {
        size--;
        tail = tail.previous;
        tail.next = null;
    }

}
