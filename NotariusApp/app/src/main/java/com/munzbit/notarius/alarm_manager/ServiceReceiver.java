package com.munzbit.notarius.alarm_manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Ratufa.Manish on 8/13/2015.
 */
public class ServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, AlarmService.class);
        context.startService(myIntent);
    }
}
