package be.calc.arithmetic;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class OperationFactoryImpl implements OperationFactory {
    @Override
    public Operation create(OperationType operationType, Operand leftOperation, Operand rightOperand) {
        Assert.notNull(operationType,"Operation type required");
        Operation operation;
        switch(operationType){
            case ADD:
                operation = new AddOperation(leftOperation,rightOperand);
                break;
            case SUB:
                operation = new SubOperation(leftOperation,rightOperand);
                break;
            case DIV:
                operation = new DivOperation(leftOperation,rightOperand);
                break;
            default:
                operation = new MultOperation(leftOperation,rightOperand);
        }
        return operation;
    }
}
