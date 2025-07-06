package payment;

public class CreditCardPayment implements Payment {
    public final String type;


    public CreditCardPayment() {
        this.type = "creditCard";
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