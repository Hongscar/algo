package algo.datastructure;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 14:00 2019/10/15
 */

class Node {
    int val;
    Node next;

    public Node() {}
    public Node(int val) {
        this.val = val;
    }
}

public class MyLinkedList {
    public Node head;
    public Node tail;
    public int size;

    /** Initialize your data structure here. */
    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if (size <= index || index < 0)
            return -1;
        Node current = head;
        int i = 0;
        while (current != null && i != index) {
            current = current.next;
            i++;
        }
        return current.val;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        Node newHead = new Node(val);
        newHead.next = head;
        head = newHead;
        size++;
        if (tail == null)
            tail = head;
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        Node newTail = new Node(val);
        if (tail == null)
            tail = head = newTail;
        else {
            tail.next = newTail;
            tail = tail.next;
        }
        size++;

    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if (index > size )
            return;
        if (index == size) {
            addAtTail(val);
            return;
        }
        if (index <= 0) {
            addAtHead(val);
            return;
        }
        int i = 0;
        Node current = head;
        while (current != null && i != index - 1) {
            current = current.next;
            i++;
        }
        Node newNode = new Node(val);
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if (index >= size || index < 0)
            return;
        if (index == 0) {
            head = head.next;
            size--;
            if (head == null) // prev_size == 1
                tail = null;
            return;
        }
        if (index == size - 1) {
            Node current = head;
            for (int i = 0; i < size - 2; i++)
                current = current.next;
            current.next = current.next.next;
            size--;
            tail = current;
            return;
        }
        Node current = head;
        for (int i = 0; i < index - 1; i++)
            current = current.next;
        current.next = current.next.next;
        size--;
    }

    @Override
    public String toString() {
        Node current = head;
        String str = "[";
        while (current != null)
            if (current.next == null)
                str += current.val + "]";
            else
                str += current.val + ", ";
        return str;
    }

    public static void main(String[] args) {
        MyLinkedList list = new MyLinkedList();
        System.out.println(list.size);
        list.addAtHead(1);
        list.addAtHead(2);
        list.addAtHead(3);
        System.out.print(list);
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */