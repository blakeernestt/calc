package be.calc.arithmetic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddOperation extends AbstractPairOperation {

    private static Logger logger = LoggerFactory.getLogger(AddOperation.class);

    public AddOperation(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected Integer applyOperation(Operand leftOperand, Operand rightOperand) {
        Long leftValue = Long.valueOf(leftOperand.getValue());
        Long rightValue = Long.valueOf(rightOperand.getValue());
        logger.debug("attempting add operation with leftOperand {} and rightOperand {}",leftValue,rightValue);
        Long result = leftValue + rightValue;

        if (result <= Integer.MIN_VALUE || result >= Integer.MAX_VALUE) {
            throw new ArithmeticException("arithmetic overflow occurred during add operation");
        }
        return result.intValue();
    }
}
