package vendingmachine;

import java.util.Scanner;
import storage.ProductStorage;
import function.cashier.Cashier;
import function.productmanager.ProductManager;
import model.command.Command;
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
    final Command command;

    VendingMachine() {
        productstorage = new ProductStorage();
        productmanager = new ProductManager(productstorage);
        cashier = new Cashier();
        command = new Command();
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
            String[] parsed = command.parseInput(input);
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
                //명령어 파싱
                machine.command.initCommand(input);
                
                // 자판기 정상 동작 흐름
                int totalPrice = machine.productmanager.calcTotalPrice(
                    machine.command.productName,
                    machine.command.productCnt
                );
                machine.productmanager.dispenseProducts(
                    machine.command.inputCash,
                    machine.command.productName,
                    machine.command.productCnt,
                    totalPrice
                );
                String paymentInput = machine.command.paymentMethod != null ? machine.command.paymentMethod : String.valueOf(machine.command.inputCash);
                int charge = machine.cashier.calcCharge(paymentInput, totalPrice);
                if (charge != -1)
                    System.out.println("here is your charge: " + charge);
            } catch (Command.ManageModeException e) { //자판기 매니저 모드 명령이 들어왔을 경우
                machine.manageMachine(scanner);
            } catch (Command.WrongParametersException e) { //잘못된 갯수의 파라미터가 들어왔을 경우
                System.out.println(e.getMessage());
            } catch (Exception e) { // 그 외 혹시모를 모든 예외의 경우
                System.out.println("Exception: " + e.getMessage());
            }
        }
        scanner.close();
    }
}