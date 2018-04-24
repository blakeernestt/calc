package be.calc.arithmetic;

/**
 * Represents a simple factory for creation of Operation instances given an OperationType, and its left and right
 * Operands
 */
public interface OperationFactory {

    Operation create(OperationType operationType, Operand leftOperation, Operand rightOperand);
}
