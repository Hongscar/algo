package algo;
import java.util.*;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 9:02 2019/11/30
 */

// TODO, 可行但是效率不高,会超时. 应该再维护一个数组/栈,来记录最左可以insert的位置和最右可以delete的位置
class DinnerPlates {
    private List<LinkedList<Integer>> plates;
    private List<Integer> sizes;
    private int size;           // the capacity of each plate

    public DinnerPlates(int capacity) {
        plates = new ArrayList<>();
        sizes = new ArrayList<>();
        size = capacity;
    }

    public void push(int val) {
        int index = -1;
        int p_size = plates.size();
        for (int i = 0; i < p_size; i++)
            if (plates.get(i).size() != size) {
                index = i;
                break;
            }
        if (index == -1 || plates.get(index).size() == size) {
            LinkedList<Integer> new_plate = new LinkedList<>();
            new_plate.add(val);
            sizes.add(1);
            plates.add(new_plate);
            return;
        }
        LinkedList<Integer> current = plates.get(index);
        current.addFirst(val);
        plates.set(index, new LinkedList<>(current));
        int prev_size = sizes.get(index);
        sizes.set(index, prev_size + 1);
    }

    public int pop() {
        int index = -1;
        int p_size = plates.size();
        for (int i = p_size - 1; i >= 0; i--)
            if (plates.get(i).size() != 0) {
                index = i;
                break;
            }
        if (index == -1)
            return -1;
        LinkedList<Integer> current_plate = plates.get(index);
        int res = current_plate.removeFirst();
        int prev_size = sizes.get(index);
        sizes.set(index, prev_size - 1);
        plates.set(index, new LinkedList<>(current_plate));
        return res;
    }

    public int popAtStack(int index) {
        int p_size = plates.size();
        if (index < 0 || index >= p_size)
            return -1;
        LinkedList<Integer> plate = plates.get(index);
        if (plate.size() == 0)
            return -1;
        int res = plate.removeFirst();
        int prev_size = sizes.get(index);
        sizes.set(index, prev_size - 1);
        plates.set(index, new LinkedList<>(plate));
        return res;
    }
}
