package com.munzbit.notarius.alarm_manager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReceiver extends BroadcastReceiver {


	@Override
	public void onReceive(Context context, Intent intent) {

		Intent myIntent = new Intent(context, NotificationService.class);
		context.startService(myIntent);
	}
}