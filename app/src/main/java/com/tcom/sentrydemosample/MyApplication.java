package com.tcom.sentrydemosample;

import android.app.Application;
import android.os.StrictMode;

/**
 * Apps. main Application.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        strictMode();
        super.onCreate();
    }

    private void strictMode() {
        //    https://developer.android.com/reference/android/os/StrictMode
        //    StrictMode is a developer tool which detects things you might be doing by accident and
        //    brings them to your attention so you can fix them.
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }
}
