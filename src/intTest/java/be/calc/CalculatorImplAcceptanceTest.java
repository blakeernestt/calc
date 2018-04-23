package be.calc;

import be.calc.arithmetic.OperationFactoryImpl;
import be.calc.parse.ParserImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        CalculatorImpl.class,
        ParserImpl.class,
        OperationFactoryImpl.class
})
public class CalculatorImplAcceptanceTest {

    @Inject
    private CalculatorImpl calculator;

    @Test
    public void arithmeticOperationEvaluatesCorrectlyWithNumericOperands(){
        assertThat(calculator.calculate("add(1,a)")).isEqualTo(3);
    }

    @Test
    public void arithmeticOperationEvaluatesCorrectlyWithNumericOperandAndArithmeticOperands(){
        assertThat(calculator.calculate("add(1,mult(2,3))")).isEqualTo(7);
    }

    @Test
    public void arithmeticOperationEvaluatesCorrectlyWithArithmeticOperands(){
        assertThat(calculator.calculate("mult(add(2,2),div(9,3))")).isEqualTo(12);
    }

    @Test
    public void letOperationEvaluatesCorrectlyWithNumericReplacementValueIntoArithmeticOperation(){
        assertThat(calculator.calculate("let(a,5,add(a,a))")).isEqualTo(10);
    }

    @Test
    public void letOperationEvaluatesCorrectlyWithNumericReplacementValueIntoLetOperation(){
        assertThat(calculator.calculate("let(a,5,let(b,mult(a,10),add(b,a)))")).isEqualTo(55);
    }

    @Test
    public void letOperationEvaluatesCorrectlyWithLetOperationReplacementValueLetOperation(){
        assertThat(calculator.calculate("let(a,let(b,10,add(b,b)),let(b,20,add(a,b)))")).isEqualTo(40);
    }
}
