package site.hohyun.api.calculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import site.hohyun.api.calculator.domain.CalculationDTO;
import site.hohyun.api.calculator.service.CalculatorService;

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
    public String calculatorPage() 
    {
        return "calculator/calculator";
    }

    /**
     * 계산 처리
     * 
     * @param num1     첫 번째 숫자
     * @param num2     두 번째 숫자
     * @param operator 연산자 (+, -, *, /)
     * @param model    모델 객체
     * @return 계산기 페이지 템플릿
     */
    @PostMapping("/calculate")
    public String calculate(@RequestParam("num1") Double num1,
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
}