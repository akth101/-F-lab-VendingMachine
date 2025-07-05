package payment;

public class MobilePayment implements Payment {
    public final String type;


    public MobilePayment() {
        this.type = "모바일";
    }

    @Override
    public boolean canPay(int productPrice) {
        return true;
    }

    @Override
    public String getPaymentType() {
        return this.type;
    }

    public boolean processPayment(int productPrice) {
        return true;
    }
}