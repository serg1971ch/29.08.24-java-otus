package ru.otus;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerConfig {
    public int port;
    public int poolSize;

    public ServerConfig(String configFile) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(configFile)) {
            properties.load(input);
            port = Integer.parseInt(properties.getProperty("port", "8080"));
            poolSize = Integer.parseInt(properties.getProperty("poolSize", "10"));
        }
    }
}
