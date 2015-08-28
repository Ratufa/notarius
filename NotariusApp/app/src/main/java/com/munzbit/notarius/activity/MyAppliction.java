package com.munzbit.notarius.activity;

import android.app.Application;
import android.util.Log;

import com.munzbit.notarius.alarm_manager.AlarmManagerHelper;

/**
 * Created by Ratufa.Aditya on 8/27/2015.
 */
public class MyAppliction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        Log.e("app terminated","true");
        AlarmManagerHelper.cancelAlarms(this);
        super.onTerminate();

    }
}
