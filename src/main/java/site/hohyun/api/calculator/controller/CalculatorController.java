
package site.hohyun.api.calculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor;
import site.hohyun.api.calculator.domain.CalculationDTO;
import site.hohyun.api.calculator.service.CalculatorService;
import site.hohyun.api.common.domain.Messenger;

@Controller
@RequiredArgsConstructor
@RequestMapping("/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;

    /**
     * 계산기 메인 페이지
     */
    @GetMapping("")
    public String calculatorHome(Model model) {
        model.addAttribute("currentInput", "0");
        model.addAttribute("expression", "");
        return "calculator/calculator";
    }

    /**
     * 숫자 입력 처리
     */
    @PostMapping("/number")
    public String inputNumber(@RequestParam("number") String number,
                             @RequestParam("currentInput") String currentInput,
                             @RequestParam("expression") String expression,
                             Model model) {
        try {
            String newInput = currentInput.equals("0") ? number : currentInput + number;
            model.addAttribute("currentInput", newInput);
            model.addAttribute("expression", expression);
        } catch (Exception e) {
            System.err.println("❌ CalculatorController: 숫자 입력 오류: " + e.getMessage());
            model.addAttribute("error", "숫자 입력 중 오류가 발생했습니다.");
            model.addAttribute("currentInput", currentInput);
            model.addAttribute("expression", expression);
        }
        return "calculator/calculator";
    }

    /**
     * 연산자 입력 처리
     */
    @PostMapping("/operator")
    public String inputOperator(@RequestParam("operator") String operator,
                               @RequestParam("currentInput") String currentInput,
                               @RequestParam("expression") String expression,
                               Model model) {
        try {
            String newExpression = expression + currentInput + " " + operator + " ";
            model.addAttribute("currentInput", "0");
            model.addAttribute("expression", newExpression);
        } catch (Exception e) {
            System.err.println("❌ CalculatorController: 연산자 입력 오류: " + e.getMessage());
            model.addAttribute("error", "연산자 입력 중 오류가 발생했습니다.");
            model.addAttribute("currentInput", currentInput);
            model.addAttribute("expression", expression);
        }
        return "calculator/calculator";
    }

    /**
     * 소수점 입력 처리
     */
    @PostMapping("/decimal")
    public String inputDecimal(@RequestParam("currentInput") String currentInput,
                              @RequestParam("expression") String expression,
                              Model model) {
        try {
            String newInput = currentInput.contains(".") ? currentInput : currentInput + ".";
            model.addAttribute("currentInput", newInput);
            model.addAttribute("expression", expression);
        } catch (Exception e) {
            System.err.println("❌ CalculatorController: 소수점 입력 오류: " + e.getMessage());
            model.addAttribute("error", "소수점 입력 중 오류가 발생했습니다.");
            model.addAttribute("currentInput", currentInput);
            model.addAttribute("expression", expression);
        }
        return "calculator/calculator";
    }

    /**
     * 계산 실행 (고급 계산)
     */
    @PostMapping("/calculate-advanced")
    public String calculateAdvanced(@RequestParam("currentInput") String currentInput,
                                   @RequestParam("expression") String expression,
                                   Model model) {
        try {
            String fullExpression = expression + currentInput;
            System.out.println("🧮 CalculatorController: 계산 실행 - " + fullExpression);
            
            // 계산 실행
            Messenger result = calculatorService.calculate(fullExpression);
            
            if (result.getCode() != 200 || result.getMessage().contains("오류")) {
                model.addAttribute("error", result.getMessage());
                model.addAttribute("currentInput", "0");
                model.addAttribute("expression", "");
            } else {
                model.addAttribute("currentInput", result.getMessage());
                model.addAttribute("expression", fullExpression + " =");
            }
        } catch (Exception e) {
            System.err.println("❌ CalculatorController: 계산 실행 오류: " + e.getMessage());
            model.addAttribute("error", "계산 중 오류가 발생했습니다: " + e.getMessage());
            model.addAttribute("currentInput", "0");
            model.addAttribute("expression", "");
        }
        return "calculator/calculator";
    }

    /**
     * 클리어 처리
     */
    @PostMapping("/clear")
    public String clear(@RequestParam("currentInput") String currentInput,
                       @RequestParam("expression") String expression,
                       Model model) {
        model.addAttribute("currentInput", "0");
        model.addAttribute("expression", "");
        return "calculator/calculator";
    }

    /**
     * 백스페이스 처리
     */
    @PostMapping("/backspace")
    public String backspace(@RequestParam("currentInput") String currentInput,
                           @RequestParam("expression") String expression,
                           Model model) {
        try {
            String newInput = currentInput.length() > 1 ? currentInput.substring(0, currentInput.length() - 1) : "0";
            model.addAttribute("currentInput", newInput);
            model.addAttribute("expression", expression);
        } catch (Exception e) {
            System.err.println("❌ CalculatorController: 백스페이스 오류: " + e.getMessage());
            model.addAttribute("currentInput", "0");
            model.addAttribute("expression", expression);
        }
        return "calculator/calculator";
    }
}