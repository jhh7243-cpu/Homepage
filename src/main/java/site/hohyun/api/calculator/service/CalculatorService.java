package site.hohyun.api.calculator.service;

import site.hohyun.api.calculator.domain.CalculationDTO;
import site.hohyun.api.common.domain.Messenger;

/**
 * 계산기 서비스 인터페이스
 * 계산 비즈니스 로직 처리
 */
public interface CalculatorService 
{
    /**
     * 수식 계산 (고급 계산)
     */
    Messenger calculate(String expression);
    
    /**
     * 덧셈
     */
    int add(CalculationDTO calculationDTO);
    
    /**
     * 뺄셈
     */
    int subtract(CalculationDTO calculationDTO);
    
    /**
     * 곱셈
     */
    int multiply(CalculationDTO calculationDTO);
    
    /**
     * 나눗셈
     */
    int divide(CalculationDTO calculationDTO);
}