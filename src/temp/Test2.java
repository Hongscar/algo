package temp;
import java.util.*;

public class Test2 {
    public List<Integer> getRow(int numRows) {
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> firstRow = new ArrayList<>();
        firstRow.add(1);
        lists.add(firstRow);
        if (numRows == 0)
            return lists.get(0);
        List<Integer> secondRow = new ArrayList<>();
        secondRow.add(1);
        secondRow.add(1);
        lists.add(secondRow);
        if (numRows == 1)
            return lists.get(1);
        btGenerate(lists, numRows, 2, secondRow);
        return lists.get(numRows);
    }

    public void btGenerate(List<List<Integer>> result, int numRows, int current, List<Integer> prevRow) {
        if (current > numRows)
            return;
        List<Integer> row = new ArrayList<>(current);
        row.add(1);
        for (int i = 1; i < current; i++)
            row.add(prevRow.get(i - 1) + prevRow.get(i));
        row.add(1);
        result.add(new ArrayList<>(row));
        btGenerate(result, numRows, current + 1, row);
    }

    // public boolean isMatch(String s, String p) {
    //     for ()
    // }

    public static void main(String[] args) {
        Test2 test2 = new Test2();
        System.out.println(test2.getRow(4));
    }
}