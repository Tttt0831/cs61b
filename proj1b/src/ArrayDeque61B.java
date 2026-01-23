import java.util.ArrayList;
import java.util.List;

/**
 * CS61B Project 1B: ArrayDeque61B
 * 使用循环数组实现的双端队列
 */
public class ArrayDeque61B<T> implements Deque61B<T> {

    /**
     * 【要点1】核心数据结构 - 循环数组
     * items: 存储元素的数组
     * size: 当前元素数量
     * nextFirst: 下一个要添加到首部的位置（空位）
     * nextLast: 下一个要添加到尾部的位置（空位）
     */
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /**
     * 【要点2】初始容量和扩缩容因子
     * INITIAL_CAPACITY: 初始数组大小（通常为8）
     * REFACTOR: 扩容因子（2倍）
     * MIN_CAPACITY: 最小容量，防止过度缩容
     * USAGE_RATIO: 使用率阈值，低于此值触发缩容
     */
    private static final int INITIAL_CAPACITY = 8;
    private static final int REFACTOR = 2;
    private static final int MIN_CAPACITY = 16;
    private static final double USAGE_RATIO = 0.25;

    /**
     * 【要点3】构造函数 - 初始化循环数组
     * nextFirst和nextLast的初始位置很重要
     * 通常设置为相邻位置，形成"空"的循环结构
     */
    public ArrayDeque61B() {
        items = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /**
     * 【要点4】辅助方法 - 计算循环数组的前一个索引
     * 使用模运算实现循环：(index - 1 + length) % length
     * 加length是为了处理负数情况
     */
    private int minusOne(int index) {
        return (index - 1 + items.length) % items.length;
    }

    /**
     * 【要点5】辅助方法 - 计算循环数组的后一个索引
     * 使用模运算实现循环：(index + 1) % length
     */
    private int plusOne(int index) {
        return (index + 1) % items.length;
    }

    /**
     * 【要点6】扩容操作 - 当数组满时扩大容量
     * 核心难点：需要重新排列元素，保持逻辑顺序
     * 策略：将所有元素从索引0开始连续存放
     */
    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];

        // 从第一个真实元素开始复制
        int oldIndex = plusOne(nextFirst);
        for (int newIndex = 0; newIndex < size; newIndex++) {
            newItems[newIndex] = items[oldIndex];
            oldIndex = plusOne(oldIndex);
        }

        // 重新设置指针
        items = newItems;
        nextFirst = capacity - 1;  // 第一个元素前面
        nextLast = size;            // 最后一个元素后面
    }

    /**
     * 【要点7】检查是否需要扩容
     * 当size == items.length时数组已满
     */
    private void checkResize() {
        if (size == items.length) {
            resize(items.length * REFACTOR);
        }
    }

    /**
     * 【要点8】检查是否需要缩容（内存优化）
     * 当数组使用率低且容量大于最小值时缩容
     * 避免频繁的扩缩容（只在使用率很低时触发）
     */
    private void checkDownsize() {
        double usageRatio = (double) size / items.length;
        if (items.length >= MIN_CAPACITY && usageRatio < USAGE_RATIO) {
            resize(items.length / REFACTOR);
        }
    }

    /**
     * 【要点9】addFirst - 在数组头部添加元素
     * 时间复杂度：均摊O(1)
     * 步骤：
     * 1. 检查是否需要扩容
     * 2. 在nextFirst位置放入元素
     * 3. 更新nextFirst（向前移动）
     * 4. size++
     */
    @Override
    public void addFirst(T x) {
        checkResize();
        items[nextFirst] = x;
        nextFirst = minusOne(nextFirst);
        size++;
    }

    /**
     * 【要点10】addLast - 在数组尾部添加元素
     * 时间复杂度：均摊O(1)
     * 步骤：
     * 1. 检查是否需要扩容
     * 2. 在nextLast位置放入元素
     * 3. 更新nextLast（向后移动）
     * 4. size++
     */
    @Override
    public void addLast(T x) {
        checkResize();
        items[nextLast] = x;
        nextLast = plusOne(nextLast);
        size++;
    }

    /**
     * 【要点11】toList - 转换为ArrayList
     * 注意：按逻辑顺序添加，不是物理顺序
     * 从第一个真实元素开始，循环遍历size次
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int index = plusOne(nextFirst);  // 第一个真实元素
        for (int i = 0; i < size; i++) {
            returnList.add(items[index]);
            index = plusOne(index);
        }
        return returnList;
    }

    /**
     * 【要点12】isEmpty - 检查是否为空
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 【要点13】size - 返回元素数量
     * 时间复杂度：O(1)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 【要点14】removeFirst - 移除并返回第一个元素
     * 时间复杂度：均摊O(1)
     * 步骤：
     * 1. 检查空队列
     * 2. 计算第一个元素的索引
     * 3. 保存元素值
     * 4. 将位置设为null（避免内存泄漏）
     * 5. 更新nextFirst（向后移动）
     * 6. size--
     * 7. 检查是否需要缩容
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        int firstIndex = plusOne(nextFirst);
        T item = items[firstIndex];
        items[firstIndex] = null;  // 避免loitering
        nextFirst = firstIndex;
        size--;

        checkDownsize();
        return item;
    }

    /**
     * 【要点15】removeLast - 移除并返回最后一个元素
     * 时间复杂度：均摊O(1)
     * 步骤类似removeFirst
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        int lastIndex = minusOne(nextLast);
        T item = items[lastIndex];
        items[lastIndex] = null;  // 避免loitering
        nextLast = lastIndex;
        size--;

        checkDownsize();
        return item;
    }

    /**
     * 【要点16】get - 获取第index个元素（逻辑索引）
     * 时间复杂度：O(1) - ArrayDeque的优势！
     * 关键：将逻辑索引转换为物理索引
     * 公式：physicalIndex = (nextFirst + 1 + index) % items.length
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        int physicalIndex = (plusOne(nextFirst) + index) % items.length;
        return items[physicalIndex];
    }

    /**
     * 【要点17】getRecursive - 递归实现get
     * 注意：ArrayDeque用递归不是最优选择
     * 但为了接口一致性，这里实现一个
     * 实际上内部还是用循环更合理
     */
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(plusOne(nextFirst), index);
    }

    /**
     * 【要点18】递归辅助方法
     * 每次递归将索引向后移动一位
     */
    private T getRecursiveHelper(int physicalIndex, int logicalIndex) {
        if (logicalIndex == 0) {
            return items[physicalIndex];
        }
        return getRecursiveHelper(plusOne(physicalIndex), logicalIndex - 1);
    }

    /**
     * 【要点19】toString - 方便调试
     * 显示逻辑顺序和物理结构
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ArrayDeque: [");
        int index = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            sb.append(items[index]);
            if (i < size - 1) {
                sb.append(", ");
            }
            index = plusOne(index);
        }
        sb.append("]");
        sb.append(" (size=" + size + ", capacity=" + items.length + ")");
        return sb.toString();
    }

    /**
     * 【要点20】可选：实现equals方法
     * 比较两个Deque的内容是否相同
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque61B)) {
            return false;
        }

        Deque61B<?> other = (Deque61B<?>) o;
        if (this.size != other.size()) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }
}