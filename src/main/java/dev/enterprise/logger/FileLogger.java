package dev.enterprise.logger;

import dev.enterprise.config.LoggingConfig;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * this class actually could utilize nio in a meaningful way. Being able
 *      to append to the file during runtime without blocking the thread
 *      would be a good reason to do this here.
 */
// TODO: create an implementation of the Loggable methods to append to a file
public class FileLogger extends AbstractLogger{

    private final FileChannel CHANNEL;

    private final ByteBuffer BUFFER;

    private Path path;

    @Override
    public void debug(String message) throws IOException {
        addToBuffer(message, "Debug:   ","Debug message is too long");
    }
    @Override
    public void info(String message) throws IOException {
        addToBuffer(message, "Info:    ","Info message is too long");
    }

    @Override
    public void warning(String message) throws IOException {
        addToBuffer(message, "Warning: ","Warning message is too long");
    }

    // for when you don't specify the exception Message
    private void addToBuffer(String message, String preamble) throws IOException {
        addToBuffer(message,preamble,"");
    }

    private void addToBuffer(String message, String preamble, String exceptionMessage) throws IOException {
        message = preamble + LocalDateTime.now().toString() + " : " + message + System.lineSeparator();
        if (message.length() <= 128) {
            BUFFER.put(message.getBytes(StandardCharsets.UTF_8));
            BUFFER.flip();
            CHANNEL.position(CHANNEL.size());
            CHANNEL.write(BUFFER);
            CHANNEL.force(false);
        } else{
            throw new IllegalArgumentException(exceptionMessage);
        }
        BUFFER.clear();
    }

    private Path getFilePath(String directoryKey, String filenameKey) throws IOException {
        String outputDirectory;
        String outputFilename;
        Optional<String> result = configuration.getPropertyByKey(directoryKey);
        if (result.isPresent())
            outputDirectory = result.get();
        else
            throw new IOException("Error reading configuration file. property: " + directoryKey);
        result = configuration.getPropertyByKey(filenameKey);
        if (result.isPresent())
            outputFilename = result.get();
        else
            throw new IOException("Error reading configuration file. property: " + filenameKey);
        this.path = Paths.get(outputDirectory+"/"+outputFilename);
        return path;
    }

    private FileChannel getFileChannel(String directoryKey, String filenameKey) throws IOException {
        return new RandomAccessFile(getFilePath(directoryKey, filenameKey).toFile(),"rw").getChannel();
    }

    private FileLogger() throws IOException {
        this.configuration= LoggingConfig.getInstance();

        this.CHANNEL = getFileChannel("output-directory", "output-file-name-template");

        this.BUFFER = ByteBuffer.allocate(256);

        // Let's us know where in the file we started logging this particular session
        addToBuffer("New Logging Session","  Begin: ");
    }
    private static FileLogger instance;

    public static FileLogger getInstance() throws IOException {
        return Optional.ofNullable(instance).orElse(instance = new FileLogger());
    }
}
