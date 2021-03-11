package dev.enterprise.config;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LoggingConfig {


    //These should be overridable in the future, but going to leave as is for demo purposes
    private final Path CONFIG_FILE_LOCATION = Paths.get("src/main/resources/william.config");
    private Path logFileLocation;

    //This would hold our properties in a map for later use
    private final Map<String, String> PROPERTIES = new HashMap<>();

    private LoggingConfig() throws IOException {getProperties();}

    private static LoggingConfig instance;

    public static LoggingConfig getInstance() throws IOException {
        return Optional.ofNullable(instance).orElse(instance = new LoggingConfig());
    }

    public Optional<String> getPropertyByKey(String key) {
        return Optional.of(PROPERTIES.get(key));
    }

    /**
    Gather the properties from the config file and add them to the map. Honestly, not the best
          example of using nio due to loading it all in during init. synch. io could be used just as
          well, but does serve as a good example of utilizing nio to read from a file.
     */
    public void getProperties() throws IOException {
        List<String> lines = Files.readAllLines(CONFIG_FILE_LOCATION);
        lines.stream().forEach((String line) -> {
            String[] splits = line.split("=");
            PROPERTIES.put(splits[0], splits[1].replace("\"",""));
        });
    }


    public static void main(String[] args) throws IOException {
        LoggingConfig.getInstance().getProperties();
    }
}
