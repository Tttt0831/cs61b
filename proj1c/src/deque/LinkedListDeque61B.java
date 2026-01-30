package deque;

import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    /**
     * 【要点1】内部节点类 - 使用双向链表
     * 每个节点包含：数据、前驱指针、后继指针
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
     * 【要点2】使用哨兵节点(sentinel node)简化边界情况
     * sentinel.next 指向第一个真实节点
     * sentinel.prev 指向最后一个真实节点
     * 空队列时：sentinel.next == sentinel.prev == sentinel
     */
    private Node sentinel;
    private int size;

    /**
     * 【要点3】构造函数 - 初始化哨兵节点为循环结构
     */
    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * 【要点4】addFirst - 在链表头部添加元素
     * 时间复杂度：O(1)
     */
    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x, sentinel, sentinel.next);
        sentinel.next.prev = newNode;  // 原第一个节点的prev指向新节点
        sentinel.next = newNode;        // sentinel的next指向新节点
        size++;
    }

    /**
     * 【要点5】addLast - 在链表尾部添加元素
     * 时间复杂度：O(1)
     */
    @Override
    public void addLast(T x) {
        Node newNode = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;  // 原最后节点的next指向新节点
        sentinel.prev = newNode;        // sentinel的prev指向新节点
        size++;
    }

    /**
     * 【要点6】toList - 转换为ArrayList
     * 用于测试和调试
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<T>();
        Node current = sentinel.next;
        while (current != sentinel) {
            returnList.add(current.item);
            current = current.next;
        }
        return returnList;
    }

    /**
     * 【要点7】isEmpty - 检查是否为空
     * 可以通过size或检查sentinel.next == sentinel来判断
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 【要点8】size - 返回元素数量
     * 时间复杂度：O(1)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 【要点9】removeFirst - 移除并返回第一个元素
     * 时间复杂度：O(1)
     * 注意：需要处理空队列的情况
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        Node first = sentinel.next;
        T item = first.item;

        // 调整指针：跳过第一个节点
        sentinel.next = first.next;
        first.next.prev = sentinel;

        size--;
        return item;
    }

    /**
     * 【要点10】removeLast - 移除并返回最后一个元素
     * 时间复杂度：O(1)
     * 注意：需要处理空队列的情况
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        Node last = sentinel.prev;
        T item = last.item;

        // 调整指针：跳过最后一个节点
        sentinel.prev = last.prev;
        last.prev.next = sentinel;

        size--;
        return item;
    }

    /**
     * 【要点11】get - 迭代方式获取第index个元素
     * 时间复杂度：O(n)
     * 注意：index从0开始，越界返回null
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
     * 【要点12】getRecursive - 递归方式获取第index个元素
     * 这是LinkedListDeque特有的方法
     * 使用辅助方法实现递归
     */
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    /**
     * 【要点13】递归辅助方法
     * 基础情况：index == 0时返回当前节点的item
     * 递归情况：在下一个节点上查找index-1
     */
    private T getRecursiveHelper(Node node, int index) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(node.next, index - 1);
    }

    /**
     * 【要点14】可选：实现toString方便调试
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
