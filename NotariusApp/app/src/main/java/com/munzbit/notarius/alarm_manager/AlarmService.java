package com.munzbit.notarius.alarm_manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.munzbit.notarius.R;
import com.munzbit.notarius.activity.MainScreenActivity;

public class AlarmService extends Service {

	public static String TAG = AlarmService.class.getSimpleName();

	private static final int NOTIFICATION = 123;
	// Name of an intent extra we can use to identify if this service was started to create a notification
	public static final String INTENT_NOTIFY = "com.notarius.service.INTENT_NOTIFY";
	// The system notification manager
	private NotificationManager mManager;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		mManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

		notification();
		
		AlarmManagerHelper.setAlarms(this);
		
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//mManager.cancel(NOTIFICATION);
	}

	public void notification(){

		CharSequence title = "Notarius";
		// This is the icon to use on the notification
		int icon = R.drawable.ic_launcher;
		// This is the scrolling text of the notification
		CharSequence text = "Register todays training";
		// What time to show on the notification
		long time = System.currentTimeMillis();

		Notification notification = new Notification(icon, text, time);

		// The PendingIntent to launch our activity if the user selects this notification
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainScreenActivity.class), 0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this, title, text, contentIntent);

		// Clear the notification when it is pressed
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// Send the notification to the system.
		mManager.notify(NOTIFICATION, notification);
		stopSelf();

	}
	
}