package com.augmentum.oes.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties properties;
    static {
        InputStream in = null;
        try {
            properties = new Properties();
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
