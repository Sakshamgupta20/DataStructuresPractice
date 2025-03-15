package QueueProblems;

import java.util.ArrayList;
import java.util.List;

class MRUQueue {
    List<Integer> nums = new ArrayList<>();
    public MRUQueue(int n) {
        for (int i = 1; i <= n; i++) {
            nums.add(i);
        }
    }

    public int fetch(int k) {
        int num = nums.get(k - 1);
        nums.remove(k - 1);
        nums.add(num);
        return num;
    }
}
