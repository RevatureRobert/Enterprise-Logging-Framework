package dev.enterprise.logger;

import dev.enterprise.config.LoggingConfig;

/**
 * this class actually could utilize nio in a meaningful way. Beinag able
 *      to append to the file during runtime without blocking the thread
 *      would be a good reason to do this here.
 */
// TODO: create an implementation of the Loggable methods to append to a file
public class FileLogger extends AbstractLogger{

    @Override
    public void debug(String message) {

    }

    @Override
    public void info(String message) {

    }

    @Override
    public void warning(String message) {

    }

    private FileLogger(){
        this.configuration= LoggingConfig.getInstance();
    }
}
