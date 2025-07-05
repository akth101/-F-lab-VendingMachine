package function.cashier;
import java.util.Map;
import java.util.HashMap;

import payment.Payment;
import payment.CashPayment;

public class Cashier {
    Map<String, Payment> paymentMethods;

    public Cashier() {
        this.paymentMethods = new HashMap<>();
        CashPayment cashPayment = new CashPayment();
        this.paymentMethods.put(cashPayment.getPaymentType(), cashPayment);
    }

    public void addPaymentMethod(Payment payment) {
        paymentMethods.put(payment.getPaymentType(), payment);
    }

    public Payment getPaymentMethod(String type) {
        return paymentMethods.get(type);
    }


}