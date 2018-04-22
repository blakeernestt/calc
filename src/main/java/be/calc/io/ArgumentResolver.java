package be.calc.io;

import com.google.common.collect.ImmutableMap;

public interface ArgumentResolver {

    ImmutableMap<ArgumentType,String> resolve(String[] rawArgs);
}
