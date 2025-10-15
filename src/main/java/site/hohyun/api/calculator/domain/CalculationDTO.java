package site.hohyun.api.calculator.domain;

/**
 * 계산 데이터 전송 객체
 * 계산기 입력 데이터를 전달하기 위한 DTO
 */
public class CalculationDTO 
{
    private Double num1;
    private Double num2;
    private String operator;

    // Getter
    public Double getNum1() 
    {
        return num1;
    }

    public Double getNum2() 
    {
        return num2;
    }

    public String getOperator() 
    {
        return operator;
    }

    // Setter
    public void setNum1(Double num1) 
    {
        this.num1 = num1;
    }

    public void setNum2(Double num2) 
    {
        this.num2 = num2;
    }

    public void setOperator(String operator) 
    {
        this.operator = operator;
    }

    @Override
    public String toString() 
    {
        return "CalculationDTO{" +
                "num1=" + num1 +
                ", num2=" + num2 +
                ", operator='" + operator + '\'' +
                '}';
    }
}

