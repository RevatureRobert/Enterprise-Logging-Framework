package dev.enterprise;

import dev.enterprise.config.LoggingConfig;
import dev.enterprise.logger.FileLogger;

import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException {
        FileLogger.getInstance().debug("Testing 123");

    }
}
