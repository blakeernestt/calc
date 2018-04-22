package be.calc;

import be.calc.arithmetic.OperationFactoryImpl;
import be.calc.parse.ParserImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
public class CalculatorImplArithmeticOperationTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    private static final Long MAX_VALUE_PLUS_1 = Long.valueOf(Integer.MAX_VALUE) + 1;
    private static final Long MIN_VALUE_MINUS_1 = Long.valueOf(Integer.MIN_VALUE) - 1;

    @Inject
    private CalculatorImpl calculator;

    @Test
    public void addEvaluatesCorrectlyWithNumericOperands(){
        assertThat(calculator.calculate("add(6,2)")).isEqualTo(8);
    }

    @Test
    public void addEvaluatesCorrectlyWithNumericOperandsReversed(){
        assertThat(calculator.calculate("add(2,1)")).isEqualTo(3);
    }

    @Test
    public void subEvaluatesCorrectlyWithNumericOperands(){
        assertThat(calculator.calculate("sub(1,2)")).isEqualTo(-1);
    }

    @Test
    public void subEvaluatesCorrectlyWithNumericOperandsReversed(){
        assertThat(calculator.calculate("sub(2,1)")).isEqualTo(1);
    }

    @Test
    public void multEvaluatesCorrectlyWithNumericOperands(){
        assertThat(calculator.calculate("mult(2,4)")).isEqualTo(8);
    }

    @Test
    public void multEvaluatesCorrectlyWithNumericOperandsReversed(){
        assertThat(calculator.calculate("mult(4,2)")).isEqualTo(8);
    }

    @Test
    public void divEvaluatesCorrectlyWithNumericOperands(){
        assertThat(calculator.calculate("div(6,2)")).isEqualTo(3);
    }

    @Test
    public void divEvaluatesCorrectlyWithNumericOperandsReversedRemainderDiscarded(){
        assertThat(calculator.calculate("div(2,6)")).isEqualTo(0);
    }

    @Test
    public void divThrowsIllegalArgumentExceptionWhenDivisorIsZero(){
        expected.expect(IllegalArgumentException.class);
        expected.expectMessage("divisor must be greater than 0");
        calculator.calculate("div(2,0)");
    }

    @Test
    public void operationThrowsExceptionWhenRightOperandGreaterThanIntegerMax(){
        expected.expect(IllegalArgumentException.class);
        expected.expectMessage("Numerical operand exceeds min/max value");
        calculator.calculate("add(1," + MAX_VALUE_PLUS_1 + ")");
    }

    @Test
    public void operationThrowsExceptionWhenLeftOperandGreaterThanIntegerMax(){
        expected.expect(IllegalArgumentException.class);
        expected.expectMessage("Numerical operand exceeds min/max value");
        calculator.calculate("add(" + MAX_VALUE_PLUS_1 + ",1)");
    }

    @Test
    public void operationThrowsExceptionWhenRightOperandLessThanIntegerMin(){
        expected.expect(IllegalArgumentException.class);
        expected.expectMessage("Numerical operand exceeds min/max value");
        calculator.calculate("add(1," + MIN_VALUE_MINUS_1 + ")");
    }

    @Test
    public void operationThrowsExceptionWhenLeftOperandLessThanIntegerMin(){
        expected.expect(IllegalArgumentException.class);
        expected.expectMessage("Numerical operand exceeds min/max value");
        calculator.calculate("add(" + MIN_VALUE_MINUS_1 + ",1)");
    }

    @Test
    public void operationThrowsIllegalArgumentExceptionWithCharacterAsLeftOperand(){
        expected.expect(IllegalArgumentException.class);
        expected.expectMessage("Invalid calculator expression");
        calculator.calculate("add(a,2)");
    }

    @Test
    public void operationThrowsIllegalArgumentExceptionWithCharacterAsRightOperand(){
        expected.expect(IllegalArgumentException.class);
        expected.expectMessage("Invalid calculator expression");
        calculator.calculate("add(2,a)");
    }
}
