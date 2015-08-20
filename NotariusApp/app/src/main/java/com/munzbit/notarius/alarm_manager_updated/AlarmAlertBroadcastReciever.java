package com.munzbit.notarius.alarm_manager_updated;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmAlertBroadcastReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Intent mathAlarmServiceIntent = new Intent(
				context,
				AlarmServiceBroadcastReciever.class);

		context.sendBroadcast(mathAlarmServiceIntent, null);
		
		StaticWakeLock.lockOn(context);

		Intent mathAlarmAlertActivityIntent;

		mathAlarmAlertActivityIntent = new Intent(context, NotificationService.class);

		context.startService(mathAlarmAlertActivityIntent);

	}

}
