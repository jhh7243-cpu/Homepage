package site.hohyun.api.calculator.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalculatorEntity 
{
    private int num1;
    private int num2;
    private int operator;   
}
