import java.util.ArrayList;
import java.util.List;

public class Sorting {
    public void selectionSort(List<Integer> numbers) {
        for (int i = 0; i < numbers.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers.get(minIndex) > numbers.get(j))
                    minIndex = j;
            }
            swap(numbers, i, minIndex);
        }
    }

    public void iterativeBubbleSort(List<Integer> numbers) {
        for (int i = numbers.size() - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (numbers.get(j) > numbers.get(j + 1)) {
                    swap(numbers, j, j + 1);
                }
            }
        }
    }

    public void recursiveBubbleSort(List<Integer> numbers) {
        recursiveBubbleSort(numbers, numbers.size());
    }

    private void recursiveBubbleSort(List<Integer> numbers, int high) {
        if (high == 1)
            return;
        int didSwap = 0;
        for (int i = 0; i < high - 1; i++) {
            if (numbers.get(i) > numbers.get(i + 1)) {
                swap(numbers, i, i + 1);
                didSwap = 1;
            }
        }

        if (didSwap == 0)
            return;

        recursiveBubbleSort(numbers, high - 1);

    }

    public void recursiveInsertionSort(List<Integer> numbers) {
        recursiveInsertionSort(numbers, 0);
    }

    private void recursiveInsertionSort(List<Integer> numbers, int i) {
        if (i >= numbers.size())
            return;
        for (int j = i; j > 0; j--) {
            if (numbers.get(j) < numbers.get(j - 1))
                swap(numbers, j, j - 1);
            else break;
        }
        recursiveInsertionSort(numbers, i + 1);
    }

    public void insertionSort(List<Integer> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (numbers.get(j) < numbers.get(j - 1))
                    swap(numbers, j - 1, j);
                else break;
            }
        }
    }

    public void mergeSort(List<Integer> numbers) {
        mergeSort(numbers, 0, numbers.size() - 1);
    }

    private void mergeSort(List<Integer> numbers, int low, int high) {
        if (low >= high)
            return;
        int mid = (high + low) / 2;
        mergeSort(numbers, low, mid);
        mergeSort(numbers, mid + 1, high);
        merge(numbers, low, mid, high);
    }

    private void merge(List<Integer> numbers, int low, int mid, int high) {
        List<Integer> merged = new ArrayList<>();

        int left = low;
        int right = mid + 1;
        while (left <= mid && right <= high) {
            if (numbers.get(left) < numbers.get(right)) {
                merged.add(numbers.get(left++));
            } else {
                merged.add(numbers.get(right++));
            }
        }

        //If part1 has items remaining then append them to merge array
        while (left <= mid) {
            merged.add(numbers.get(left++));
        }
        //If part2 has items remaining then append them to merge array
        while (right <= high) {
            merged.add(numbers.get(right++));
        }

        for (int i = low; i <= high; i++) {
            numbers.set(i, merged.get(i - low));
        }
    }

    public void quickSort(List<Integer> numbers) {
        quickSort(numbers, 0, numbers.size() - 1);
    }

    private void quickSort(List<Integer> numbers, int low, int high) {
        if (low < high) {
            int pivot = quickSortPartition(numbers, low, high);
            quickSort(numbers, low, pivot - 1);
            quickSort(numbers, pivot + 1, high);
        }
    }

    private int quickSortPartition(List<Integer> numbers, int low, int pivot) {
        if (low > pivot)
            return pivot;
        if (numbers.get(low) > numbers.get(pivot)) {
            swap(numbers, pivot, pivot - 1);
            if (pivot - 1 != low)
                swap(numbers, low, pivot);
            return quickSortPartition(numbers, low, pivot - 1);
        } else
            return quickSortPartition(numbers, low + 1, pivot);
    }

    private void swap(List<Integer> numbers, int i, int j) {
        int temp = numbers.get(i);
        numbers.set(i, numbers.get(j));
        numbers.set(j, temp);
    }
}
