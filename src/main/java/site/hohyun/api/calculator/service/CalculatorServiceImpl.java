package site.hohyun.api.calculator.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import site.hohyun.api.calculator.domain.CalculationDTO;
import site.hohyun.api.calculator.repository.CalculatorRepository;
import site.hohyun.api.common.domain.Messenger;

@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService
{
    private final CalculatorRepository calculatorRepository;
    
    /**
     * 수식 계산 (고급 계산)
     */
    @Override
    public Messenger calculate(String expression) {
        try {
            System.out.println("🧮 CalculatorService: 계산 시작 - " + expression);
            
            // Repository에서 계산 수행
            Messenger result = calculatorRepository.calculateResult(expression);
            
            System.out.println("🧮 CalculatorService: 계산 결과 - " + result.getMessage());
            return result;
            
        } catch (Exception e) {
            System.err.println("❌ CalculatorService: 계산 오류 - " + e.getMessage());
            return new Messenger(500, "계산 오류: " + e.getMessage());
        }
    }

    @Override
    public int add(CalculationDTO calculationDTO) {
        return calculationDTO.getNum1() + calculationDTO.getNum2();
    }

    @Override
    public int subtract(CalculationDTO calculationDTO) {
        return calculationDTO.getNum1() - calculationDTO.getNum2();
    }

    @Override
    public int multiply(CalculationDTO calculationDTO) {
        return calculationDTO.getNum1() * calculationDTO.getNum2();
    }

    @Override
    public int divide(CalculationDTO calculationDTO) {
        if (calculationDTO.getNum2() == 0) {
            throw new ArithmeticException("0으로 나눌 수 없습니다.");
        }
        return calculationDTO.getNum1() / calculationDTO.getNum2();
    }
}