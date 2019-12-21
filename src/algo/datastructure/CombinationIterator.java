package algo.datastructure;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 9:26 2019/12/16
 */

public class CombinationIterator {
    private String characters;
    private int charactersLength;
    private int combinationLength;
    private List<String> result;
    private int current;
    private int size;

    public CombinationIterator(String characters, int combinationLength) {
        this.characters = characters;
        this.charactersLength = characters.length();
        this.combinationLength = combinationLength;
        result = new ArrayList<>();
        current = 0;
        initHelper(new StringBuilder(), 0, -1);
        size = result.size();
    }

    public void initHelper(StringBuilder sb, int index, int prev) {
        if (sb.length() == combinationLength) {
            result.add(sb.toString());
            return;
        }
        for (int i = index; i < charactersLength; i++) {
            if (i <= prev)
                continue;
            sb.append(characters.charAt(i));
            initHelper(sb, index + 1, i);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public String next() {
        String res = result.get(current);
        current++;
        return res;
    }

    public boolean hasNext() {
        return current != size;
    }

    public static void main(String[] args) {
        CombinationIterator iterator = new CombinationIterator("abc", 2); // 创建迭代器 iterator

        System.out.println(iterator.next()); // 返回 "ab"
        System.out.println(iterator.hasNext()); // 返回 true
        System.out.println(iterator.next()); // 返回 "ac"
        System.out.println(iterator.hasNext()); // 返回 true
        System.out.println(iterator.next()); // 返回 "bc"
        System.out.println(iterator.hasNext()); // 返回 false


    }
}

/**
 * Your CombinationIterator object will be instantiated and called as such:
 * CombinationIterator obj = new CombinationIterator(characters, combinationLength);
 * String param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */
