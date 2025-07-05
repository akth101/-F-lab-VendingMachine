package vendingmachine;

import java.util.Scanner;
import storage.ProductStorage;
import function.cashier.Cashier;
import function.productmanager.ProductManager;
import model.product.Product;
import payment.CreditCardPayment;
import payment.MobilePayment;
import payment.Payment;

import java.util.List;
import java.util.ArrayList;

public class VendingMachine {

    final ProductManager productmanager;
    final Cashier cashier;
    final ProductStorage productstorage;

    VendingMachine() {
        productstorage = new ProductStorage();
        productmanager = new ProductManager(productstorage);
        cashier = new Cashier();
    }

    public String[] parseInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new String[0];
        }
        
        return input.trim().split("\\s+"); // 공백으로 분할(연속된 공백도 처리)
    }

    public void manageMachine(Scanner scanner) {
        System.out.println("[Switched to manage Mode]");
        while (true) {
            System.out.println("usage:");
            System.out.println("product [name] [price] [brand] [amount] [slotNumber] [expirationDate]");
            System.out.println("cash [amount]");
            String input = scanner.nextLine();

            if (input.trim().equals("quit")) {
                System.out.println("[Switched to user mode]");
                break;
            }
            String[] parsed = parseInput(input);
            if (parsed.length == 0) {
                System.out.println("Warning: You did not enter any command.");
                continue;
            }
            //상품 추가 시
            if (parsed[0].equals("product") && parsed.length >= 6 && parsed.length <= 7) {
                Product product = new Product(
                    parsed[1], // name
                    Integer.parseInt(parsed[2]), // price  
                    parsed[3], // brand
                    parsed.length == 6 ? parsed[5] : null // expirationDate
                );
                productmanager.supplyProduct(Integer.parseInt(parsed[5]), product, Integer.parseInt(parsed[4])); //product & amount
            }
            //잔돈 추가 시
            if (parsed[0].equals("cash") && parsed.length == 2) {
                cashier.addCash(Integer.parseInt(parsed[1]));
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VendingMachine machine = new VendingMachine();
        
        machine.cashier.addPaymentMethod(new CreditCardPayment());
        machine.cashier.addPaymentMethod(new MobilePayment());
        
        while (true) {
            machine.productmanager.printInventory();
            System.out.println("usage: [inputCash or paymentMethod] [productName] [productCnt]");
            System.out.print("input: ");
            String input = scanner.nextLine();

            if (input.trim().equals("exit"))
                break;
            try {
                    String[] parsed = machine.parseInput(input);
                    if (parsed.length != 3) {
                        if (parsed.length == 1 && parsed[0].equals("manage")) {
                            machine.manageMachine(scanner);
                        } else {
                            System.out.println("Error: wrong parameters.");
                        }
                        continue;
                    }
                    
                    //각각의 함수에서 파싱을 하고 있다. -> 파싱을 책임지는 메서드 하나 추가되면 개선될 수 있을 것 같다.

                    int totalPrice = machine.productmanager.calcTotalPrice(parsed[1], parsed[2]);
                    machine.productmanager.dispenseProducts(parsed[0], parsed[1], parsed[2], totalPrice);
                    int charge = machine.cashier.calcCharge(parsed[0], totalPrice);
                    if (charge != -1)
                        System.out.println("here is your charge: " + charge);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
        scanner.close();
    }
}