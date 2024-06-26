package org.alexdev.finlay.util.config;

import org.alexdev.finlay.util.config.writer.ConfigWriter;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOError;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerConfiguration {
    private static Logger log = LoggerFactory.getLogger(ServerConfiguration.class);
    private static Map<String, String> config = new ConcurrentHashMap<>();
    private static ConfigWriter writer;

    public static void load(String configPath) throws IOError, IOException, ConfigurationException {
        config = writer.setConfigurationDefaults();

        var configurationFile = Configuration.createConfigurationFile(configPath);

        if (configurationFile != null) {
            writer.setConfigurationData(config, configurationFile);
        }

        config = Configuration.load(configPath);
    }

    /**
     * Get key from configuration and cast to an Boolean
     *
     * @param key the key to use
     * @return value as boolean
     */
    public static boolean getBoolean(String key) {
        String val = config.getOrDefault(key, "false");

        if (val.equalsIgnoreCase("true")) {
            return true;
        }

        if (val.equals("1")) {
            return true;
        }

        return val.equalsIgnoreCase("yes");

    }

    /**
     * Get value from configuration
     *
     * @param key the key to use
     * @return value
     */
    public static String getString(String key) {
        return config.getOrDefault(key, key);
    }

    /**
     * Get value from configuration
     *
     * @param key the key to use
     * @return value
     */
    public static String getStringOrDefault(String key, String value) {
        return config.getOrDefault(key, value);
    }

    /**
     * Get value from configuration and cast to an Integer
     *
     * @param key the key to use
     * @return value as int
     */
    public static int getInteger(String key) {
        return Integer.parseInt(config.getOrDefault(key, "0"));
    }


    /**
     * Get if key exists.
     *
     * @param key to check
     * @return true if exists
     */
    public static boolean exists(String key) {
        return config.containsKey(key);
    }

    public static void setWriter(ConfigWriter writer) {
        ServerConfiguration.writer = writer;
    }
}
