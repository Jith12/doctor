package com.example.saver.Helper;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.multidex.MultiDex;

import com.pixplicity.easyprefs.library.Prefs;

import org.acra.ACRA;
import org.acra.annotation.AcraMailSender;

@AcraMailSender(mailTo = "shadowwsjeganathan@gmail.com")
public class Saver extends Application {

    public static Saver saver;

    @Override
    public void onCreate() {
        super.onCreate();
        saver = this;
        ACRA.init(this);
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        ACRA.init(this);
    }

    public static void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
}
