package com.mydways.aadabhyd.application;

import android.app.Application;

import com.mydways.aadabhyd.network.AppAPIManager;
import com.squareup.otto.Bus;

/**
 * @author Kishore Adda on 31/3/18.
 */
public class AadabHydApplication extends Application {
    private static Bus sEventBus;
    private static AppAPIManager mAppAPIManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sEventBus = new Bus();
        mAppAPIManager = new AppAPIManager();
    }

    public static Bus getEventBus() {
        return sEventBus;
    }

    public static AppAPIManager getAppAPIManager() {
        return mAppAPIManager;
    }
}
