package dev.enterprise;

import dev.enterprise.config.LoggingConfig;

import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException {
        LoggingConfig.getInstance().getProperties();

    }
}
