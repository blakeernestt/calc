package be.calc.io;

/**
 * This handler is responsible for accepting a String representation of a log level and adjusting the level
 * accordingly.
 */
public interface LogLevelHandler {

    /**
     * Convert the provided logLevel into a standard level (ERROR, INFO, DEBUG etc) and update the logger dynamically
     */
    void adjustLogLevel(String logLevel);
}
