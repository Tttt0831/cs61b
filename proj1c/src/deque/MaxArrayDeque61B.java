package deque;

import java.util.Comparator;

/**
 * CS61B Project 1C: MaxArrayDeque61B
 *
 * KEY POINT 1: Inheritance and Enhancement
 * MaxArrayDeque61B extends ArrayDeque61B to add max() functionality
 * Uses Comparator to compare elements, supporting different comparison strategies
 *
 * This is an example of the Strategy Pattern:
 * - Algorithm (finding max) stays the same
 * - Strategy (how to compare) can vary
 */
public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {

    /**
     * KEY POINT 2: Store Comparator
     * Used for the default max() method
     * private final ensures immutability
     *
     * Why do we need Comparator?
     * - Generic type T doesn't have comparison operators like >
     * - Comparator provides a way to compare any type T
     */
    private final Comparator<T> comparator;

    /**
     * KEY POINT 3: Constructor - Accept Comparator parameter
     * Must pass in a Comparator to define what "maximum" means
     *
     * Example usage:
     * MaxArrayDeque61B<Integer> deque = new MaxArrayDeque61B<>(Integer::compare);
     * MaxArrayDeque61B<String> deque = new MaxArrayDeque61B<>((a,b) -> a.length() - b.length());
     *
     * @param c Comparator used to compare elements
     */
    public MaxArrayDeque61B(Comparator<T> c) {
        super();  // Call parent class constructor
        this.comparator = c;
    }

    /**
     * KEY POINT 4: max() - Use default comparator
     * Returns the "maximum" element according to the stored comparator
     * Time complexity: O(n) - must examine all elements
     *
     * Key considerations:
     * - Must traverse all elements to find max
     * - Uses the comparator provided in constructor
     * - Returns null for empty deque
     *
     * @return the maximum element in the deque, or null if empty
     */
    public T max() {
        return max(this.comparator);
    }

    /**
     * KEY POINT 5: max(Comparator) - Use custom comparator
     * Allows using different comparison strategies to find "maximum"
     * This demonstrates the Strategy Pattern in action
     *
     * Examples:
     * - Use default comparator to find largest
     * - Use reverse comparator to find smallest
     * - Use custom comparator to find by different criteria
     *
     * Algorithm:
     * 1. Start with first element as max
     * 2. Compare each element with current max
     * 3. Update max if current element is "greater"
     *
     * @param c Comparator to use for comparison
     * @return the maximum element according to c, or null if empty
     */
    public T max(Comparator<T> c) {
        // Handle empty deque
        if (isEmpty()) {
            return null;
        }

        // KEY POINT 6: Traversal algorithm
        // Assume first element is max, then compare with rest
        T maxItem = get(0);

        for (int i = 1; i < size(); i++) {
            T current = get(i);

            // Use comparator to compare
            // If current > maxItem (c.compare returns positive), update maxItem
            if (c.compare(current, maxItem) > 0) {
                maxItem = current;
            }
        }

        return maxItem;
    }

    /**
     * KEY POINT 7: Optional enhancement - equals method
     * Compare two MaxArrayDeque61B for equality
     * NOTE: Only compares content, not comparators
     *
     * Design decision: Two deques are equal if they have same elements
     * in same order, regardless of their comparators
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof MaxArrayDeque61B)) {
            return false;
        }

        MaxArrayDeque61B<?> o = (MaxArrayDeque61B<?>) other;

        if (this.size() != o.size()) {
            return false;
        }

        for (int i = 0; i < size(); i++) {
            if (!this.get(i).equals(o.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * KEY POINT 8: toString enhancement
     * Convenient for debugging and viewing contents
     */
    @Override
    public String toString() {
        return "MaxArrayDeque61B: " + toList().toString();
    }
}