package com.qb.xrealsys.ifafu.Tool;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by sky on 10/02/2018.
 */

public class ConfigHelper {

    private Properties systemProperties;

    private Properties userProperties;

    private Context    context;

    public ConfigHelper(Context inContext) throws IOException {
        context    = inContext;
        systemProperties = new Properties();
        userProperties   = new Properties();

        systemProperties.load(context.getAssets().open("config.properties"));
        try {
            InputStream fis = context.openFileInput("user.properties");
            userProperties.load(fis);
        } catch (IOException e) {
            userProperties.load(context.getAssets().open("user.properties"));
        }
    }

    public String GetSystemValue(String key) {
        return systemProperties.getProperty(key);
    }

    public String GetValue(String key) {
        String answer = userProperties.getProperty(key);
        if (answer == null) {
            return systemProperties.getProperty(key);
        } else {
            return userProperties.getProperty(key);
        }
    }

    public void SetValue(String key, String value) {
        userProperties.setProperty(key, value);
        OutputStream fos;
        try {
            fos = context.openFileOutput("user.properties", Context.MODE_PRIVATE);
            userProperties.store(fos, null);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
