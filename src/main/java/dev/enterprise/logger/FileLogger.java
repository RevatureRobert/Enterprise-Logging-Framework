package dev.enterprise.logger;

import dev.enterprise.config.LoggingConfig;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    private final ByteBuffer BUFFER;

    private Path path;

    @Override
    public void debug(String message) throws IOException {
        addToBuffer(message,"Debug: ","Debug message is too long");
    }
    @Override
    public void info(String message) throws IOException {
        addToBuffer(message,"Info: ","Info message is too long");
    }

    @Override
    public void warning(String message) throws IOException {
        addToBuffer(message,"Warning: ","Warning message is too long");
    }

    private void addToBuffer(String message, String preamble, String exceptionMessage) throws IOException {
        message = preamble + LocalDateTime.now().toString() + " : " + message;
        if (message.length() <= 128) {
            BUFFER.put(message.getBytes(StandardCharsets.UTF_8));
            BUFFER.flip();
            FILE_CHANNEL.write(BUFFER);
            System.out.println(BUFFER);
            FILE_CHANNEL.force(false);
            //FILE_CHANNEL.truncate(message.getBytes(StandardCharsets.UTF_8).length);
        } else
            throw new IllegalArgumentException(exceptionMessage);
        BUFFER.clear();
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
            outputFilename = result.get();
        else
            throw new IOException("Error reading configuration file. property: output-file-name-template");
            this.path = Paths.get(outputDirectory+"/"+outputFilename);
            RandomAccessFile file = new RandomAccessFile("D:\\data\\revature documents\\gitrepos\\Enterprise-Logging-Framework\\test.txt","rw");
            return file.getChannel();
    }

    private FileLogger() throws IOException {
        this.configuration= LoggingConfig.getInstance();
        this.FILE_CHANNEL = getFileChannel();
        this.BUFFER = ByteBuffer.allocate(256);
    }
    private static FileLogger instance;

    public static FileLogger getInstance() throws IOException {
        return Optional.ofNullable(instance).orElse(instance = new FileLogger());
    }
}
