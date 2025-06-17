package function.productmanager;

import storage.ProductStorage;
import model.product.Product;

public class ProductManager {
    private ProductStorage storage;
    
    public ProductManager(ProductStorage storage) {
        this.storage = storage;
    }

    public void ejectProduct(String productName) {
        storage.ejectProduct(productName);
    }

    public void supplyProduct(String productName, Product product) {
        storage.supplyProduct(productName, product);
    }

    public void checkProduct(String productName) {
        storage.checkProductAmount(productName);
    }
}