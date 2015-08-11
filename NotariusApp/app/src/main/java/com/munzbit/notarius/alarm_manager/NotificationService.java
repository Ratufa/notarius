package com.munzbit.notarius.alarm_manager;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.munzbit.notarius.R;
import com.munzbit.notarius.activity.MainScreenActivity;
import com.munzbit.notarius.notification.AlarmTask;
import com.munzbit.notarius.notification.ScheduleClient;


public class NotificationService extends Service {

	private NotificationManager mManager;
	private ScheduleClient scheduleClient;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mManager = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
		scheduleClient = new ScheduleClient(this);
	}

	@SuppressLint("NewApi")
	@SuppressWarnings({ "deprecation", "static-access", "unchecked" })
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		Intent intent1 = new Intent(this.getApplicationContext(),
						MainScreenActivity.class);
		intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		intent1.setFlags(PendingIntent.FLAG_CANCEL_CURRENT);
		PendingIntent pi = PendingIntent.getActivity(this.getApplicationContext(), 0,
						intent1, 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this.getApplicationContext()).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Notarius").setContentText("Register today's training");
		mBuilder.setContentIntent(pi);
		mBuilder.setDefaults(Notification.DEFAULT_SOUND);
		mBuilder.setAutoCancel(true);
		mManager.notify(0, mBuilder.build());
		//new AlarmTask(this).run();
		//scheduleClient.doUnbindService();
		//scheduleClient.doBindService();
		//startService(new Intent(NotificationService.this, AlarmService.class));
		stopSelf();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}