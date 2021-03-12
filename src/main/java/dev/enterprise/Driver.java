package dev.enterprise;

import dev.enterprise.config.LoggingConfig;
import dev.enterprise.logger.FileLogger;

import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException {
        FileLogger logger =  new FileLogger("output-file-name-template");
        logger.debug("Testing 123");
        logger.warning("Testing 123");
        logger.info("Testing 123");
        logger.debug("Testing 123");
    }
}
