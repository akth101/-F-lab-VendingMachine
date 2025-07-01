package storage;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.IntStream;
import model.product.Product;

//slotMap의 key로 1 ~ 10 사이의 int값을 할당하여 같은 상품을 여러 slot에 진열할 수 있음
//priceTableMap에 저장된 가격을 getProductPrice 메서드를 통해 O(1) 시간복잡도로 외부에 제공 가능

public class ProductStorage {
    private Map<Integer, Deque<Product>> slotMap = new HashMap<>();
    private Map<String, Integer> priceTableMap;
    private final int MAX_SLOTS = 10;
    private final int MAX_SLOT_CAPACITY = 10;
    
    public ProductStorage() {
        for (int i = 1; i <= MAX_SLOTS; i++) {
            slotMap.put(i, new ArrayDeque<>());
        }
        this.priceTableMap = new HashMap<>();
        initializeProducts();
    }
    
    private void initializeProducts() {
        supplyProduct(1, new Product("코카콜라_제로", 1500, "코카콜라", "2024-12-31"), 5);
        supplyProduct(2, new Product("코카콜라_제로", 1500, "코카콜라", "2024-12-15"), 3);
        supplyProduct(3, new Product("KF94_마스크", 1000, "3M"), 5);
        supplyProduct(4, new Product("포테토칩", 1800, "오리온", "2024-11-30"), 2);
        supplyProduct(5, new Product("포테토칩", 1800, "오리온", "2024-12-20"), 2);
        supplyProduct(6, new Product("미니_휴지", 500, "유한킴벌리"), 5);
    }

    // 특정 슬롯에 상품 공급
    public void supplyProduct(int slotNumber, Product product, int amount) {
        if (slotNumber < 1 || slotNumber > MAX_SLOTS) {
            System.out.println("Error: invalid Slot number.");
            return;
        }
        if (slotMap.get(slotNumber).size() + amount > MAX_SLOT_CAPACITY) {
            System.out.println("Error: Max slot capacity is 10.");
            return;
        }
        if (slotMap.get(slotNumber).size() != 0 && !slotMap.get(slotNumber).peekFirst().getName().equals(product.getName())) {
                System.out.println("Error: A slot can only hold one type of product.");
                return;
            }
        
        Deque<Product> slot = slotMap.get(slotNumber);
        for (int i = 0; i < amount; i++) {
            slot.addLast(
                new Product(
                product.getName(),
                product.getPrice(),
                product.getBrand(),
                product.getExpirationDate()
                )
            );
        }

        //가격표에 상품 추가(이 상품이 자판기에 진열된 적 없던 상품인 경우에 한하여)
        if (!priceTableMap.containsKey(product.getName())) {
            priceTableMap.put(product.getName(), product.getPrice());
        }

        System.out.println("added " + product.getName() + " into " + slotNumber + " slot.");
    }

    // 상품명으로 첫 번째로 찾은 슬롯에서 해당 상품을 배출
    public void ejectProduct(String productName) {
        for (int slotNumber : slotMap.keySet()) {
            Deque<Product> slot = slotMap.get(slotNumber);
            //슬롯이 비어있지 X && 슬롯에 있는 첫 번째 상품의 이름 == 매개변수로 들어온 이름 일 경우 상품 방출
            if (!slot.isEmpty() && slot.peekFirst().getName().equals(productName)) {
                slot.removeFirst();
                System.out.println("ejected " + productName + " from " + slotNumber + " slot.");
                return;
            }
        }
        System.out.println("Warning: can't find " + productName + " in the machine.");
    }
    
    // slotMap 전체에서 해당 상품이 몇 개 있는지 반환
    public int checkProductAmount(String productName) {
        int totalAmount = 0;
        for (Deque<Product> slot : slotMap.values()) {
            for (Product product : slot) {
                if (product.getName().equals(productName)) {
                    totalAmount++;
                }
            }
        }
        return totalAmount;
    }

    public int getProductPrice(String productName) {
        return priceTableMap.get(productName);
    }

    // 슬롯별 재고 현황 출력
    public void printInventory() {
        System.out.println("=== storage Overview ===");
        for (int i = 1; i <= MAX_SLOTS; i++) {
            Deque<Product> slot = slotMap.get(i);
            if (!slot.isEmpty()) {
                Product firstProduct = slot.peekFirst();
                System.out.println("slot " + i + ": " + firstProduct.getName() + 
                                 " (" + slot.size() + ", " + firstProduct.getPrice() + "won)");
            } else {
                System.out.println("slot " + i + ": empty");
            }
        }
        System.out.println("===================");
    }

}