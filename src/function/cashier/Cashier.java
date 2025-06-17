package function.cashier;

import storage.CashStorage;

public class Cashier {

    private CashStorage storage;

    public Cashier(CashStorage storage) {
        this.storage = storage;
    }

    public int calculateCharge(int input) {
        return storage.calculateCharge(input);
    }

    public void checkCashAmount() {
        storage.checkCashAmount();
    }

    public void addCash(int amount) {
        storage.addCash(amount);
    }
}