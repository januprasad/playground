package playground.jenuine;

import android.app.Application;

import timber.log.Timber;


/**
 * Created by jenuprasad on 15/01/18.
 */

public class Playground extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
//            Timber.plant(new CrashReportingTree());
        }


    }

}
