package be.calc.arithmetic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OperationOverflowTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Test
    public void addThrowsArithmeticExceptionOnOverflow(){
        Operation operation = new AddOperation(new NumericOperand(Integer.MAX_VALUE), new NumericOperand(2));
        expected.expect(ArithmeticException.class);
        expected.expectMessage("arithmetic overflow occurred during add operation");
        operation.evaluate();
    }

    @Test
    public void multThrowsArithmeticExceptionOnOverflow(){
        Operation operation = new MultOperation(new NumericOperand(Integer.MAX_VALUE), new NumericOperand(2));
        expected.expect(ArithmeticException.class);
        expected.expectMessage("arithmetic overflow occurred during mult operation");
        operation.evaluate();
    }

    @Test
    public void subThrowsArithmeticExceptionOnOverflow(){
        Operation operation = new SubOperation(new NumericOperand(Integer.MIN_VALUE), new NumericOperand(1));
        expected.expect(ArithmeticException.class);
        expected.expectMessage("arithmetic overflow occurred during sub operation");
        operation.evaluate();
    }
}
