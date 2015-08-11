package com.munzbit.notarius.alarm_manager;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.munzbit.notarius.datamanager.DataManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AlarmService extends WakefulIntentService {


    private Calendar c;

    private int mYear;
    private int mMonth;
    private int mDay;

    private DataManager dataManager;

    private ArrayList<PendingIntent> intentArray;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        c = Calendar.getInstance();
        dataManager = new DataManager(this);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        //NotificationUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        c.set(Calendar.YEAR, mYear);

        c.set(Calendar.MONTH, mMonth);

        c.set(Calendar.HOUR_OF_DAY, 20);

        c.set(Calendar.MINUTE, 00);

        NotificationUpdate();

        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationUpdate();
        super.onHandleIntent(intent);
    }

    @Override
    public void onDestroy() {
        //super.onDestroy();
        //stopSelf();
    }

    public void NotificationUpdate() {

        c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);

        mMonth = c.get(Calendar.MONTH);

        mDay = c.get(Calendar.DAY_OF_MONTH);

        c.set(Calendar.YEAR, mYear);

        c.set(Calendar.MONTH, mMonth);

        c.set(Calendar.HOUR_OF_DAY, 20);

        c.set(Calendar.MINUTE, 0);

        c.set(mYear, mMonth, mDay);

        AlarmManager mgrAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        ;
        intentArray = new ArrayList<PendingIntent>();
        ArrayList<String> dataList = dataManager.getAllFrequency();
        Log.e("data list>>>", dataList + ">>>");

        if (dataList.size() > 0) {

            for (int i = 0; i < dataList.size(); i++) {

                if (dataList.get(i).split(" ")[1].equals("Monday"))
                    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                if (dataList.get(i).split(" ")[1].equals("Tuesday"))
                    c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                if (dataList.get(i).split(" ")[1].equals("Wednesday"))
                    c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                if (dataList.get(i).split(" ")[1].equals("Thursday"))
                    c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                if (dataList.get(i).split(" ")[1].equals("Friday"))
                    c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                if (dataList.get(i).split(" ")[1].equals("Saturday"))
                    c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                Log.e("day wise alarm", "true>>");

                Intent intent = new Intent(this,
                        AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent
                        .getBroadcast(this, i, intent, 0);

//                mgrAlarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                        SystemClock.elapsedRealtime(),
//                        1 * 60 * 1000,
//                        pendingIntent);

                mgrAlarm.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
               /* mgrAlarm.set(AlarmManager.RTC_WAKEUP,
                        c.getTimeInMillis(), pendingIntent);*/
                intentArray.add(pendingIntent);
            }
        } else {
            Log.e("everyday alarm", "true>>");
            c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            //c.set(Calendar.YEAR, mYear);

            //c.set(Calendar.MONTH, mMonth);

            c.set(Calendar.HOUR_OF_DAY, 20);

            c.set(Calendar.MINUTE, 0);

            c.set(Calendar.DAY_OF_MONTH, mDay);
            //c.set(mYear, mMonth, mDay);
            //mgrAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(this,
                    AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent
                    .getBroadcast(this, 1000, intent, 0);

//            mgrAlarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                    SystemClock.elapsedRealtime(),
//                    1 * 60 * 1000,
//                    pendingIntent);

            mgrAlarm.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private static String pad(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        } else {
            return "0" + String.valueOf(c);
        }
    }
}
