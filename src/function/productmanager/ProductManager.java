package function.productmanager;

import storage.ProductStorage;
import model.product.Product;
import model.command.*;
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

    public void supplyProduct(Manage manage) {
        Product product = new Product(
            manage.productName,
            manage.productPrice,
            manage.productBrand,
            manage.expirationDate
        );
        storage.supplyProduct(manage.slotNumber, product, manage.productAmount);
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

    public void dispenseProducts(String productName, int productCnt) {
        if (storage.checkProductAmount(productName) >= productCnt) {
            for (int i = 0; i < productCnt; i++) {
                ejectProduct(productName);
            }
        }
    }
}