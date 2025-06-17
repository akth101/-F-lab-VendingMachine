
package model.product;

final class Mask extends Product {

    final String brand;
    
    Mask(String name, int price, String brand) {
        super(name, price);
        this.brand = (brand != null) ? brand : "Unknown";
    }

    String getBrand() {
        return this.brand;
    }

}