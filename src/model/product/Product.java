package model.product;

public class Product {
    final String name;
    final String expirationDate;
    int price;
    final String brand;
    
    //유통기한이 없는 물품
    public Product(String name, int price, String brand) {
        this.name = (name != null) ? name : "Unknown";
        this.price = (price > 0) ? price : 0;
        this.brand = (brand != null) ? brand : "Unknown";
        this.expirationDate = "None";
    }

    //유통기한이 있는 물품
    public Product(String name, int price, String brand, String expirationDate) {
        this.name = (name != null) ? name : "Unknown";
        this.price = (price != 0) ? price : 0;
        this.brand = (brand != null) ? brand : "Unknown";
        this.expirationDate = (expirationDate != null) ? expirationDate : "None";
    }

    public String getName() {
        return this.name;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public int getPrice() {
        return this.price;
    }

    public String getBrand() {
        return this.brand;
    }
}