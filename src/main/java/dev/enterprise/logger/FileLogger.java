package dev.enterprise.logger;

import dev.enterprise.config.LoggingConfig;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * this class actually could utilize nio in a meaningful way. Beinag able
 *      to append to the file during runtime without blocking the thread
 *      would be a good reason to do this here.
 */
// TODO: create an implementation of the Loggable methods to append to a file
public class FileLogger extends AbstractLogger{

    private final FileChannel FILE_CHANNEL;

    private final CharBuffer BUFFER;

    private Path path;

    @Override
    public void debug(String message) {
        message = "Debug: " + LocalDateTime.now().toString() + message;
        if (message.length() <= 128) {
            BUFFER.put(message);
        } else
            throw new IllegalArgumentException("Debug message is too long");
        BUFFER.clear();
    }
    @Override
    public void info(String message) {

    }

    @Override
    public void warning(String message) {

    }

    private FileChannel getFileChannel() throws IOException {
        String outputDirectory;
        String outputFilename;
        Optional<String> result = configuration.getPropertyByKey("output-directory");
        if (result.isPresent())
           outputDirectory = result.get();
        else throw new IOException("Error reading configuration file. property: output-directory");
        result = configuration.getPropertyByKey("output-file-name-template");
        if (result.isPresent())
            outputFilename = LocalDateTime.now() + result.get();
        else
            throw new IOException("Error reading configuration file. property: output-file-name-template");
            this.path = Paths.get(outputDirectory+"/"+outputFilename);
         return FileChannel.open(this.path);
    }

    private FileLogger() throws IOException {
        this.configuration= LoggingConfig.getInstance();
        this.FILE_CHANNEL = getFileChannel();
        this.BUFFER = CharBuffer.allocate(128);
    }
}
