import BinarySearch.BinarySearchProblems;
import Graphs.BfsGraphProblems;
import Graphs.DfsGraphProblems;
import Graphs.DijkstrasAlgorithm.DijkstrasAlgorithm;
import Graphs.ImplicitGraphProblems;
import Heap.HeapProblems;
import Monotonic.MonotonicProblems;
import QueueProblems.NumberContainers;
import StackProblems.StackProblems;
import StringProblems.StringProblems;
import Trees.FindElements;
import Trees.TreeNode;
import Trees.TreeProblems;
import Trie.TrieProblems;
import common.CommonUtils;
import linkedList.ListNode;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        //Integers
        int[] arrInt1 = {2,-5,1,-4,3,-2};
        int[] arrInt2 = {3,5,4,2,6,8,7,1};
        int[] arrInt3 = {1,3,4,5,8};
        List<List<Integer>> arr2DIntList = List.of(List.of(0, 3), List.of(1, 2), List.of(0, 2));
        int[][] arrInt2D1 = CommonUtils.stringTo2DArray("[[0,2],[0,5],[1,3],[1,5],[2,4]]");
        int[][] arrInt2D2 = CommonUtils.stringTo2DArray("[[0,6],[0,3],[0,5]]");

        //String and characters
        String[] strings1 = new String[]{"0"};
        String[] strings2 = new String[]{"s","s","ss"};
        char[] chars = new char[]{'a', 'b'};
        List<List<String>> arr2DStringList = List.of(List.of("JFK", "AAA"), List.of("JFK", "BBB"), List.of("BBB", "CCC"), List.of("CCC", "JFK"));
        char[][] arrChar2D = CommonUtils.stringTo2DChar("[['#','.','*','.'],['#','#','*','.']]");

        //Tree Nodes
        TreeNode node1 = new TreeNode(Arrays.asList(-1,-1,-1,null,null,-1,-1,null,null,null,-1));
        TreeNode node2 = new TreeNode(Arrays.asList(9, 6, -3, null, null, -6, 2, null, null, 2, null, -6, -6, -6));

        //Linked List Nodes
        ListNode listNode = new ListNode(arrInt1);


        //Problems
        PrefixSum problems = new PrefixSum();
        System.out.println(problems.maxAbsoluteSum(arrInt1));;



    }
}