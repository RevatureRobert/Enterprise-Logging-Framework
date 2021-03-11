package dev.enterprise.logger;

import java.io.IOException;

public interface Loggable {

    void debug(String message) throws IOException;

    void info(String message) throws IOException;

    void warning(String message) throws IOException;
}
