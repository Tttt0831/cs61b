import java.util.ArrayList;
import java.util.List;

public class JavaExercises {

    /** Returns an array [1, 2, 3, 4, 5, 6] */
    public static int[] makeDice() {
        // TODO: Fill in this function.
        int [] target = {1, 2, 3, 4, 5, 6};
        return target;
    }

    /** Returns the order depending on the customer.
     *  If the customer is Ergun, return ["beyti", "pizza", "hamburger", "tea"].
     *  If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     *  In any other case, return an empty String[] of size 3. */
    public static String[] takeOrder(String customer) {
        // TODO: Fill in this function.
        if (customer == "Ergun"){
            String[] food = {"beyti", "pizza", "hamburger", "tea"};
            return food;
        } else if (customer == "Erik") {
            String[] food ={"sushi", "pasta", "avocado", "coffee"};
            return food;
        }
        else {
            String[] food = new String[3];
            return food;
        }
    }

    /** Returns the positive difference between the maximum element and minimum element of the given array.
     *  Assumes array is nonempty. */
    public static int findMinMax(int[] array) {
        // TODO: Fill in this function.
        if (array == null || array.length == 0) {
            // Handle empty/null array appropriately
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        int min = array[0];
        int max = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
            if (array[i] > max) {
                max = array[i];
            }
        }

        return max - min;
    }

    /**
      * Uses recursion to compute the hailstone sequence as a list of integers starting from an input number n.
      * Hailstone sequence is described as:
      *    - Pick a positive integer n as the start
      *        - If n is even, divide n by 2
      *        - If n is odd, multiply n by 3 and add 1
      *    - Continue this process until n is 1
      */
    public static List<Integer> hailstone(int n) {
        return hailstoneHelper(n, new ArrayList<>());
    }

    private static List<Integer> hailstoneHelper(int x, List<Integer> list) {
        // TODO: Fill in this function.
        // 将当前数字加入列表
        list.add(x);

        // 基准情况：如果x为1，序列结束
        if (x == 1) {
            return list;
        }

        // 递归情况：根据奇偶性计算下一个数字
        if (x % 2 == 0) {
            // 如果是偶数，除以2
            return hailstoneHelper(x / 2, list);
        } else {
            // 如果是奇数，乘以3加1
            return hailstoneHelper(x * 3 + 1, list);
        }
    }

}
