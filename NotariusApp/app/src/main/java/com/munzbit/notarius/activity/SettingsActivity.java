package com.munzbit.notarius.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.munzbit.notarius.alarm_manager.AlarmService;
import com.munzbit.notarius.datamanager.DataManager;
import com.munzbit.notarius.datamanager.SharedPrefrnceNotarius;
import com.munzbit.notarius.notification.ScheduleClient;
import com.munzbit.notarius.R;
import com.munzbit.notarius.adapter.PopupAdapter;
import com.munzbit.notarius.modal.TimerModal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Created by Ratufa.Manish on 8/6/2015.
 */
public class SettingsActivity extends FragmentActivity implements View.OnClickListener {

    private ToggleButton toggleNotification;

    private TextView frequencyTv;

    private ArrayList<TimerModal> frequencyTimes;

    private String repeatTime = "Everyday";

    //private ScheduleClient scheduleClient;

    private Calendar calendar;

    private int year, month, day;

    private List<String> frequencyList;

    public static TextView timeTextView;

    public static TextView frequencyTv2;

    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        frequencyTimes = new ArrayList<TimerModal>();
        dataManager = new DataManager(this);

        toggleNotification = (ToggleButton) findViewById(R.id.toggleButton);

        frequencyTv = (TextView) findViewById(R.id.frequencySpinner);

        timeTextView = (TextView) findViewById(R.id.timeTv);

        frequencyTv2 = (TextView) findViewById(R.id.frequencyText);

        frequencyTv.setOnClickListener(this);
        timeTextView.setOnClickListener(this);
        frequencyTv2.setOnClickListener(this);

        //scheduleClient = new ScheduleClient(this);

        //scheduleClient.doBindService();


        if (SharedPrefrnceNotarius.getBoolSharedPrefData(this, "notification_on")) {
            toggleNotification.setChecked(true);
        } else {
            toggleNotification.setChecked(false);
        }

        TimerModal timerModal1 = new TimerModal();
        timerModal1.setIsSelected(false);
        timerModal1.setTimerType("Repeat");

        TimerModal timerModal2 = new TimerModal();
        timerModal2.setIsSelected(false);
        timerModal2.setTimerType("Every Monday");

        TimerModal timerModal3 = new TimerModal();
        timerModal3.setIsSelected(false);
        timerModal3.setTimerType("Every Tuesday");

        TimerModal timerModal4 = new TimerModal();
        timerModal4.setIsSelected(false);
        timerModal4.setTimerType("Every Wednesday");

        TimerModal timerModal5 = new TimerModal();
        timerModal5.setIsSelected(false);
        timerModal5.setTimerType("Every Thursday");

        TimerModal timerModal6 = new TimerModal();
        timerModal6.setIsSelected(false);
        timerModal6.setTimerType("Every Friday");

        TimerModal timerModal7 = new TimerModal();
        timerModal7.setIsSelected(false);
        timerModal7.setTimerType("Every Saturday");

        frequencyTimes.add(timerModal1);
        frequencyTimes.add(timerModal2);
        frequencyTimes.add(timerModal3);
        frequencyTimes.add(timerModal4);
        frequencyTimes.add(timerModal5);
        frequencyTimes.add(timerModal6);
        frequencyTimes.add(timerModal7);

        repeatTime = "";

        frequencyList = new ArrayList<>();

        List<String> tempList = new ArrayList<>();

        tempList = dataManager.getAllFrequency();

        if (tempList.size() > 0) {

            for (int i = 0; i < tempList.size(); i++) {

                if (tempList.get(i).split(" ")[1].equals("Monday")) {

                    if (tempList.size() % 2 == 0)
                        repeatTime = repeatTime.concat("Mon").concat(",");
                    else
                        repeatTime = repeatTime.concat("Mon");

                }
                if (tempList.get(i).split(" ")[1].equals("Tuesday")) {
                    if (tempList.size() % 2 == 0)
                        repeatTime = repeatTime.concat("Tue").concat(",");
                    else
                        repeatTime = repeatTime.concat("Tue");
                }
                if (tempList.get(i).split(" ")[1].equals("Wednesday")) {
                    if (tempList.size() % 2 == 0)
                        repeatTime = repeatTime.concat("Wed").concat(",");
                    else
                        repeatTime = repeatTime.concat("Wed");
                }
                if (tempList.get(i).split(" ")[1].equals("Thursday")) {
                    if (tempList.size() % 2 == 0)
                        repeatTime = repeatTime.concat("Thu").concat(",");
                    else
                        repeatTime = repeatTime.concat("Thu");
                }
                if (tempList.get(i).split(" ")[1].equals("Friday")) {
                    if (tempList.size() % 2 == 0)
                        repeatTime = repeatTime.concat("Fri").concat(",");
                    else
                        repeatTime = repeatTime.concat("Fri");
                }
                if (tempList.get(i).split(" ")[1].equals("Saturday")) {
                    if (tempList.size() % 2 == 0)
                        repeatTime = repeatTime.concat("Sat").concat(",");
                    else
                        repeatTime = repeatTime.concat("Sat");
                }

                frequencyTv2.setVisibility(View.VISIBLE);
                frequencyTv.setVisibility(View.INVISIBLE);
                frequencyTv2.setText(repeatTime);
            }

        }else{
            stopService(new Intent(SettingsActivity.this, AlarmService.class));
        }


        toggleNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

               @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                 if (isChecked) {
                   SharedPrefrnceNotarius.setBoolInSharedPrefs(SettingsActivity.this, "notification_on", true);
                   //scheduleClient.setAlarmForNotification();
                     startService(new Intent(SettingsActivity.this, AlarmService.class));
                   }

                   if (!isChecked) {
                   SharedPrefrnceNotarius.setBoolInSharedPrefs(SettingsActivity.this, "notification_on", false);
                   //scheduleClient.doUnbindService();
                       stopService(new Intent(SettingsActivity.this, AlarmService.class));
                   }

                 }

            }

        );

    }

    public void getSelectedItems(ArrayList<TimerModal> data) {

        repeatTime = "";

        frequencyList = new ArrayList<String>();

        frequencyList = dataManager.getAllFrequency();

        Log.e("data store", frequencyList.toString() + "");

        for (int i = 0; i < data.size(); i++) {

            if (data.get(i).getIsSelected()) {

                if (data.get(i).getTimerType().split(" ")[1].equals("Monday")) {
                    if (data.size() % 2 == 0)
                        repeatTime = repeatTime.concat("Mon").concat(",");
                    else
                        repeatTime = repeatTime.concat("Mon");

                }
                if (data.get(i).getTimerType().split(" ")[1].equals("Tuesday")) {
                    if (data.size() % 2 == 0)
                        repeatTime = repeatTime.concat("Tue").concat(",");
                    else
                        repeatTime = repeatTime.concat("Tue");
                }
                if (data.get(i).getTimerType().split(" ")[1].equals("Wednesday")) {
                    if (data.size() % 2 == 0)
                        repeatTime = repeatTime.concat("Wed").concat(",");
                    else
                        repeatTime = repeatTime.concat("Wed");
                }
                if (data.get(i).getTimerType().split(" ")[1].equals("Thursday")) {
                    if (data.size() % 2 == 0)
                        repeatTime = repeatTime.concat("Thu").concat(",");
                    else

                        repeatTime = repeatTime.concat("Thu");
                }
                if (data.get(i).getTimerType().split(" ")[1].equals("Friday")) {
                    if (data.size() % 2 == 0)
                        repeatTime = repeatTime.concat("Fri").concat(",");
                    else
                        repeatTime = repeatTime.concat("Fri");
                }
                if (data.get(i).getTimerType().split(" ")[1].equals("Saturday")) {
                    if (data.size() % 2 == 0)
                        repeatTime = repeatTime.concat("Sat").concat(",");
                    else
                        repeatTime = repeatTime.concat("Sat");
                }

                frequencyList.add(repeatTime);

            }
        }

        SharedPrefrnceNotarius.writeArraylist(this, "frequency_list", frequencyList);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frequencySpinner:
                showFrequencyPopup();
                break;
            case R.id.frequencyText:
                showFrequencyPopup();
                break;
            case R.id.timeTv:
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
                break;
        }
    }

    public void showFrequencyPopup() {

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.popup_box);

        ListView listView = (ListView) dialog.findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog.dismiss();

                stopService(new Intent(SettingsActivity.this, AlarmService.class));

                startService(new Intent(SettingsActivity.this, AlarmService.class));

                if (position == 0) {
                    frequencyList = new ArrayList<String>();
                    repeatTime = "Everyday";
                    frequencyTv.setVisibility(View.VISIBLE);
                    frequencyTv.setText(repeatTime);
                    frequencyTv2.setVisibility(View.GONE);
                    dataManager.removeAllFrequency();
                }
            }
        });

        listView.setAdapter(new PopupAdapter(this, frequencyTimes));

        Button okBtn = (Button) dialog.findViewById(R.id.dismissBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(frequencyList.size() > 0){
                    frequencyTv.setVisibility(View.INVISIBLE);
                    frequencyTv2.setVisibility(View.VISIBLE);
                    frequencyTv2.setText(repeatTime);
                }else {
                    frequencyTv.setVisibility(View.VISIBLE);
                    frequencyTv.setText(repeatTime);
                    frequencyTv2.setVisibility(View.GONE);
                }

            }
        });

        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPrefrnceNotarius.getBoolSharedPrefData(this, "notification_on")) {
            toggleNotification.setChecked(true);
            //stopService(new Intent(SettingsActivity.this, AlarmService.class));

            startService(new Intent(SettingsActivity.this, AlarmService.class));
        } else {
           // stopService(new Intent(SettingsActivity.this, AlarmService.class));
            toggleNotification.setChecked(false);
        }

    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            timeTextView.setText(hourOfDay + ":" + minute);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //scheduleClient.doUnbindService();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
