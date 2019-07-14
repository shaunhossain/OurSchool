package com.jaminoss.android.ourschool;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by kodenerd on 2/15/18.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
