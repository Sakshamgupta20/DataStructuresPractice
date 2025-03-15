import java.util.ArrayList;
import java.util.List;

class ProductOfNumbers {
    List<Integer> products = new ArrayList<>();

    public ProductOfNumbers() {
        products.add(1);
    }

    public void add(int num) {
        if (num == 0) {
            products.clear();
            products.add(1);  // Reset prefix product list, maintaining a dummy value
        } else {
            products.add(products.getLast() * num);
        }
    }

    public int getProduct(int k) {
        if (k > products.size()) return 0; // If zero was encountered, return 0
        return products.get(products.size() - 1) / products.get(products.size() - 1 - k);
    }
}