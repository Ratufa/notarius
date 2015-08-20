package com.munzbit.notarius.alarm_manager;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.util.Log;

import com.munzbit.notarius.datamanager.DataManager;
import com.munzbit.notarius.datamanager.SharedPrefrnceNotarius;
import com.munzbit.notarius.modal.TimerModal;

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

    public PendingIntent pendingIntent;

    private  AlarmManager mgrAlarm;

    public static ArrayList<PendingIntent> intentArray;

    private boolean intentValue = false;

    private String timeString;

    public AlarmService() {
        super("");
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
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        super.onHandleIntent(intent);
        NotificationUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        c = Calendar.getInstance();
//
//        timeString = SharedPrefrnceNotarius.getSharedPrefData(this, "alarm_time");
//
//        mYear = c.get(Calendar.YEAR);
//        mMonth = c.get(Calendar.MONTH);
//        mDay = c.get(Calendar.DAY_OF_MONTH);
//
//        c.set(Calendar.YEAR, mYear);
//
//        c.set(Calendar.MONTH, mMonth);
//
//        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeString.split(":")[0]));
//
//        c.set(Calendar.MINUTE, Integer.parseInt(timeString.split(":")[1]));

        NotificationUpdate();

        return START_STICKY;
    }

   /* @Override
    protected void onHandleIntent(Intent intent) {
        NotificationUpdate();
        super.onHandleIntent(intent);
    }*/


    @Override
    public boolean stopService(Intent name) {
        stopSelf();
        return super.stopService(name);
    }

    public void NotificationUpdate() {

        c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);

        mMonth = c.get(Calendar.MONTH);

        mDay = c.get(Calendar.DAY_OF_MONTH);

        c.set(Calendar.YEAR, mYear);

        c.set(Calendar.MONTH, mMonth);

        timeString = SharedPrefrnceNotarius.getSharedPrefData(this,"alarm_time");

        c.set(Calendar.HOUR_OF_DAY, 20);

        c.set(Calendar.MINUTE, 00);

        intentArray = new ArrayList<PendingIntent>();

        ArrayList<TimerModal> dataList = dataManager.getAllFrequency();

        ArrayList<TimerModal> tempData = new ArrayList<>();

        for(int i = 0;i<dataList.size();i++){

            if(dataList.get(i).getIsSelected())
                tempData.add(dataList.get(i));
        }

        if (tempData.size() > 0) {

            Calendar now = null;

                for (int i = 0; i < tempData.size(); i++) {

                    Intent intent = new Intent(this, AlarmReceiver.class);

                    pendingIntent = PendingIntent
                            .getBroadcast(this, i*100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    if (tempData.get(i).getTimerType().equals("Monday"))
                        //c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                       forday(2,now);
                    if (tempData.get(i).getTimerType().equals("Tuesday"))
                        //c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                        forday(3,now);
                    if (tempData.get(i).getTimerType().equals("Wednesday"))
                        //c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                        forday(4,now);
                    if (tempData.get(i).getTimerType().equals("Thursday"))
                        //c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                        forday(5,now);
                    if (tempData.get(i).getTimerType().equals("Friday"))
                        //c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                        forday(6,now);
                    if (tempData.get(i).getTimerType().equals("Saturday"))
                        //c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                        forday(7,now);
                    if (tempData.get(i).getTimerType().equals("Sunday"))
                        //c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                        forday(1,now);

                    intentArray.add(pendingIntent);

            }
        }
//         else {
//            Log.e("everyday alarm", "true>>");
//            if (tempData.size() == 7) {
//                mgrAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);
//                c = Calendar.getInstance();
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                c.set(Calendar.YEAR, mYear);
//
//                c.set(Calendar.MONTH, mMonth);
//
//                timeString = SharedPrefrnceNotarius.getSharedPrefData(this,"alarm_time");
//
//                c.set(Calendar.HOUR_OF_DAY, 20);
//
//                c.set(Calendar.MINUTE, 00);
//
//                c.set(Calendar.DAY_OF_MONTH, mDay);
//
//                Intent intent = new Intent(this,
//                            AlarmReceiver.class);
//                pendingIntent = PendingIntent
//                            .getBroadcast(this, 1000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                mgrAlarm.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), DateUtils.DAY_IN_MILLIS, pendingIntent);
//
//            }
//        }
    }

    public void forday(int week, Calendar cal) {

       // if(!c.before(cal) && c.after(cal) && c.equals(cal)) {
        Log.e("day wise alarm", "true>>");
            mgrAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);

            c.setTimeInMillis(System.currentTimeMillis());
            c.set(Calendar.DAY_OF_WEEK,week);
            c.set(Calendar.HOUR_OF_DAY, 20);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            mgrAlarm.setExact(AlarmManager.RTC_WAKEUP,
//                    c.getTimeInMillis(), pendingIntent);
//        }else{
            mgrAlarm.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    c.getTimeInMillis(), (DateUtils.DAY_IN_MILLIS)*7, pendingIntent);
        //}

    }
}
