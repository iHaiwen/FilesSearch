package com.mobile.hw.filessearch.app;

import android.app.Application;

/**
 * Created with IntelliJ IDEA.
 * User: haiwen.li
 * Date: 1/25/15
 * Time: 6:25 PM
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    public BaseApplication() {
        instance = this;
    }

    public static BaseApplication getInstance() {

        return instance;
    }
}
