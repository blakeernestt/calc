package be.calc.arithmetic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubOperation extends AbstractPairOperation {

    private static Logger logger = LoggerFactory.getLogger(SubOperation.class);

    public SubOperation(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected Integer applyOperation(Operand leftOperand, Operand rightOperand) {
        Long leftValue = Long.valueOf(leftOperand.getValue());
        Long rightValue = Long.valueOf(rightOperand.getValue());

        logger.debug("attempting sub operation with leftOperand {} and rightOperand {}",leftValue,rightValue);
        Long result = leftValue - rightValue;

        if (result <= Integer.MIN_VALUE || result >= Integer.MAX_VALUE) {
            throw new ArithmeticException("arithmetic overflow occurred during sub operation");
        }
        return result.intValue();
    }
}
