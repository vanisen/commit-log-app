package com.test.commitlog.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utils class
 *
 */
public class Utils {

    /**
     * Load properties file. Use the file name passed as arg and if arg not
     * passed then use config.prop file
     * 
     * @param configFileName
     */
    public static Properties loadProperties(String configFileName) {
        Properties props = new Properties();
        if (configFileName == null) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try (InputStream resourceStream = loader.getResourceAsStream("config.prop")) {
                props.load(resourceStream);
            } catch (IOException e) {
                System.err.println("Config Properties not found " + e.getMessage());
            }
        } else {
            try (FileInputStream stream = new FileInputStream(new File(configFileName))) {
                props.load(stream);
            } catch (IOException e) {
                System.err.println("Config Properties not found " + e.getMessage());
            }
        }
        return props;
    }
}
