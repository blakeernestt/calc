package be.calc;

import be.calc.parse.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.inject.Inject;

@Component
public class CalculatorImpl implements Calculator {

    private static Logger logger = LoggerFactory.getLogger(CalculatorImpl.class);
    private Parser parser;

    @Inject
    public CalculatorImpl(Parser parser){
        this.parser = parser;
    }

    @Override
    public int calculate(String expression){
        Assert.notNull(expression,"expression is required");
        logger.debug("CalculatorImpl starting with provided input: {}", expression);
        int result = parser.parse(expression).evaluate();
        logger.debug("CalculatorImpl expression evaluated to {}", result);
        return result;
     }

}
