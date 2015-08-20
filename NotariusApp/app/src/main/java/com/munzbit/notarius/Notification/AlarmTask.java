package com.munzbit.notarius.notification;



import android.app.AlarmManager;

import java.util.ArrayList;
import java.util.Calendar;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.munzbit.notarius.alarm_manager.AlarmReceiver;
import com.munzbit.notarius.datamanager.DataManager;
import com.munzbit.notarius.modal.TimerModal;


/**
 * Set an alarm for the date passed into the constructor
 * When the alarm is raised it will start the NotifyService
 * <p/>
 * This uses the android build in alarm manager *NOTE* if the phone is turned off this alarm will be cancelled
 * <p/>
 * This will run on it's own thread.
 */
public class AlarmTask implements Runnable {
    // The date selected for the alarm
    //private final Calendar date;
    // The android system alarm manager
    public AlarmManager am = null;
    // Your context to retrieve the alarm manager from
    private final Context context;

    private DataManager dataManager;

    private Calendar calendar;

    private int mDay;

    public AlarmTask(Context context) {
        this.context = context;
        dataManager = new DataManager(context);
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

    }

    @Override
    public void run() {
        // Request to start are service when the alarm date is upon us
        // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH);

        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year, month, mDay);

        calendar.set(Calendar.HOUR_OF_DAY, 20);

        NotificationUpdate();

//        ArrayList<PendingIntent> pendingIntents = new ArrayList<>();
//
//        //for (int i = 0; i < frequencyList.size(); i++) {
//
//        Intent intent = new Intent(context, NotifyService.class);
//
//        intent.putExtra(NotifyService.INTENT_NOTIFY, true);
//
//        PendingIntent pendingIntent = PendingIntent.getService(context, 1000, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
//        //am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pendingIntent);
//
//        pendingIntents.add(pendingIntent);

    }

    public void NotificationUpdate() {

        //AlarmManager mgrAlarm = null;

        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();

        ArrayList<TimerModal> dataList = dataManager.getAllFrequency();

        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH);

        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year, month, mDay);

        calendar.set(Calendar.HOUR_OF_DAY, 20);
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {

                if (dataList.get(i).getTimerType().equals("Monday"))
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                else if (dataList.get(i).getTimerType().equals("Tuesday"))
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                else if (dataList.get(i).getTimerType().equals("Wednesday"))
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                else if (dataList.get(i).getTimerType().equals("Thursday"))
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                else if (dataList.get(i).getTimerType().equals("Friday"))
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                else if (dataList.get(i).getTimerType().equals("Saturday"))
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                Log.e("day wise alarm", "true>>"+dataList.get(i));
                //mgrAlarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(context,
                        AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent
                        .getBroadcast(context, i, intent, PendingIntent.FLAG_ONE_SHOT);
                am.set(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(), pendingIntent);
                intentArray.add(pendingIntent);
            }

        } else {
            Log.e("everyday alarm","true>>");
            calendar = Calendar.getInstance();

            int yr = calendar.get(Calendar.YEAR);

            int mon = calendar.get(Calendar.MONTH);

            mDay = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.set(yr, mon, mDay);

            calendar.set(Calendar.HOUR_OF_DAY, 20);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            calendar.set(Calendar.DAY_OF_MONTH, mDay);

            //am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context,
                    AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent
                    .getBroadcast(context, 1000, intent, PendingIntent.FLAG_ONE_SHOT);

            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1*60*1000, pendingIntent);
           // mgrAlarm.cancel(pendingIntent);
        }
    }
}