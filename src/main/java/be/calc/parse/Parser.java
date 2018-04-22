package be.calc.parse;

import be.calc.arithmetic.Operation;

public interface Parser {

    Operation parse(String expression);
}
