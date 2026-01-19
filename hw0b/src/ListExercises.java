import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        int sum = 0;
        for(int i = 0; i < L.size(); i++) {
            sum = sum + L.get(i);
            }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> evens = new java.util.ArrayList<>();
        for (Integer num : L) {
            if (num % 2 == 0) {
                evens.add(num);
            }
        }
        return evens;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> result = new java.util.ArrayList<>();

        for (Integer num : L1) {
            // 如果L2包含这个数字且结果列表还没有包含（去重）
            if (L2.contains(num) && !result.contains(num)) {
                result.add(num);
            }
        }

        return result;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int count = 0;

        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == c) {
                    count++;
                }
            }
        }

        return count;
    }
}
