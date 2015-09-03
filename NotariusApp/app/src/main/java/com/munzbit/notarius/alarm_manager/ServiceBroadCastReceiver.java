package com.munzbit.notarius.alarm_manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Ratufa.Aditya on 9/1/2015.
 */
public class ServiceBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        context.startService(new Intent(context,AlarmService.class));
    }
}
