package storage;

public class CashStorage {
    private int amount;

    public CashStorage(int amount) {
        if (amount >= 0) {
            this.amount = amount;
        } else {
            System.out.println("Error: cash cannot be negative. try positive.");
            this.amount = 0;
        }
    }

    public int calculateCharge(int productPrice) {
        if (amount >= productPrice) {
            amount -= productPrice;
            return 0; // 정확히 지불
        } else {
            return productPrice - amount; // 부족한 금액 반환
        }
    }

    public void addCash(int cash) {
        if (cash > 0) {
            this.amount += cash;
        }
    }

    public int getCashAmount() {
        return this.amount;
    }

    public void checkCashAmount() {
        System.out.println("cashAmount: " + amount);
    }
}