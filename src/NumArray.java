public class NumArray {

    int[] arr;
    int[] nums;
    public NumArray(int[] nums) {
        this.arr = new int[nums.length];
        this.nums = nums;
        this.arr[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            this.arr[i] = arr[i - 1] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return arr[right] - arr[left] + nums[left];
    }
}
