package function.cashier;
import java.util.Map;
import java.util.HashMap;
import model.command.*;
import payment.Payment;
import payment.CashPayment;

public class Cashier {
    Map<String, Payment> paymentMethods;

    // 현금 부족 예외 클래스
    public static class InsufficientCashException extends Exception {
        public InsufficientCashException(String message) {
            super(message);
        }
    }

    public Cashier() {
        this.paymentMethods = new HashMap<>();
        CashPayment cashPayment = new CashPayment();
        this.paymentMethods.put(cashPayment.getPaymentType(), cashPayment);
    }

    public void processPayment(Purchase command, int totalPrice) throws InsufficientCashException {
        if (command.inputCash == -1) { // 현금이 아닌 결제수단 사용
            Payment paymentMethod = getPaymentMethod(command.paymentMethod);
            if (paymentMethod != null) { // 신용카드나 모바일 결제는 항상 성공한다고 가정
                System.out.println(paymentMethod.getPaymentType() + " payment completed");
            } else {
                System.out.println("Error: Unsupported payment method");
            }
        } else if (command.inputCash > 0) {
            if (command.inputCash < totalPrice) {
                throw new InsufficientCashException("Insufficient cash");
            }
            // 현금 결제
            CashPayment cashPayment = (CashPayment)getPaymentMethod("cash");
            if (cashPayment != null) {
                cashPayment.calcCharge(command.inputCash, totalPrice);
            }
        } else {
            System.out.println("Error: Invalid payment information");
        }
    }

    public void addPaymentMethod(Payment payment) {
        paymentMethods.put(payment.getPaymentType(), payment);
    }

    public Payment getPaymentMethod(String type) {
        return paymentMethods.get(type);
    }


}