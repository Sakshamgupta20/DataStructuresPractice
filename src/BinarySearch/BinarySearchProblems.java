package BinarySearch;

import Trees.TreeNode;

import java.util.*;

public class BinarySearchProblems {
    public int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return mid;
            }
            if (target > arr[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public int binarySearchLeftMost(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        int result = -1;  // To store the index of the leftmost target

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                result = mid;  // Store the current mid as a potential answer
                right = mid - 1;  // Continue searching to the left
            } else if (arr[mid] > target) {
                right = mid - 1;  // Move right boundary
            } else {
                left = mid + 1;  // Move left boundary
            }
        }

        return result;  // Return the leftmost index or -1 if not found
    }

    public int binarySearchRightMost(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        int result = -1;  // Store the index of the rightmost occurrence

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                result = mid;  // Update result to mid, but continue searching to the right
                left = mid + 1;  // Move left to mid + 1 to search for further occurrences
            } else if (arr[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return result;  // Return the index of the rightmost occurrence, or -1 if not found
    }

    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target == nums[mid])
                return mid;
            if (target > nums[mid])
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }

    /**
     * 74. Search a 2D Matrix
     * You are given an m x n integer matrix matrix with the following two properties:
     * <p>
     * Each row is sorted in non-decreasing order.
     * The first integer of each row is greater than the last integer of the previous row.
     * Given an integer target, return true if target is in matrix or false otherwise.
     * <p>
     * You must write a solution in O(log(m * n)) time complexity.
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int rowCount = matrix.length;
        if (rowCount == 0) return false;
        int columnCount = matrix[0].length;
        int left = 0;
        int right = rowCount * columnCount - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int column = mid % columnCount;
            int row = mid / columnCount;
            if (target == matrix[row][column])
                return true;
            if (target > matrix[row][column])
                left = mid + 1;
            else
                right = mid - 1;
        }
        return false;
    }

    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target)
                right = mid - 1;
            else
                left = mid + 1;
        }
        return left;
    }

    public int[] answerQueries(int[] nums, int[] queries) {
        int n = nums.length;
        int[] pathSum = new int[n];
        int[] ans = new int[queries.length];
        Arrays.sort(nums);

        int index = 0;
        pathSum[index++] = nums[0];
        for (int i = 1; i < n; i++) {
            pathSum[index] += pathSum[index - 1] + nums[i];
            index++;
        }
        for (int i = 0; i < queries.length; i++) {
            ans[i] = searchInsert(pathSum, queries[i]);
        }

        return ans;
    }

    public int smallestDivisor(int[] nums, int threshold) {
        int left = 1;
        int right = 0;
        for (int num : nums) {
            right = Math.max(right, num);
        }

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int sum = 0;
            for (int num : nums) {
                sum += Math.ceilDiv(num, mid);
            }
            if (sum <= threshold) {
                right = mid - 1;
            } else
                left = mid + 1;
        }
        return left;
    }

    public int maximizeSweetness(int[] sweetness, int k) {
        int numberOfPeople = k + 1;
        int left = Arrays.stream(sweetness).min().getAsInt();
        int right = Arrays.stream(sweetness).sum() / numberOfPeople;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int count = 0;
            int sum = 0;
            for (int j : sweetness) {
                sum += j;
                if (sum >= mid) {
                    sum = 0;
                    count++;
                }
            }

            if (count >= k) {
                left = mid + 1;
            } else
                right = mid - 1;

        }
        return right;
    }

    public int splitArray(int[] nums, int k) {
        int left = Arrays.stream(nums).max().getAsInt();
        int right = Arrays.stream(nums).sum();

        int minimumLargestSplitSum = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (check(nums, mid) > k) {
                left = mid + 1;
            } else {
                right = mid - 1;
                minimumLargestSplitSum = mid;
            }
        }
        return minimumLargestSplitSum;
    }

    public int check(int[] nums, int target) {
        int currentSum = 0;
        int splitsRequired = 0;

        for (int element : nums) {
            if (currentSum + element <= target) {
                currentSum += element;
            } else {
                currentSum = element;
                splitsRequired++;
            }
        }

        return splitsRequired + 1;
    }

    public int maxDistance(int[] nums1, int[] nums2) {
        int max = 0;

        for (int i = 0; i < nums1.length; i++) {
            int left = i, right = nums2.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;

                if (nums1[i] <= nums2[mid]) {
                    max = Math.max(max, mid - i);
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return max;
    }

    public List<List<Integer>> closestNodes(TreeNode root, List<Integer> queries) {
        List<Integer> sorted = new ArrayList<>();
        sorted(root, sorted);
        List<List<Integer>> res = new ArrayList<>();
        for (Integer query : queries) {
            int left = 0;
            int right = sorted.size() - 1;

            boolean added = false;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (Objects.equals(sorted.get(mid), query)) {
                    res.add(List.of(query, query));
                    added = true;
                    break;
                } else if (sorted.get(mid) > query) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            if (!added) {
                List<Integer> r = new ArrayList<>();
                r.add(left - 1 == -1 ? -1 : (sorted.get(left - 1)));
                r.add(left == sorted.size() ? -1 : (sorted.get(left)));
                res.add(r);
            }
        }
        return res;
    }

    public void sorted(TreeNode node, List<Integer> sorted) {
        if (node == null)
            return;

        sorted(node.left, sorted);
        sorted.add(node.val);
        sorted(node.right, sorted);
    }

    public int getIndex(ArrayReader reader) {
        int left = 0;
        int right = reader.length() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison;
            if ((right - left) % 2 == 0) {
                comparison = reader.compareSub(left, mid, mid, right);
            } else {
                comparison = reader.compareSub(left, mid, mid + 1, right);
            }
            if (comparison == 1) {
                right = mid;
            } else if (comparison == -1)
                left = mid + 1;
            else
                return mid;
        }
        return left;
    }

    public int singleNonDuplicate(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            boolean halvesAreEven = (right - mid) % 2 == 0;
            if (nums[mid - 1] == nums[mid]) {
                if (halvesAreEven)
                    right = mid - 2;
                else
                    left = mid + 1;
            } else if (nums[mid + 1] == nums[mid]) {
                if (halvesAreEven)
                    left = mid + 2;
                else
                    right = mid - 1;
            } else return mid;
        }
        return nums[left];
    }

    public int searchInRotatedArray(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int leftValue = nums[left];
            int rightValue = nums[right];
            int midValue = nums[mid];
            if (target == midValue)
                return mid;
            if (target == leftValue)
                return left;
            if (target == rightValue)
                return right;

            if (leftValue < midValue) {
                if (target < leftValue) {
                    left = mid + 1;
                } else {
                    if (target < midValue) {
                        right = mid - 1;
                    } else
                        left = mid + 1;
                }
            } else {
                if (target > midValue && target <= rightValue) {
                    left = mid + 1;
                } else
                    right = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 57. Insert Interval
     * You are given an array of non-overlapping intervals intervals where intervals[i] = [starti, endi] represent the start and the end of the ith interval and intervals is sorted in ascending order by starti. You are also given an interval newInterval = [start, end] that represents the start and end of another interval.
     * <p>
     * Insert newInterval into intervals such that intervals is still sorted in ascending order by starti and intervals still does not have any overlapping intervals (merge overlapping intervals if necessary).
     * <p>
     * Return intervals after the insertion.
     * <p>
     * Note that you don't need to modify intervals in-place. You can make a new array and return it.
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int left = 0;
        int right = intervals.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (intervals[mid][0] < newInterval[0]) {
                left = mid + 1;
            } else
                right = mid - 1;
        }

        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < left; i++) {
            result.add(intervals[i]);
        }
        result.add(newInterval);
        for (int i = left; i < intervals.length; i++) {
            result.add(intervals[i]);
        }

        // Merge overlapping intervals
        List<int[]> merged = new ArrayList<>();
        for (int[] interval : result) {
            if (
                    merged.isEmpty() ||
                            merged.get(merged.size() - 1)[1] < interval[0]
            ) {
                merged.add(interval);
            } else {
                merged.get(merged.size() - 1)[1] = Math.max(
                        merged.get(merged.size() - 1)[1],
                        interval[1]
                );
            }
        }

        return merged.toArray(new int[0][]);

    }

    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> merged = new ArrayList<>();
        for (int[] interval : intervals) {
            if (
                    merged.isEmpty() ||
                            merged.get(merged.size() - 1)[1] < interval[0]
            ) {
                merged.add(interval);
            } else {
                merged.get(merged.size() - 1)[1] = Math.max(
                        merged.get(merged.size() - 1)[1],
                        interval[1]
                );
            }
        }

        return merged.toArray(new int[0][]);
    }

    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));
        int ans = 0;
        int k = Integer.MIN_VALUE;

        for (int[] interval : intervals) {
            int x = interval[0];
            int y = interval[1];

            if (x >= k) {
                // Case 1
                k = y;
            } else {
                // Case 2
                ans++;
            }
        }

        return ans;
    }

    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));
        int ans = 0;
        int k = Integer.MIN_VALUE;

        for (int[] interval : intervals) {
            int x = interval[0];
            int y = interval[1];

            if (x >= k) {
                // Case 1
                k = y;
            } else {
                // Case 2
                return false;
            }
        }

        return true;
    }

    public int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int max = 0;

        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> a - b);
        for (int[] interval : intervals) {
            int x = interval[0];
            int y = interval[1];
            if (!queue.isEmpty() && x >= queue.peek()) {
                queue.poll();
            }
            queue.add(y);
            max = Math.max(queue.size(), max);
        }
        return max;
    }

    public int[] minInterval(int[][] intervals, int[] queries) {
        HashMap<Integer, Integer> map = new HashMap<>();
        Arrays.sort(intervals, Comparator.comparingInt(x -> x[0]));

        int[] arr = new int[queries.length];
        int i = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(x -> (x[1] - x[0])));

        System.arraycopy(queries, 0, arr, 0, arr.length);
        Arrays.sort(arr);
        for (int q : arr) {
            while (i < intervals.length && intervals[i][0] <= q) {
                pq.offer(intervals[i++]);
            }
            while (!pq.isEmpty() && pq.peek()[1] < q) {
                pq.poll();
            }
            map.put(q, (pq.isEmpty()) ? -1 : pq.peek()[1] - pq.peek()[0] + 1);
        }

        for (i = 0; i < queries.length; i++) {
            queries[i] = map.get(queries[i]);
        }
        return queries;
    }

    /**
     * 2563. Count the Number of Fair Pairs
     * <p>
     * Given a 0-indexed integer array nums of size n and two integers lower and upper, return the number of fair pairs.
     * <p>
     * A pair (i, j) is fair if:
     * <p>
     * 0 <= i < j < n, and
     * lower <= nums[i] + nums[j] <= upper
     */
    public long countFairPairs(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        long ans = 0;

        for (int i = 0; i < nums.length; i++) {
            long l = countFairPairs(nums, i + 1, nums.length - 1, lower - nums[i]);
            long r = countFairPairs(nums, i + 1, nums.length - 1, upper - nums[i] + 1);

            ans += r - l;
        }
        return ans;
    }

    long countFairPairs(int[] nums, int left, int right, int element) {

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= element) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 2064. Minimized Maximum of Products Distributed to Any Store
     * You are given an integer n indicating there are n specialty retail stores. There are m product types of varying amounts, which are given as a 0-indexed integer array quantities, where quantities[i] represents the number of products of the ith product type.
     * <p>
     * You need to distribute all products to the retail stores following these rules:
     * <p>
     * A store can only be given at most one product type but can be given any amount of it.
     * After distribution, each store will have been given some number of products (possibly 0). Let x represent the maximum number of products given to any store. You want x to be as small as possible, i.e., you want to minimize the maximum number of products that are given to any store.
     * Return the minimum possible x.
     */
    public int minimizedMaximum(int n, int[] quantities) {
        int right = Integer.MIN_VALUE;
        for (int quantity : quantities) {
            right = Math.max(quantity, right);
        }

        int left = 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (minimizedMaximumPossible(quantities, mid, n)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    private boolean minimizedMaximumPossible(int[] quantities, int count, int n) {
        int countDone = 0;
        for (int quantity : quantities) {

            int totalPossible = quantity / count;
            countDone += totalPossible;

            int curr = quantity % count;
            if (curr > 0)
                countDone++;
        }

        return countDone > n;
    }

    /**
     * 2601. Prime Subtraction Operation
     * You are given a 0-indexed integer array nums of length n.
     * <p>
     * You can perform the following operation as many times as you want:
     * <p>
     * Pick an index i that you haven’t picked before, and pick a prime p strictly less than nums[i], then subtract p from nums[i].
     * Return true if you can make nums a strictly increasing array using the above operation and false otherwise.
     * <p>
     * A strictly increasing array is an array whose each element is strictly greater than its preceding element.
     */
    public boolean primeSubOperation(int[] nums) {
        return false;
    }

    public boolean[] isArraySpecial(int[] nums, int[][] queries) {
        boolean[] ans = new boolean[queries.length];
        ArrayList<Integer> violatingIndices = new ArrayList<>();
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] % 2 == nums[i - 1] % 2) {
                violatingIndices.add(i);
            }
        }

        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            int start = query[0];
            int end = query[1];

            boolean foundViolatingIndex = binarySearchIsArraySpecial(start + 1, end, violatingIndices
            );

            ans[i] = !foundViolatingIndex;
        }

        return ans;
    }

    private boolean binarySearchIsArraySpecial(int start, int end, ArrayList<Integer> violatingIndices) {
        int left = 0;
        int right = violatingIndices.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int violatingIndex = violatingIndices.get(mid);

            if (violatingIndex < start) {
                left = mid + 1;
            } else if (violatingIndex > end) {
                right = mid - 1;
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * 1891. Cutting Ribbons
     * You are given an integer array ribbons, where ribbons[i] represents the length of the ith ribbon, and an integer k. You may cut any of the ribbons into any number of segments of positive integer lengths, or perform no cuts at all.
     * <p>
     * For example, if you have a ribbon of length 4, you can:
     * Keep the ribbon of length 4,
     * Cut it into one ribbon of length 3 and one ribbon of length 1,
     * Cut it into two ribbons of length 2,
     * Cut it into one ribbon of length 2 and two ribbons of length 1, or
     * Cut it into four ribbons of length 1.
     * Your task is to determine the maximum length of ribbon, x, that allows you to cut at least k ribbons, each of length x. You can discard any leftover ribbon from the cuts. If it is impossible to cut k ribbons of the same length, return 0.
     */
    public int maxLength(int[] ribbons, int k) {
        int left = 1;
        int right = 0;
        for (int ribbon : ribbons)
            right = Math.max(right, ribbon);

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if(maxLengthPossible(mid,ribbons,k)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left - 1;

    }

    public boolean maxLengthPossible(int length, int[] ribbons, int k) {
        int total = 0;
        for (int ribbon : ribbons) {
            total += ribbon / length;
        }
        return total >= k;
    }

}
