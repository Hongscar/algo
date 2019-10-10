package algo;

import java.util.HashMap;

/**
 * @Author: Seth
 * @Description:    using double pointer to accomplish LRUCache
 * @Date: Created in 12:20 2019/10/10
 */

// time complex is O(1), and space cost is O(n)
public class LRUCache1 {
    class DLinkedNode {
        DLinkedNode prev;
        DLinkedNode next;
        int key;
        int value;
        DLinkedNode() { }
        DLinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public void moveToHead(DLinkedNode node) {
        removeNode(node);
        addNode(node);
    }

    public void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public DLinkedNode removeTail() {
        if (tail.prev.prev != null) {   // in this sample, it's duplicate
            DLinkedNode last = tail.prev;
            last.prev.next = tail;
            tail.prev = last.prev;
            return last;
        }
        return null;
        // else, LinkedList only has the head node and the tail node
    }

    // the node must add at the head
    public void addNode(DLinkedNode node) {
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    public HashMap<Integer, DLinkedNode> map;
    public int capacity;
    public DLinkedNode head;
    public DLinkedNode tail;

    public LRUCache1(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(2 * capacity);
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;       // head.prev == null
        tail.prev = head;       // tail.next == null
    }

    public int get(int key) {
        DLinkedNode integer = map.get(key);
        if (integer == null)
            return -1;          // not exist this key-value
        moveToHead(integer);
        return integer.value;
    }

    public void put(int key, int value) {
        DLinkedNode node1 = map.get(key);
        if (map.size() == capacity && node1 == null) {  // if node1 exists,only need to change the value
            DLinkedNode lastNode = removeTail();
            map.remove(lastNode.key);
        }
        if (node1 != null) {
            removeNode(node1);
            DLinkedNode newNode = new DLinkedNode(key, value);
            addNode(newNode);
            map.put(key, newNode);
        }
        else {
            DLinkedNode newNode = new DLinkedNode(key, value);
            addNode(newNode);
            map.put(key, newNode);
        }
    }

    public static void main(String[] args) {
        LRUCache1 cache1 = new LRUCache1(2);
        cache1.put(1, 1);
        cache1.put(2, 2);
        System.out.println(cache1.get(1));
        cache1.put(3, 3);
        System.out.println(cache1.get(2));
        cache1.put(4, 4);
        System.out.println(cache1.get(1));
        System.out.println(cache1.get(3));
        System.out.println(cache1.get(4));
    }
}
