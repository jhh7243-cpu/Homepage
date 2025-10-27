package site.hohyun.api.calculator.repository;

import org.springframework.stereotype.Repository;
import site.hohyun.api.common.domain.Messenger;
import java.util.Stack;

@Repository
public class CalculatorRepository 
{
    /**
     * 메신저 생성
     */
    public Messenger message(String message) 
    {
        return new Messenger(200, message);
    }
    
    /**
     * 계산 결과 메신저 생성
     */
    public Messenger calculateResult(String expression) 
    {
        try {
            System.out.println("🧮 CalculatorRepository: 계산 시작 - " + expression);
            
            if (expression == null || expression.trim().isEmpty()) {
                return new Messenger(200, "0");
            }
            
            // 수식 정리
            expression = expression.trim().replaceAll("\\s+", " ");
            
            // 간단한 계산 (우선순위 없는 경우)
            if (expression.matches("^\\d+(\\.\\d+)?\\s*[+\\-*/%]\\s*\\d+(\\.\\d+)?$")) {
                return calculateSimple(expression);
            }
            
            // 복잡한 계산 (우선순위 고려)
            return calculateAdvanced(expression);
            
        } catch (Exception e) {
            System.err.println("❌ CalculatorRepository: 계산 오류 - " + e.getMessage());
            return new Messenger(500, "계산 오류: " + e.getMessage());
        }
    }
    
    /**
     * 간단한 계산 (두 숫자 연산)
     */
    private Messenger calculateSimple(String expression) {
        try {
            String[] parts = expression.split("\\s*[+\\-*/%]\\s*");
            String[] operators = expression.split("\\d+(\\.\\d+)?");
            
            if (parts.length != 2 || operators.length < 2) {
                return new Messenger(400, "계산 오류: 잘못된 수식입니다.");
            }
            
            double num1 = Double.parseDouble(parts[0]);
            double num2 = Double.parseDouble(parts[1]);
            String operator = operators[1].trim();
            
            double result = performOperation(num1, num2, operator);
            return new Messenger(200, formatResult(result));
            
        } catch (Exception e) {
            return new Messenger(400, "계산 오류: " + e.getMessage());
        }
    }
    
    /**
     * 복잡한 계산 (우선순위 고려)
     */
    private Messenger calculateAdvanced(String expression) {
        try {
            // 중위 표기법을 후위 표기법으로 변환 후 계산
            String postfix = infixToPostfix(expression);
            double result = evaluatePostfix(postfix);
            return new Messenger(200, formatResult(result));
        } catch (Exception e) {
            return new Messenger(400, "계산 오류: " + e.getMessage());
        }
    }
    
    /**
     * 중위 표기법을 후위 표기법으로 변환
     */
    private String infixToPostfix(String expression) {
        Stack<Character> operatorStack = new Stack<>();
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            
            if (Character.isDigit(c) || c == '.') {
                // 숫자 처리
                StringBuilder number = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    number.append(expression.charAt(i));
                    i++;
                }
                i--; // 마지막 증가를 되돌림
                result.append(number.toString()).append(" ");
            } else if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    result.append(operatorStack.pop()).append(" ");
                }
                if (!operatorStack.isEmpty()) {
                    operatorStack.pop(); // '(' 제거
                }
            } else if (isOperator(c)) {
                while (!operatorStack.isEmpty() && getPrecedence(operatorStack.peek()) >= getPrecedence(c)) {
                    result.append(operatorStack.pop()).append(" ");
                }
                operatorStack.push(c);
            }
        }
        
        while (!operatorStack.isEmpty()) {
            result.append(operatorStack.pop()).append(" ");
        }
        
        return result.toString().trim();
    }
    
    /**
     * 후위 표기법 계산
     */
    private double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");
        
        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("잘못된 수식입니다.");
                }
                double b = stack.pop();
                double a = stack.pop();
                double result = performOperation(a, b, token);
                stack.push(result);
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("잘못된 수식입니다.");
        }
        
        return stack.pop();
    }
    
    /**
     * 연산 실행
     */
    private double performOperation(double a, double b, String operator) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("0으로 나눌 수 없습니다.");
                }
                return a / b;
            case "%":
                if (b == 0) {
                    throw new ArithmeticException("0으로 나눌 수 없습니다.");
                }
                return a % b;
            default:
                throw new IllegalArgumentException("지원하지 않는 연산자입니다: " + operator);
        }
    }
    
    /**
     * 연산자 우선순위 반환
     */
    private int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            default:
                return 0;
        }
    }
    
    /**
     * 연산자 확인
     */
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }
    
    /**
     * 숫자 확인
     */
    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * 결과 포맷팅
     */
    private String formatResult(double result) {
        if (result == (long) result) {
            return String.valueOf((long) result);
        } else {
            return String.format("%.6f", result).replaceAll("0+$", "").replaceAll("\\.$", "");
        }
    }
}