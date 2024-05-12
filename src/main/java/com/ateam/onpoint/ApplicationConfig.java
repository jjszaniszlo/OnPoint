package com.ateam.onpoint;

import java.io.File;

public class ApplicationConfig {
    public static String USER_HOME = System.getProperty("user.home");
    public static String CONFIG_LOCATION = USER_HOME + File.separator + ".onpoint";

    public static String DATABASE_FILE = CONFIG_LOCATION + File.separator + "data.db";

    private ApplicationConfig(){}
}
