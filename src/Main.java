import Graphs.ImplicitGraphProblems;
import Trees.TreeNode;
import common.CommonUtils;
import linkedList.ListNode;

import java.util.Arrays;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        //Integers
        int[] arrInt1 = {1,5,4,2,9,9,9};
        int[] arrInt2 = {1,2,5,7,9};
        int[] arrInt3 = {1,3,4,5,8};
        List<List<Integer>> arr2DIntList = List.of(List.of(0, 3), List.of(1, 2), List.of(0, 2));
        int[][] arrInt2D1 = CommonUtils.stringTo2DArray("[[4,1,2],[5,0,3]]");
        int[][] arrInt2D2 = CommonUtils.stringTo2DArray("[[0,6],[0,3],[0,5]]");

        //String and characters
        String[] strings = new String[]{"wrt","wrf","er","ett","rftt"};
        char[] chars = new char[]{'a', 'b'};
        List<List<String>> arr2DStringList = List.of(List.of("JFK", "AAA"), List.of("JFK", "BBB"), List.of("BBB", "CCC"), List.of("CCC", "JFK"));
        char[][] arrChar2D = CommonUtils.stringTo2DChar("[['#','.','*','.'],['#','#','*','.']]");

        //Tree Nodes
        TreeNode node1 = new TreeNode(Arrays.asList(144, 62, 197, 7, 132, 179, 200, null, 12, 66, 133, null, 192, null, null, null, 42, 63, 102, null, 141, 191, null, 34, 47, null, null, 94, 122, null, null, null, null, 33, null, null, 54, 72, null, 104, 128, 28, null, null, null, null, 87, null, null, null, 129));
        TreeNode node2 = new TreeNode(Arrays.asList(9, 6, -3, null, null, -6, 2, null, null, 2, null, -6, -6, -6));

        //Linked List Nodes
        ListNode listNode = new ListNode(arrInt1);


        //Problems
        ImplicitGraphProblems problems = new ImplicitGraphProblems();

        System.out.println(problems.slidingPuzzle(arrInt2D1));


    }
}