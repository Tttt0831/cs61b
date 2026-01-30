package deque;

import java.util.ArrayList;
import java.util.List;

/**
 * CS61B Project 1A: LinkedListDeque
 * A double-ended queue implementation using a doubly-linked list with sentinel node.
 *
 * KEY POINT: This implementation uses a circular doubly-linked list with a sentinel
 * node to simplify edge cases and maintain constant-time operations at both ends.
 */
public class LinkedListDeque<T> implements Deque61B<T> {

    /**
     * KEY POINT 1: Inner Node class - Doubly linked list
     * Each node contains: data, previous pointer, next pointer
     * This allows O(1) traversal in both directions
     */
    private class Node {
        T item;
        Node prev;
        Node next;

        Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * KEY POINT 2: Sentinel node simplifies edge cases
     * sentinel.next points to the first real node
     * sentinel.prev points to the last real node
     * Empty deque: sentinel.next == sentinel.prev == sentinel
     *
     * Benefits:
     * - No need to special-case first/last element operations
     * - No null pointer checks needed
     * - Uniform code for all add/remove operations
     */
    private Node sentinel;
    private int size;

    /**
     * KEY POINT 3: Constructor - Initialize circular structure
     * The sentinel points to itself in an empty deque
     */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * KEY POINT 4: addFirst - Add element at the front
     * Time complexity: O(1)
     *
     * Steps:
     * 1. Create new node pointing to sentinel and current first
     * 2. Update current first's prev to new node
     * 3. Update sentinel's next to new node
     * 4. Increment size
     */
    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x, sentinel, sentinel.next);
        sentinel.next.prev = newNode;  // Current first now points back to new node
        sentinel.next = newNode;        // Sentinel points to new node as first
        size++;
    }

    /**
     * KEY POINT 5: addLast - Add element at the back
     * Time complexity: O(1)
     *
     * Steps:
     * 1. Create new node pointing to current last and sentinel
     * 2. Update current last's next to new node
     * 3. Update sentinel's prev to new node
     * 4. Increment size
     */
    @Override
    public void addLast(T x) {
        Node newNode = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;  // Current last now points to new node
        sentinel.prev = newNode;        // Sentinel's prev points to new node
        size++;
    }

    /**
     * KEY POINT 6: toList - Convert to ArrayList
     * Used for testing and debugging
     * Traverses from first to last in logical order
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node current = sentinel.next;
        while (current != sentinel) {
            returnList.add(current.item);
            current = current.next;
        }
        return returnList;
    }

    /**
     * KEY POINT 7: isEmpty - Check if deque is empty
     * Can check either size or sentinel.next == sentinel
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * KEY POINT 8: size - Return number of elements
     * Time complexity: O(1) because we maintain a size variable
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * KEY POINT 9: removeFirst - Remove and return first element
     * Time complexity: O(1)
     *
     * IMPORTANT: Must handle empty deque case
     *
     * Steps:
     * 1. Check if empty, return null if so
     * 2. Save reference to first node and its item
     * 3. Update sentinel.next to skip first node
     * 4. Update new first's prev to sentinel
     * 5. Decrement size
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        Node first = sentinel.next;
        T item = first.item;

        // Skip over first node
        sentinel.next = first.next;
        first.next.prev = sentinel;

        size--;
        return item;
    }

    /**
     * KEY POINT 10: removeLast - Remove and return last element
     * Time complexity: O(1)
     *
     * IMPORTANT: Must handle empty deque case
     *
     * Steps:
     * 1. Check if empty, return null if so
     * 2. Save reference to last node and its item
     * 3. Update sentinel.prev to skip last node
     * 4. Update new last's next to sentinel
     * 5. Decrement size
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        Node last = sentinel.prev;
        T item = last.item;

        // Skip over last node
        sentinel.prev = last.prev;
        last.prev.next = sentinel;

        size--;
        return item;
    }

    /**
     * KEY POINT 11: get - Iterative approach to get element at index
     * Time complexity: O(n) where n is the index
     *
     * IMPORTANT: Index starts at 0, returns null if out of bounds
     *
     * Algorithm:
     * 1. Validate index bounds
     * 2. Start from first real node (sentinel.next)
     * 3. Traverse forward index times
     * 4. Return item at that node
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node current = sentinel.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    /**
     * KEY POINT 12: getRecursive - Recursive approach to get element
     * This is specific to LinkedListDeque (not in interface)
     * Uses a helper method to implement recursion
     *
     * Time complexity: O(n)
     * Space complexity: O(n) due to call stack
     */
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    /**
     * KEY POINT 13: Recursive helper method
     * Base case: index == 0, return current node's item
     * Recursive case: search in next node with index-1
     *
     * This demonstrates the recursive thinking pattern:
     * "To get element at index n, get element at index n-1 from next node"
     */
    private T getRecursiveHelper(Node node, int index) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(node.next, index - 1);
    }

    /**
     * KEY POINT 14: Optional toString for debugging
     * Makes it easy to see the deque's contents
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node current = sentinel.next;
        while (current != sentinel) {
            sb.append(current.item);
            if (current.next != sentinel) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}