

import Graphs.BfsGraphProblems;
import Graphs.DfsGraphProblems;
import Trees.TreeNode;
import common.CommonUtils;

import java.util.Arrays;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //1,0,0,0,1,0,1,0,0,1,0,1,0,1,0,1,0,1
        List<List<Integer>> arr1 = Arrays.asList(List.of(0, 1), List.of(1, 2), List.of(2, 0));
        int[] arr2 = {1, 2, 3, 4, 5};

        BfsGraphProblems problems = new BfsGraphProblems();
        int[][] arr = CommonUtils.stringTo2DArray("[[-1,-1,30,14,15,-1],[23,9,-1,-1,-1,9],[12,5,7,24,-1,30],[10,-1,-1,-1,25,17],[32,-1,28,-1,-1,32],[-1,-1,23,-1,13,19]]");

        TreeNode node = new TreeNode(Arrays.asList(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4));
        System.out.println(problems.snakesAndLadders(arr));
    }
}