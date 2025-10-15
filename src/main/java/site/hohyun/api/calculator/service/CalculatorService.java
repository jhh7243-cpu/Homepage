package site.hohyun.api.calculator.service;

import org.springframework.stereotype.Service;
import site.hohyun.api.calculator.domain.CalculationDTO;

/**
 * 계산기 서비스
 * 계산 비즈니스 로직 처리
 */
@Service
public class CalculatorService 
{
    /**
     * 계산 수행
     * @param calculationDTO 계산 정보
     * @return 계산 결과
     */
    public Double calculate(CalculationDTO calculationDTO)
    {
        System.out.println("계산기 서비스로 들어옴");
        System.out.println("전달된 첫 번째 숫자 = " + calculationDTO.getNum1());
        System.out.println("전달된 두 번째 숫자 = " + calculationDTO.getNum2());
        System.out.println("전달된 연산자 = " + calculationDTO.getOperator());
        
        Double num1 = calculationDTO.getNum1();
        Double num2 = calculationDTO.getNum2();
        String operator = calculationDTO.getOperator();
        
        Double result = 0.0;
        
        switch (operator) 
        {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 != 0) 
                {
                    result = num1 / num2;
                } 
                else 
                {
                    throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
                }
                break;
            default:
                throw new IllegalArgumentException("지원하지 않는 연산자입니다: " + operator);
        }
        
        System.out.println("계산 결과 = " + result);
        return result;
    }
}

