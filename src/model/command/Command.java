package model.command;

public class Command {
    public String paymentMethod;
    public int inputCash;
    public String productName;
    public int productCnt;
    
    // 커스텀 예외 클래스들
    public static class ManageModeException extends Exception {
        public ManageModeException(String message) {
            super(message);
        }
    }
    
    public static class WrongParametersException extends Exception {
        public WrongParametersException(String message) {
            super(message);
        }
    }
    
    public Command() {}
    
    public String[] parseInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new String[0];
        }
        
        return input.trim().split("\\s+"); // 공백으로 분할(연속된 공백도 처리)
    }
    
    // String을 받아서 파싱하고 초기화
    public void initCommand(String input) throws ManageModeException, WrongParametersException, IllegalArgumentException {
        String[] parsed = parseInput(input);
        
        if (parsed.length != 3) {
            if (parsed.length == 1 && parsed[0].equals("manage")) {
                throw new ManageModeException("Switching to manage mode");
            } else {
                throw new WrongParametersException("Error: wrong parameters.");
            }
        }
        
        try {
            try {
                this.inputCash = Integer.parseInt(parsed[0]); //parsed[0]가 숫자가 아니면 catch문으로 점프
                this.productName = parsed[1];
                this.productCnt = Integer.parseInt(parsed[2]);
            } catch (NumberFormatException e) {
                this.paymentMethod = parsed[0]; // parsed[0]가 숫자가 아니면 String인 paymentMethod로 취급
                this.productName = parsed[1];
                this.productCnt = Integer.parseInt(parsed[2]);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자 형식이 올바르지 않습니다.");
        }
    }
    
    // 유효성 검사 메서드
    public void validate() throws IllegalArgumentException {
        if (inputCash < 0) {
            throw new IllegalArgumentException("입력 금액은 0 이상이어야 합니다.");
        }
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("상품명은 비어있을 수 없습니다.");
        }
        if (productCnt <= 0) {
            throw new IllegalArgumentException("상품 개수는 1 이상이어야 합니다.");
        }
    }
}
