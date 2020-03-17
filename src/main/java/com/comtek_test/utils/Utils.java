package com.comtek_test.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public class Utils {

    public static int generateRandomDuration(int minDuration, int maxDuration) {
        return new Random().ints(minDuration, maxDuration).findFirst().getAsInt();
    }

    public static int getDuration(String property, String filePath) throws IOException {
        try (InputStream input = new FileInputStream(filePath)) {
            Properties properties = new Properties();
            properties.load(input);
            return Integer.parseInt(properties.getProperty(property)) * 1000;
        } catch (IOException ex) {
            throw new IOException("Can't find properties file");
        }
    }
}
