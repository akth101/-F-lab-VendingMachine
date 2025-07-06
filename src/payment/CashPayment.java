package payment;

public class CashPayment implements Payment {
    private int amount;
    private final int MAX_AMOUNT;
    public final String type;


    public CashPayment() {
        this.amount = 100000;
        this.MAX_AMOUNT = 3000000;
        this.type = "cash";
    }

    @Override
    public boolean canPay(int productPrice) {
        if (this.amount < productPrice) {
            return true;
        }
        return false;
    }

    @Override
    public String getPaymentType() {
        return this.type;
    }

    public void calcCharge(int inputCash, int productPrice) {
        if (inputCash < productPrice) { //주문한 상품 가격보다 투입 금액이 적을 때
            System.out.println("Warning: Not enough input cash.");
            return;
        }
        else if (this.amount < inputCash - productPrice) { //거슬러줄 잔액이 부족할 때
            System.out.println("Warning: Not enough cash in the machine.");
            return;
        }
        this.amount -= (inputCash - productPrice);
        System.out.println("here is your charge: " + (inputCash - productPrice));
    }

    public void addCash(int cash) {
        if (cash < 0) { //cash는 음수값이 될 수 없음
            System.out.println("Error: cash cannot be negative.");
            return;
        } else if (this.amount + cash > MAX_AMOUNT) { // 자판기에는 최대 300만원까지 저장 가능
            System.out.println("Error: Max cash amount is 300,0000.");
            return;
        }
        this.amount += cash;
        System.out.println("added " + cash + " into cashStorage.");
        checkCashAmount();
    }

    public int getCashAmount() {
        return this.amount;
    }

    public void checkCashAmount() {
        System.out.println("cashAmount: " + amount);
    }
}