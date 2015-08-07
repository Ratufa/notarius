package com.munzbit.notarius.notification;

/**
 * Created by Ratufa.Manish on 8/6/2015.
 */

import android.app.Activity;
import android.app.AlarmManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.munzbit.notarius.activity.SettingsActivity;
import com.munzbit.notarius.datamanager.SharedPrefrnceNotarius;


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
    private final AlarmManager am;
    // Your context to retrieve the alarm manager from
    private final Context context;

    public AlarmTask(Context context) {
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

    }

    @Override
    public void run() {
        // Request to start are service when the alarm date is upon us
        // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH);

        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year, month, day);

        calendar.set(Calendar.HOUR_OF_DAY, 20);

        calendar.set(Calendar.MINUTE, 0);

        calendar.set(Calendar.SECOND, 0);

        List<String> frequencyList = SharedPrefrnceNotarius.ReadArraylist(context, "frequency_list");

        ArrayList<PendingIntent> pendingIntents = new ArrayList<>();

        //for (int i = 0; i < frequencyList.size(); i++) {

            Intent intent = new Intent(context, NotifyService.class);

            intent.putExtra(NotifyService.INTENT_NOTIFY, true);

            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

            // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            pendingIntents.add(pendingIntent);
        //}


    }
}