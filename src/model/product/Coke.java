package model.product;

public class Coke extends Product {

    final String brand;
    
    public Coke(String name, String expirationDate, int price, String brand) {
        super(name, expirationDate, price);
        this.brand = (brand != null) ? brand : "Unknown";
    }

    public String getBrand() {
        return this.brand;
    }

}