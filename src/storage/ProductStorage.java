package storage;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.HashMap;
import model.product.Product;

public class ProductStorage {
    private Map<String, Deque<Product>> productMap = new HashMap<>();

    public void ejectProduct(String productName) {
        //productName에 해당하는 제품을 Deque에서 제거
        Deque<Product> deque = productMap.get(productName);
        if (deque != null && !deque.isEmpty()) {
            deque.removeFirst();
        }
    }
    
    public void supplyProduct(String productName, Product product) {
        // 해당 제품명의 Deque를 가져오거나, 없으면 새로 생성
        Deque<Product> productQueue = productMap.get(productName);
        if (productQueue == null) {
            productQueue = new ArrayDeque<>();
            productMap.put(productName, productQueue);
        }
        
        // 제품을 큐의 맨 뒤에 추가
        productQueue.addLast(product);
    }


    public void checkProductAmount(String productName) {
        Deque<Product> productQueue = productMap.get(productName);
        if (productQueue != null) {
            System.out.println(productName + " 제품 개수: " + productQueue.size());
        } else {
            System.out.println(productName + " 제품 개수: 0");
        }
    }

}