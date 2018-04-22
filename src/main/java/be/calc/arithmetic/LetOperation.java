package be.calc.arithmetic;

import org.springframework.util.Assert;

public class LetOperation extends AbstractPairOperation {

    private char variableName;

    public LetOperation(char variableName, Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
        Assert.notNull(variableName,"variableName is required");
        Assert.isTrue(String.valueOf(variableName).matches("[a-zA-Z]{1}"),
                "Variable name must be 'a' through 'z' or 'A' through 'Z'");
        Assert.isTrue(rightOperand instanceof Operation, "rightOperand must be an Operation");
        this.variableName = variableName;
    }

    @Override
    protected Integer applyOperation(Operand leftOperand, Operand rightOperand) {
        return rightOperand.getValue();
    }
}
