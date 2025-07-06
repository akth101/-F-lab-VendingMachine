package model.command;

public class Manage {
    public String commandType;
    public String productName;
    public int productPrice;
    public String productBrand;
    public int productAmount;
    public int slotNumber;
    public String expirationDate;
    public int cashAmount;
    
    public static class InvalidCommandException extends Exception {
        public InvalidCommandException(String message) {
            super(message);
        }
    }
    
    public static class WrongParametersException extends Exception {
        public WrongParametersException(String message) {
            super(message);
        }
    }
    
    public Manage() {}
    
    public String[] parseInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new String[0];
        }
        return input.trim().split("\\s+");
    }
    
    public void initCommand(String input) throws InvalidCommandException, WrongParametersException, IllegalArgumentException {
        String[] parsed = parseInput(input);
        
        if (parsed.length == 0) {
            throw new WrongParametersException("Warning: You did not enter any command.");
        }
        
        this.commandType = parsed[0];
        
        if (commandType.equals("product")) {
            if (parsed.length < 6 || parsed.length > 7) {
                throw new WrongParametersException("Error: product command requires 6-7 parameters.");
            }
            
            try {
                this.productName = parsed[1];
                this.productPrice = Integer.parseInt(parsed[2]);
                this.productBrand = parsed[3];
                this.productAmount = Integer.parseInt(parsed[4]);
                this.slotNumber = Integer.parseInt(parsed[5]);
                this.expirationDate = parsed.length == 7 ? parsed[6] : null;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: Invalid number format");
            }
            
        } else if (commandType.equals("cash")) {
            if (parsed.length != 2) {
                throw new WrongParametersException("Error: cash command requires 2 parameters.");
            }
            
            try {
                this.cashAmount = Integer.parseInt(parsed[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: Invalid number format");
            }
            
        } else {
            throw new InvalidCommandException("Error: unknown command '" + commandType + "'");
        }
        
        validate();
    }
    
    public void validate() throws IllegalArgumentException {
        if (commandType.equals("product")) {
            if (productName == null || productName.trim().isEmpty()) {
                throw new IllegalArgumentException("Error: Product name cannot be empty");
            }
            if (productPrice <= 0) {
                throw new IllegalArgumentException("Error: Product price must be greater than 0");
            }
            if (productBrand == null || productBrand.trim().isEmpty()) {
                throw new IllegalArgumentException("Error: Brand name cannot be empty");
            }
            if (productAmount <= 0) {
                throw new IllegalArgumentException("Error: Product amount must be at least 1");
            }
            if (slotNumber < 0) {
                throw new IllegalArgumentException("Error: Slot number must be 0 or greater");
            }
        } else if (commandType.equals("cash")) {
            if (cashAmount <= 0) {
                throw new IllegalArgumentException("Error: Cash amount must be greater than 0");
            }
        }
    }
}
