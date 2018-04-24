package be.calc.arithmetic;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.util.Assert;

/**
 * Abstract implementation of an Operation which accepts two values, represented as left and right Operands.
 * An Operation is also an Operand in order to support nested arithmetic Operation invocations.
 */
public abstract class AbstractPairOperation implements Operation, Operand {

    private ImmutablePair<Operand,Operand> operands;

    public AbstractPairOperation(Operand leftOperand, Operand rightOperand){
        Assert.notNull(leftOperand,"leftOperand is required");
        Assert.notNull(rightOperand,"rightOperand is required");
        operands = ImmutablePair.of(leftOperand,rightOperand);
    }

    @Override
    public Integer getValue(){
        return evaluate();
    }

    @Override
    public Integer evaluate(){
        return applyOperation(operands.left,operands.right);
    }

    protected abstract Integer applyOperation(Operand leftOperand, Operand rightOperand);

}
