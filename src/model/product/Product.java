package model.product;

public class Product {
    final String name;
    final String expirationDate;
    int price;
    
    //유통기한이 없는 물품(ex. 휴지, 손세정제, 마스크...)
    public Product(String name, int price) {
        this.name = (name != null) ? name : "Unknown";
        this.price = (price > 0) ? price : 0;
        this.expirationDate = "None";
    }

    //유통기한이 있는 물품(ex. 콜라, 이온음료, 과자...)
    public Product(String name, String expirationDate, int price) {
        this.name = (name != null) ? name : "Unknown";
        this.price = (price != 0) ? price : 0;
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

    
}