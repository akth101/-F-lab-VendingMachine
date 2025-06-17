
package model.product;

final class Tissue extends Product {

    final String brand;
    
    Tissue(String name, int price, String brand) {
        super(name, price);
        this.brand = (brand != null) ? brand : "Unknown";
    }

    String getBrand() {
        return this.brand;
    }

}