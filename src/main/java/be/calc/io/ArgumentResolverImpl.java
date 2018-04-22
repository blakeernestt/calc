package be.calc.io;


import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ArgumentResolverImpl implements ArgumentResolver {

    private Logger logger = LoggerFactory.getLogger(ArgumentResolverImpl.class);

    @Override
    public ImmutableMap<ArgumentType,String> resolve(String[] rawArgs) {
        logger.info("Starting argument resolution with a value of {}",Arrays.asList(rawArgs).toString());

        ImmutableMap.Builder<ArgumentType,String> resolvedArgsBuilder = ImmutableMap.builder();

        List<String> argList = new ArrayList<>(Arrays.asList(rawArgs));
        int index = argList.indexOf(ArgumentType.LOG_LEVEL.getArgName());
        if (index > 0) {
            argList.remove(index);
            if (argList.size() >= index) {
                resolvedArgsBuilder.put(ArgumentType.LOG_LEVEL,argList.get(index));
                logger.debug("Log level of {} provided by user",argList.get(index));
                argList.remove(index);
            } else {
                throw new IllegalArgumentException("Log level argument provided without a value");
            }
        }

        if (argList.size() == 1) {
            resolvedArgsBuilder.put(ArgumentType.CALC_EXPRESSION,argList.get(0));
            logger.debug("CalculatorImpl input of {} provided by user",argList.get(0));
        } else {
            throw new IllegalArgumentException("Issue resolving calculator input");
        }

        logger.info("Ending argument resolution");
        return resolvedArgsBuilder.build();
    }
}
