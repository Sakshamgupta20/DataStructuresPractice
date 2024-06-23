package common;

public class CommonUtils {

    public static int[][] stringTo2DArray(String str) {
        // Remove the outer brackets
        str = str.substring(1, str.length() - 1);

        // Split the string into individual array strings
        String[] rows = str.split("\\],\\[");

        // Determine the number of rows and columns
        int numRows = rows.length;
        int numCols = rows[0].split(",").length;

        // Initialize the 2D array
        int[][] result = new int[numRows][numCols];

        // Fill the 2D array
        for (int i = 0; i < numRows; i++) {
            // Remove any remaining brackets
            rows[i] = rows[i].replace("[", "").replace("]", "");
            // Split the row string into individual numbers
            String[] elements = rows[i].split(",");
            for (int j = 0; j < numCols; j++) {
                result[i][j] = Integer.parseInt(elements[j]);
            }
        }
        return result;
    }

    public static char[][] stringTo2DChar(String str) {
        // Remove the outer brackets
        str = str.substring(1, str.length() - 1);

        // Split the string into individual array strings
        String[] rows = str.split("\\],\\[");

        // Determine the number of rows and columns
        int numRows = rows.length;
        int numCols = rows[0].split(",").length;

        // Initialize the 2D array
        char[][] result = new char[numRows][numCols];

        // Fill the 2D array
        for (int i = 0; i < numRows; i++) {
            // Remove any remaining brackets
            rows[i] = rows[i].replace("[", "").replace("]", "");
            // Split the row string into individual numbers
            String[] elements = rows[i].split(",");
            for (int j = 0; j < numCols; j++) {
                result[i][j] = elements[j].charAt(0);
            }
        }
        return result;
    }

    public static String convertToHash(int row, int col) {
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
