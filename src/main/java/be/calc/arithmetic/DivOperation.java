package be.calc.arithmetic;

import org.springframework.util.Assert;

public class DivOperation extends AbstractPairOperation {

    public DivOperation(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected Integer applyOperation(Operand leftOperand, Operand rightOperand) {
        Assert.isTrue(rightOperand.getValue() > 0,"divisor must be greater than 0");
        return leftOperand.getValue() / rightOperand.getValue();
    }
}
