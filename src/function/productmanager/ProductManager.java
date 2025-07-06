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

    public int calcTotalPrice(String productName, int productCnt) {
        if (storage.checkProductAmount(productName) == 0) {
            System.out.println("Warning: There is no product in the machine.");
            return 0;
        }
        return storage.getProductPrice(productName) * productCnt;
    }

    public void dispenseProducts(int inputCash, String productName, int productCnt, int totalPrice) {
        if (inputCash < totalPrice) { // cashStorage에서 경고문 찍어주므로 여기서는 그냥 종료만 -> cashier & productmanager 의사소통 안됐을 때 어떻게?
            return;
        }
        if (storage.checkProductAmount(productName) >= productCnt) {
            for (int i = 0; i < productCnt; i++) {
                ejectProduct(productName);
            }
        }
    }
}