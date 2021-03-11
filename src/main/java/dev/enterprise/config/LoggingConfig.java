package dev.enterprise.config;

import sun.rmi.runtime.Log;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class LoggingConfig {


    //These should be overridable in the future, but going to leave as is for demo purposes
    private Path configFileLocation = Paths.get("src/main/resources/william.config");
    private Path logFileLocation;

    //This would hold our properties in a map for later use
    Map<String, String> properties;

    private LoggingConfig(){}

    private static LoggingConfig instance;

    public static LoggingConfig getInstance(){
        return Optional.ofNullable(instance).orElse(instance = new LoggingConfig());
    }

    /**
    Gather the properties from the config file and add them to the map. Honestly, not the best
          example of using nio due to loading it all in during init. synch. io could be used just as
          well, but does serve as a good example of utilizing nio to read from a file.
     */
    public void getProperties() throws IOException {
        properties = new HashMap<>();
        FileChannel channel = FileChannel.open(configFileLocation);
        ByteBuffer buffer = ByteBuffer.allocate(48);
        FileInputStream in = new FileInputStream(configFileLocation.toFile());
        in.getChannel().read(buffer);
        List<String> lines = Files.readAllLines(configFileLocation);
        lines.stream().forEach((String line) -> {
            String[] splits = line.split("=");
            properties.put(splits[0], splits[1].replace("\"",""));
        });
    }


    public static void main(String[] args) throws IOException {
        LoggingConfig.getInstance().getProperties();
    }
}
