package common;

import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

    public static int[][] stringTo2DArray(String str) {
        // Remove the outer brackets
        str = str.substring(1, str.length() - 1);

        // Split the string into individual array strings
        String[] rows = str.split("\\],\\[");

        // Initialize a list to store the rows dynamically
        List<int[]> resultList = new ArrayList<>();

        // Fill the resultList
        for (String row : rows) {
            // Remove any remaining brackets
            row = row.replace("[", "").replace("]", "");

            // Handle empty rows
            if (row.isEmpty()) {
                resultList.add(new int[0]);
            } else {
                // Split the row string into individual numbers
                String[] elements = row.split(",");
                int[] rowArray = new int[elements.length];
                for (int j = 0; j < elements.length; j++) {
                    rowArray[j] = Integer.parseInt(elements[j]);
                }
                resultList.add(rowArray);
            }
        }

        // Convert the list to a 2D array
        return resultList.toArray(new int[0][]);
    }


    public static char[][] stringTo2DChar(String str) {
        // Remove the outermost square brackets and any spaces
        str = str.trim().substring(1, str.length() - 1);

        // Split the string by "],[" to separate the rows
        String[] rows = str.split("\\],\\[");

        // Determine the number of rows and columns
        int numRows = rows.length;
        int numCols = rows[0].split(",").length;

        // Initialize the 2D char array
        char[][] result = new char[numRows][numCols];

        // Fill the 2D array
        for (int i = 0; i < numRows; i++) {
            // Remove any remaining brackets or single quotes in each row and split by commas
            String[] elements = rows[i].replaceAll("[\\[\\]']", "").split(",");
            for (int j = 0; j < numCols; j++) {
                result[i][j] = elements[j].charAt(0);  // Get the first character of each string
            }
        }
        return result;
    }


    public static String convertToHash(int row, int col) {
        return row + "," + col;
    }

    public static String convertToHash(String row, String col) {
        return row + "," + col;
    }

    public static boolean validGrid(int n, int m, int nextRow, int nextCol) {
        return (nextRow >= 0 && n > nextRow && nextCol >= 0 && m > nextCol);
    }

    public static int[][] getVerticalHorizontalDirections() {
        return new int[][]{{1, 0}, {0, 1}, {0, -1}, {-1, 0}};
    }

    public static int[][] getDiagonalDirections() {
        return new int[][]{{1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
    }

    public static int[][] getAllDirections() {
        return new int[][]{{1, 0}, {0, 1}, {0, -1}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
    }
}
