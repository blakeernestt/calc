package be.calc.parse;

import be.calc.arithmetic.Operation;

/**
 * Implementations are responsible for accepting a raw String based calculation expression, parsing it into Operations
 * containing Operands, and returning the overall Operation to be evaluated.
 */
public interface Parser {

    /**
     * Convert the provided expression into a hierarchy of Operations, returning the top most Operation to be
     * evaluated.
     */
    Operation parse(String expression);
}
