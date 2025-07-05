package function.cashier;

import storage.CashStorage;

public class Cashier {

    private CashStorage storage;

    //현재 구조로는 확장을 하게 되면 코드가 바뀌어야 된다 -> 유연성 X
    public Cashier(CashStorage storage) {
        this.storage = storage;
    }

    //카드의 경우에는 잔돈이 필요가 없다
    public int calcCharge(String inputCash, int totalPrice) {
        return storage.calcCharge(inputCash, totalPrice);
    }

    public void checkCashAmount() {
        storage.checkCashAmount();
    }

    public void addCash(int amount) {
        storage.addCash(amount);
    }
}