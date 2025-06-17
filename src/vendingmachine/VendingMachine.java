package vendingmachine;

import java.util.Scanner;
import storage.ProductStorage;
import storage.CashStorage;

public class VendingMachine {

    VendingMachine() {}
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VendingMachine machine = new VendingMachine();
        
        while (true) {
            System.out.print("돈 입력: ");
            String input = scanner.nextLine();
            
            if (input.equals("exit")) {
                break;
            }
            
            try {
                int money = Integer.parseInt(input);
                System.out.println(result);
            } catch (NumberFormatException e) {
            }
        }
        
        scanner.close();
    }
}