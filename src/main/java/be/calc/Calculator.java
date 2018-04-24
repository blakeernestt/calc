package be.calc;

/**
 * Represents a simple Calculator that accepts an expression, evaluates, and returns the interpreted result.
 */
public interface Calculator {

    /**
     * Converts the provided expression into a series of Operations, evaluates the hierarchy of Operations and returns
     * the result.
     */
    int calculate(String expression);
}
