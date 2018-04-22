package be.calc;

import be.calc.io.ArgumentResolver;
import be.calc.io.ArgumentType;
import be.calc.io.LogLevelHandler;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static be.calc.io.ArgumentType.CALC_EXPRESSION;
import static be.calc.io.ArgumentType.LOG_LEVEL;

@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args){
        Logger logger = LoggerFactory.getLogger(Main.class);
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        try {
            ImmutableMap<ArgumentType,String> resolvedArgs = context.getBean(ArgumentResolver.class).resolve(args);
            if (resolvedArgs.containsKey(LOG_LEVEL)) {
                context.getBean(LogLevelHandler.class).adjustLogLevel(resolvedArgs.get(LOG_LEVEL));
            }

            System.out.println(String.format("Expression evaluated to: %d",
                    context.getBean(Calculator.class).calculate(resolvedArgs.get(CALC_EXPRESSION))));
        } catch (IllegalArgumentException | ArithmeticException ex) {
            logger.error("Invalid calculator expression provided",ex);
            System.out.println("Invalid calculator expression provided, please check the logs");
        }

    }
}