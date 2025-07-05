
package payment;

public interface Payment {
    boolean canPay(int productPrice);
    String getPaymentType();
}