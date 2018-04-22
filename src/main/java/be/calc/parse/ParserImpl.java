package be.calc.parse;

import be.calc.arithmetic.*;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.Optional;

@Component
public class ParserImpl implements Parser {

    private static Logger logger = LoggerFactory.getLogger(ParserImpl.class);
    private OperationFactory operationFactory;

    @Inject
    public ParserImpl(OperationFactory operationFactory){
        this.operationFactory = operationFactory;
    }

    @Override
    public Operation parse(String expression) {
        Assert.notNull(expression, "Expression is required");
        expression = expression.toLowerCase().replaceAll("\\s","");
        Optional<Operation> operation = parseLetOperation(expression);
        operation = operation.isPresent() ? operation : parseArithmeticOperation(expression);
        return operation.orElseThrow(() -> new IllegalArgumentException("Expression could no not be parsed"));
    }

    private Optional<Operation> parseLetOperation(String expression) {
        logger.debug("parsing let operation with expression {}",expression);
        Optional<Operation> operation = Optional.empty();

        if (expression.matches("^(let){1}\\([a-z]{1},.+,.+\\)")) {
            //Parse variable name
            expression = StringUtils.substring(expression, expression.indexOf('(') + 1);
            expression = StringUtils.chop(expression);
            char variableName = CharUtils.toChar(expression);
            logger.debug("variable name to replace {}",variableName);
            expression = StringUtils.substring(expression, expression.indexOf(',') + 1);

            //Parse Operands
            Optional<String[]> rawOperands = splitOperands(expression);
            String rawValueOperand = rawOperands.map(a -> a[0])
                    .orElseThrow(() -> new IllegalArgumentException("Invalid calculator expression"));
            Operand valueOperand = parseOperand(rawValueOperand);
            String replacementValue = String.valueOf(valueOperand.getValue());
            logger.debug("replacement value {}",rawValueOperand);

            String rawExpressionOperand = rawOperands.map(a -> a[1])
                    .orElseThrow(() -> new IllegalArgumentException("Invalid calculator expression"));
            logger.debug("expression before replacement {}",rawExpressionOperand);

            rawExpressionOperand = rawExpressionOperand.replaceAll("\\("+variableName+",","("+replacementValue+",");
            rawExpressionOperand = rawExpressionOperand.replaceAll(","+variableName+"\\)",","+replacementValue+")");
            logger.debug("expression after replacement {}",rawExpressionOperand);

            Operand expressionOperand = parseOperand(rawExpressionOperand);

            operation = Optional.of(new LetOperation(variableName, valueOperand, expressionOperand));
        }
        logger.debug("Parsing LetOperation end");
        return operation;
    }

    private Optional<Operation> parseArithmeticOperation(String expression) {
        logger.debug("parsing arithmetic operation with expression {}",expression);
        Optional<Operation> operation = Optional.empty();

        if (expression.matches("^(add|sub|mult|div){1}\\(.+,.+\\)")) {
            //Operand
            String[] rawValues = expression.split("\\(",2);
            Assert.isTrue(rawValues.length == 2,"Invalid calculator expression");
            String rawOperation = rawValues[0];
            logger.debug("arithmetic operation type parsed {}",rawOperation);

            //Operands
            //Strip off the trailing ')'
            String rawOperandExp = StringUtils.chop(rawValues[1]);

            Optional<String[]> operands = splitOperands(rawOperandExp);
            String[] rawOperands = operands.orElseThrow(() ->
                    new IllegalArgumentException("Invalid calculator expression"));
            logger.debug("left operand {}",rawOperands[0]);
            logger.debug("right operand {}",rawOperands[1]);

            operation = Optional.of(operationFactory.create(OperationType.valueOf(rawOperation.toUpperCase()),
                    parseOperand(rawOperands[0]), parseOperand(rawOperands[1])));
        }
        logger.debug("parsing arithmetic operation end");
        return operation;
    }

    private Optional<String[]> splitOperands(String expression){
        logger.debug("split operands called with expression {}",expression);
        Optional<String[]> rawOperands = Optional.empty();

        //Left Operand is an Operation
        if (expression.matches("^(let|add|sub|mult|div){1}\\(.+")) {

            int parenthesesCnt = 1;
            char[] expSearch = expression.toCharArray();
            int splitIndex = expression.indexOf('(') + 1;

            for (; splitIndex < expSearch.length; splitIndex++) {
                parenthesesCnt = expSearch[splitIndex] == '(' ? parenthesesCnt+1 : parenthesesCnt;
                parenthesesCnt = expSearch[splitIndex] == ')' ? parenthesesCnt-1 : parenthesesCnt;

                if (parenthesesCnt == 0) {
                    break;
                }
            }
            rawOperands = splitIndex < expression.length() ?
                    Optional.of(new String[]{
                            StringUtils.substring(expression, 0, splitIndex + 1),
                            StringUtils.stripStart(
                                    StringUtils.substring(expression, splitIndex + 1, expression.length()),",")}) :
                    rawOperands;

        } else if (expression.matches("^-?\\d+,.+")) {
            //Left Operand is a NumericOperand

            int splitIndex = expression.indexOf(',');
            rawOperands = Optional.of(new String[]{
                    StringUtils.substring(expression, 0, splitIndex),
                    StringUtils.stripStart(
                            StringUtils.substring(expression, splitIndex, expression.length()),",")});
        }
        logger.debug("split operands end");
        return rawOperands;
    }

    private Operand parseOperand(String expression) {
        logger.debug("parsing operand with expression {}",expression);

        return parseLetOperation(expression).map(o -> (Operand)o).orElseGet(() ->
                parseArithmeticOperation(expression).map(o -> (Operand)o).orElseGet(() -> {
                    Assert.isTrue(expression.matches("^-?\\d+"),"Invalid calculator expression");
                    Long numericValue = Long.valueOf(expression);
                    Assert.isTrue(numericValue >= Integer.MIN_VALUE && numericValue <= Integer.MAX_VALUE,
                            "Numerical operand exceeds min/max value");
                    return new NumericOperand(numericValue.intValue());
                })
        );
    }
}
