package site.hohyun.api.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalculatorVO 
{
    private int num1;
    private int num2;
    private int operator;
}
