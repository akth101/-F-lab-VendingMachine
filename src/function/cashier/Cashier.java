package function.cashier;
import java.util.Map;
import java.util.HashMap;

import payment.Payment;

public class Cashier {
    Map<String, Payment> paymentMethods;

    public Cashier() {}

    public void addPaymentMethod(Payment payment) {
        paymentMethods.put(payment.getPaymentType(), payment);
    }

    public Payment getPaymentMethod(String type) {
        return paymentMethods.get(type);
    }


}