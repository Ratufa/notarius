package com.munzbit.notarius.alarm_manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Ratufa.Manish on 8/11/2015.
 */
public class WakeupReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent myIntent = new Intent(context, AlarmService.class);
        context.startService(myIntent);
    }
}
