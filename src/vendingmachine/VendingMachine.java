package vendingmachine;

import java.util.Scanner;
import storage.ProductStorage;
import function.cashier.Cashier;
import function.productmanager.ProductManager;
import model.command.*;
import payment.*;
import function.cashier.Cashier.InsufficientCashException;

public class VendingMachine {

    final ProductManager productmanager;
    final Cashier cashier;
    final ProductStorage productstorage;
    final Purchase purchase;
    final Manage manage;

    VendingMachine() {
        productstorage = new ProductStorage();
        productmanager = new ProductManager(productstorage);
        cashier = new Cashier();
        purchase = new Purchase();
        manage = new Manage();
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
            try {
                manage.initCommand(input);
                if (manage.commandType.equals("manage")) { //상품 추가 시
                    productmanager.supplyProduct(manage);
                }
                else if (manage.commandType.equals("cash")) { //잔돈 추가 시
                    CashPayment cashPayment = (CashPayment)cashier.getPaymentMethod("cash");
                    cashPayment.addCash(manage.cashAmount);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VendingMachine machine = new VendingMachine();
        
        //결제수단 추가 시 여기에 1줄만 추가하면 됨
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
                machine.purchase.initCommand(input);
                
                // 자판기 정상 동작 흐름
                int totalPrice = machine.productmanager.calcTotalPrice(
                    machine.purchase.productName,
                    machine.purchase.productCnt
                );
                machine.cashier.processPayment(
                    machine.purchase,
                    totalPrice
                );
                machine.productmanager.dispenseProducts(
                    machine.purchase.productName,
                    machine.purchase.productCnt
                );
            } catch (Purchase.ManageModeException e) { //자판기 매니저 모드 명령이 들어왔을 경우
                machine.manageMachine(scanner);
            } catch (Purchase.WrongParametersException e) { //잘못된 갯수의 파라미터가 들어왔을 경우
                System.out.println(e.getMessage());
            } catch (InsufficientCashException e) { //현금이 부족한 경우
                System.out.println(e.getMessage());
            } catch (Exception e) { // 그 외 혹시모를 모든 예외의 경우
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}