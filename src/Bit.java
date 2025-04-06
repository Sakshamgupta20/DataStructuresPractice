import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Bit {
    public int subsetXORSum(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        subsetXORSumBackTrack(new ArrayList<>(),0,subsets,nums);

        int result = 0;
        for (List<Integer> subset : subsets) {
            int subsetXORTotal = 0;
            for (int num : subset) {
                subsetXORTotal ^= num;
            }
            result += subsetXORTotal;
        }
        return result;
    }

    private void subsetXORSumBackTrack(List<Integer> cur, int index, List<List<Integer>>  subsets, int[] nums) {
        if(index > nums.length)
            return;
        subsets.add(new ArrayList<>(cur));
        for (int i = index; i < nums.length; i++) {
            cur.add(nums[i]);
            subsetXORSumBackTrack(cur,i+1,subsets,nums);
            cur.removeLast();
        }
    }
}
