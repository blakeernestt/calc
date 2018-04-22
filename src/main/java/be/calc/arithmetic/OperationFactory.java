package be.calc.arithmetic;

public interface OperationFactory {

    Operation create(OperationType operationType, Operand leftOperation, Operand rightOperand);
}
