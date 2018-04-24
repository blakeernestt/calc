package be.calc.io;

import com.google.common.collect.ImmutableMap;

/**
 * Implementations are responsible for parsing Calculator command line arguments into ArgumentType/String mappings
 * for easy consumption.
 */
public interface ArgumentResolver {

    /**
     * Accepts a raw array of command line arguments and converts them into a Map of ArgumentTypes with their
     * respective values as Strings.
     */
    ImmutableMap<ArgumentType,String> resolve(String[] rawArgs);
}
