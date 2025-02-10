package com.example.teamcity.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Config implementation using Singleton pattern
public class Config {
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static Config config;
    private final Properties properties;

    private Config() {
        properties = new Properties();
        loadProperties();
    }

    public static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    private void loadProperties() {
        try (InputStream stream = Config.class.getClassLoader().getResourceAsStream(Config.CONFIG_PROPERTIES)) {
            if (stream == null) {
                System.err.println("File not found: " + Config.CONFIG_PROPERTIES);
            }
            properties.load(stream);
        } catch (IOException e) {
            System.err.println("Error while reading file: " + Config.CONFIG_PROPERTIES);
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return getConfig().properties.getProperty(key);
    }
}
