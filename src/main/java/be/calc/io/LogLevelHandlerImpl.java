package be.calc.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.Level;

import java.util.Arrays;

@Component
public class LogLevelHandlerImpl implements LogLevelHandler {

    private Logger logger = LoggerFactory.getLogger(LogLevelHandlerImpl.class);

    @Override
    public void adjustLogLevel(String logLevel) {
        changeLevel(resolveLogLevel(logLevel));
    }

    //By convention logging is configured externally via a properties or xml file and not dynamically at runtime.
    //To support setting dynamically the Log4J API is used directly rather than the SLF4J wrapper.  It would be
    //preferable to use the wrapper only.
    private Level resolveLogLevel(String logLevel){
        return Arrays.asList(org.apache.logging.log4j.Level.values()).stream()
                .filter(l -> l.toString().equalsIgnoreCase(logLevel))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid log level supplied"));
    }

    private void changeLevel(Level level){
        Configurator.setAllLevels(LogManager.getRootLogger().getName(),
                org.apache.logging.log4j.Level.getLevel(level.name()));
        logger.info("Log level adjusted to {}",level);
    }
}
