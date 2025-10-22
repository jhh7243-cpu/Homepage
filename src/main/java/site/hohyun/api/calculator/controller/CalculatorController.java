package site.hohyun.api.calculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import site.hohyun.api.calculator.domain.CalculationDTO;
import site.hohyun.api.calculator.service.CalculatorService;
import jakarta.servlet.http.HttpSession;

/**
 * 계산기 관련 컨트롤러
 * 계산기 기능 및 계산 처리
 */
@Controller
@RequestMapping("/calculator")
public class CalculatorController 
{
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) 
    {
        this.calculatorService = calculatorService;
    }

    /**
     * 계산기 페이지 표시
     * 
     * @return 계산기 페이지 템플릿
     */
    @GetMapping
    public String calculatorPage(HttpSession session, Model model) 
    {
        // 세션에서 계산기 상태 가져오기
        String currentInput = (String) session.getAttribute("currentInput");
        String expression = (String) session.getAttribute("expression");
        
        if (currentInput == null) {
            currentInput = "0";
            session.setAttribute("currentInput", currentInput);
        }
        if (expression == null) {
            expression = "0";
            session.setAttribute("expression", expression);
        }
        
        model.addAttribute("currentInput", currentInput);
        model.addAttribute("expression", expression);
        
        return "calculator/calculator";
    }

    /**
     * 계산 처리 (기본 계산기)
     * 
     * @param num1     첫 번째 숫자
     * @param num2     두 번째 숫자
     * @param operator 연산자 (+, -, *, /)
     * @param model    모델 객체
     * @return 계산기 페이지 템플릿
     */
    @PostMapping("/calculate-basic")
    public String calculateBasic(@RequestParam("num1") Double num1,
            @RequestParam("num2") Double num2,
            @RequestParam("operator") String operator,
            Model model) 
    {
        System.out.println("계산기 컨트롤러로 들어옴");
        System.out.println("화면에서 컨트롤러로 전달된 첫 번째 숫자 = " + num1);
        System.out.println("화면에서 컨트롤러로 전달된 두 번째 숫자 = " + num2);
        System.out.println("연산자 = " + operator);

        try 
        {
            CalculationDTO calculationDTO = new CalculationDTO();
            calculationDTO.setNum1(num1);
            calculationDTO.setNum2(num2);
            calculationDTO.setOperator(operator);

            Double result = calculatorService.calculate(calculationDTO);

            model.addAttribute("result", result);
            model.addAttribute("num1", num1);
            model.addAttribute("num2", num2);
            model.addAttribute("operator", operator);
            model.addAttribute("success", true);
        } 
        catch (IllegalArgumentException e) 
        {
            System.out.println("계산 오류: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("num1", num1);
            model.addAttribute("num2", num2);
            model.addAttribute("operator", operator);
            model.addAttribute("success", false);
        } 
        catch (Exception e) 
        {
            System.out.println("예상치 못한 오류: " + e.getMessage());
            model.addAttribute("error", "계산 중 오류가 발생했습니다.");
            model.addAttribute("num1", num1);
            model.addAttribute("num2", num2);
            model.addAttribute("operator", operator);
            model.addAttribute("success", false);
        }

        return "calculator/calculator";
    }

    /**
     * 숫자 입력 처리
     */
    @PostMapping("/number")
    public String inputNumber(@RequestParam("number") String number,
                             @RequestParam("currentInput") String currentInput,
                             @RequestParam("expression") String expression,
                             HttpSession session, Model model) 
    {
        System.out.println("숫자 입력: " + number);
        
        String newInput;
        if (currentInput.equals("0")) {
            newInput = number;
        } else {
            newInput = currentInput + number;
        }
        
        session.setAttribute("currentInput", newInput);
        session.setAttribute("expression", expression);
        
        model.addAttribute("currentInput", newInput);
        model.addAttribute("expression", expression);
        
        return "calculator/calculator";
    }

    /**
     * 소수점 입력 처리
     */
    @PostMapping("/decimal")
    public String inputDecimal(@RequestParam("currentInput") String currentInput,
                              @RequestParam("expression") String expression,
                              HttpSession session, Model model) 
    {
        System.out.println("소수점 입력");
        
        String newInput = currentInput;
        if (!currentInput.contains(".")) {
            newInput = currentInput + ".";
        }
        
        session.setAttribute("currentInput", newInput);
        session.setAttribute("expression", expression);
        
        model.addAttribute("currentInput", newInput);
        model.addAttribute("expression", expression);
        
        return "calculator/calculator";
    }

    /**
     * 연산자 입력 처리
     */
    @PostMapping("/operator")
    public String inputOperator(@RequestParam("operator") String operator,
                               @RequestParam("currentInput") String currentInput,
                               @RequestParam("expression") String expression,
                               HttpSession session, Model model) 
    {
        System.out.println("연산자 입력: " + operator);
        
        // 세션에서 계산 상태 가져오기
        String firstNumber = (String) session.getAttribute("firstNumber");
        String currentOperator = (String) session.getAttribute("currentOperator");
        
        if (firstNumber == null) {
            // 첫 번째 숫자 저장
            firstNumber = currentInput;
            session.setAttribute("firstNumber", firstNumber);
            session.setAttribute("currentOperator", operator);
            session.setAttribute("currentInput", "0");
            
            String newExpression = firstNumber + " " + operator;
            session.setAttribute("expression", newExpression);
            
            model.addAttribute("currentInput", "0");
            model.addAttribute("expression", newExpression);
        } else {
            // 이미 연산자가 있으면 계산 후 새 연산자 설정
            if (currentOperator != null) {
                try {
                    Double result = performCalculation(Double.parseDouble(firstNumber), 
                                                      Double.parseDouble(currentInput), 
                                                      currentOperator);
                    
                    String newExpression = result + " " + operator;
                    session.setAttribute("firstNumber", result.toString());
                    session.setAttribute("currentOperator", operator);
                    session.setAttribute("currentInput", "0");
                    session.setAttribute("expression", newExpression);
                    
                    model.addAttribute("currentInput", "0");
                    model.addAttribute("expression", newExpression);
                } catch (Exception e) {
                    model.addAttribute("error", "계산 오류가 발생했습니다.");
                    model.addAttribute("currentInput", currentInput);
                    model.addAttribute("expression", expression);
                }
            }
        }
        
        return "calculator/calculator";
    }

    /**
     * 계산 실행 (고급 계산기)
     */
    @PostMapping("/calculate-advanced")
    public String calculateAdvanced(@RequestParam("currentInput") String currentInput,
                           @RequestParam("expression") String expression,
                           HttpSession session, Model model) 
    {
        System.out.println("계산 실행");
        
        String firstNumber = (String) session.getAttribute("firstNumber");
        String currentOperator = (String) session.getAttribute("currentOperator");
        
        if (firstNumber != null && currentOperator != null) {
            try {
                Double result = performCalculation(Double.parseDouble(firstNumber), 
                                                  Double.parseDouble(currentInput), 
                                                  currentOperator);
                
                // 결과 표시
                model.addAttribute("result", result);
                model.addAttribute("num1", Double.parseDouble(firstNumber));
                model.addAttribute("num2", Double.parseDouble(currentInput));
                model.addAttribute("operator", currentOperator);
                model.addAttribute("success", true);
                
                // 세션 초기화
                session.removeAttribute("firstNumber");
                session.removeAttribute("currentOperator");
                session.setAttribute("currentInput", result.toString());
                session.setAttribute("expression", "0");
                
                model.addAttribute("currentInput", result.toString());
                model.addAttribute("expression", "0");
                
            } catch (Exception e) {
                System.err.println("계산 오류: " + e.getMessage());
                model.addAttribute("error", "계산 중 오류가 발생했습니다: " + e.getMessage());
                model.addAttribute("currentInput", currentInput);
                model.addAttribute("expression", expression);
            }
        } else {
            model.addAttribute("currentInput", currentInput);
            model.addAttribute("expression", expression);
        }
        
        return "calculator/calculator";
    }

    /**
     * 전체 클리어
     */
    @PostMapping("/clear")
    public String clearAll(@RequestParam("currentInput") String currentInput,
                          @RequestParam("expression") String expression,
                          HttpSession session, Model model) 
    {
        System.out.println("전체 클리어");
        
        // 세션 초기화
        session.removeAttribute("firstNumber");
        session.removeAttribute("currentOperator");
        session.setAttribute("currentInput", "0");
        session.setAttribute("expression", "0");
        
        model.addAttribute("currentInput", "0");
        model.addAttribute("expression", "0");
        
        return "calculator/calculator";
    }

    /**
     * 백스페이스
     */
    @PostMapping("/backspace")
    public String backspace(@RequestParam("currentInput") String currentInput,
                           @RequestParam("expression") String expression,
                           HttpSession session, Model model) 
    {
        System.out.println("백스페이스");
        
        String newInput;
        if (currentInput.length() > 1) {
            newInput = currentInput.substring(0, currentInput.length() - 1);
        } else {
            newInput = "0";
        }
        
        session.setAttribute("currentInput", newInput);
        session.setAttribute("expression", expression);
        
        model.addAttribute("currentInput", newInput);
        model.addAttribute("expression", expression);
        
        return "calculator/calculator";
    }

    /**
     * 계산 수행 헬퍼 메서드
     */
    private Double performCalculation(Double firstValue, Double secondValue, String operation) 
    {
        switch (operation) {
            case "+":
                return firstValue + secondValue;
            case "-":
                return firstValue - secondValue;
            case "*":
                return firstValue * secondValue;
            case "/":
                if (secondValue == 0) {
                    throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
                }
                return firstValue / secondValue;
            case "%":
                if (secondValue == 0) {
                    throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
                }
                return firstValue % secondValue;
            default:
                throw new IllegalArgumentException("지원하지 않는 연산자입니다: " + operation);
        }
    }
}