package function.productmanager;

import storage.ProductStorage;
import model.product.Product;
import java.util.Map;
import java.util.HashMap;

public class ProductManager {
    private ProductStorage storage;
    
    public ProductManager(ProductStorage storage) {
        this.storage = storage;
    }

    public void ejectProduct(String productName) {
        storage.ejectProduct(productName);
    }

    public void supplyProduct(int slotNumber, Product product, int amount) {
        storage.supplyProduct(slotNumber, product, amount);
    }

    public void checkProduct(String productName) {
        storage.checkProductAmount(productName);
    }

    public void printInventory() {
        storage.printInventory();
    }

    public int calcTotalPrice(String productName, String cnt) {
        int count = Integer.parseInt(cnt);
        if (storage.checkProductAmount(productName) == 0) {
            System.out.println("Warning: There is no product in the machine.");
            return 0;
        }
        return storage.getProductPrice(productName) * count;
    }

    public void dispenseProducts(String inputCash, String productName, String cnt, int totalPrice) {
        int input = Integer.parseInt(inputCash);
        int count = Integer.parseInt(cnt);
        if (input < totalPrice) { // cashStorage에서 경고문 찍어주므로 여기서는 그냥 종료만
            return;
        }
        if (storage.checkProductAmount(productName) >= count) {
            for (int i = 0; i < count; i++) {
                ejectProduct(productName);
            }
        }
    }
}