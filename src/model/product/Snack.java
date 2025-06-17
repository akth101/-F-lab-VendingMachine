package model.product;

final class Snack extends Product {

    final String brand;
    
    Snack(String name, String expirationDate, int price, String brand) {
        super(name, expirationDate, price);
        this.brand = (brand != null) ? brand : "Unknown";
    }

    String getBrand() {
        return this.brand;
    }

}