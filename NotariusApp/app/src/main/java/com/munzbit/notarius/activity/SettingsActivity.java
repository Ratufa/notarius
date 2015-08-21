package com.munzbit.notarius.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.text.format.Time;
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
import com.munzbit.notarius.utility.Methods;

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

    // private ArrayList<TimerModal> frequencyTimes;

    private String repeatTime = "Never";

    private ScheduleClient scheduleClient;

    private Calendar calendar;

    private int year, month, day;

    private List<TimerModal> frequencyList;

    public static TextView timeTextView;

    public static TextView frequencyTv2;

    private DataManager dataManager;

    private AlarmService alarmService;

    private ListView listView;

    private Dialog dialog;

    private ArrayList<TimerModal> tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        dataManager = new DataManager(this);
        alarmService = new AlarmService();

        if (SharedPrefrnceNotarius.getSharedPrefData(this, "alarm_time") == null) {
            SharedPrefrnceNotarius.setDataInSharedPrefrence(this, "am_pm", "PM");
            SharedPrefrnceNotarius.setDataInSharedPrefrence(this, "alarm_time", Methods.pad(20) + ":" + Methods.pad(0));
        }

        dialog = new Dialog(this);

        dialog.setContentView(R.layout.popup_box);

        listView = (ListView) dialog.findViewById(R.id.listView);

        frequencyTv = (TextView) findViewById(R.id.frequencySpinner);

        timeTextView = (TextView) findViewById(R.id.timeTv);

        frequencyTv2 = (TextView) findViewById(R.id.frequencyText);

        timeTextView.setText(SharedPrefrnceNotarius.getSharedPrefData(this, "alarm_time") + " " + SharedPrefrnceNotarius.getSharedPrefData(this, "am_pm"));

        frequencyTv.setOnClickListener(this);
        timeTextView.setOnClickListener(this);
        frequencyTv2.setOnClickListener(this);

        repeatTime = "";
    }

    public void getSelectedItems(ArrayList<TimerModal> data) {

        repeatTime = "";

        frequencyList = new ArrayList<TimerModal>();

        frequencyList = dataManager.getAllFrequency();

        //Log.e("data store", frequencyList.toString() + "");

        for (int i = 0; i < frequencyList.size(); i++) {

            int j = i;
            if (j % 2 != 0) {
                j = j + 1;
            }

            if (data.get(i).getIsSelected()) {

                if (data.get(i).getTimerType().equals("Monday")) {

                    if (j % 2 == 0)
                        repeatTime = repeatTime.concat("Mon").concat(",");
                    else
                        repeatTime = repeatTime.concat("Mon");
                }
                if (data.get(i).getTimerType().equals("Tuesday")) {

                    if (j % 2 == 0)
                        repeatTime = repeatTime.concat("Tue").concat(",");
                    else
                        repeatTime = repeatTime.concat("Tue");

                }
                if (data.get(i).getTimerType().equals("Wednesday")) {

                    if (j % 2 == 0)
                        repeatTime = repeatTime.concat("Wed").concat(",");
                    else
                        repeatTime = repeatTime.concat("Wed");

                }
                if (data.get(i).getTimerType().equals("Thursday")) {

                    if (j % 2 == 0)
                        repeatTime = repeatTime.concat("Thu").concat(",");
                    else
                        repeatTime = repeatTime.concat("Thu");

                }
                if (data.get(i).getTimerType().equals("Friday")) {

                    if (j % 2 == 0)
                        repeatTime = repeatTime.concat("Fri").concat(",");
                    else
                        repeatTime = repeatTime.concat("Fri");

                }
                if (data.get(i).getTimerType().equals("Saturday")) {

                    if (j % 2 == 0)
                        repeatTime = repeatTime.concat("Sat").concat(",");
                    else
                        repeatTime = repeatTime.concat("Sat");
                }
                if (data.get(i).getTimerType().equals("Sunday")) {

                    if (j % 2 == 0)
                        repeatTime = repeatTime.concat("Sun").concat(",");
                    else
                        repeatTime = repeatTime.concat("Sun");
                }

            }
        }


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

        dialog.setCancelable(false);
        //listView.setAdapter(new PopupAdapter(this, tempList));

        Button okBtn = (Button) dialog.findViewById(R.id.dismissBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                frequencyList = dataManager.getAllFrequency();
                ArrayList<String> tmpList = new ArrayList<String>();

                for (int i = 0; i < frequencyList.size(); i++) {

                    if (frequencyList.get(i).getIsSelected())
                        tmpList.add(frequencyList.get(i).getTimerType());

                }

                if (tmpList.size() > 0) {

                    if (tmpList.size() == 7) {
                        frequencyTv.setVisibility(View.VISIBLE);
                        frequencyTv2.setVisibility(View.GONE);
                        frequencyTv.setText("Everyday");
                    } else {
                        frequencyTv.setVisibility(View.INVISIBLE);
                        frequencyTv2.setVisibility(View.VISIBLE);
                        frequencyTv2.setText(repeatTime);
                    }

                    alarmService.stopSelf();
                    startService(new Intent(SettingsActivity.this, AlarmService.class));
                } else {
                    stopService(new Intent(SettingsActivity.this, AlarmService.class));
                    frequencyTv.setVisibility(View.VISIBLE);
                    frequencyTv.setText("Never");
                    frequencyTv2.setVisibility(View.GONE);
                }

            }
        });

        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        repeatTime = "";
        frequencyList = new ArrayList<>();

        tempList = new ArrayList<TimerModal>();

        tempList = dataManager.getAllFrequency();

        //listView.setAdapter(new PopupAdapter(SettingsActivity.this, tempList));
        ArrayList<String> cloneList = new ArrayList<String>();

        for (int i = 0; i < tempList.size(); i++) {

            if (tempList.get(i).getIsSelected())
                cloneList.add(tempList.get(i).getTimerType());

        }

        if (cloneList.size() > 0) {

            for (int i = 0; i < tempList.size(); i++) {

                int j = i;
                if (j % 2 != 0) {
                    j = j + 1;
                }

                if (tempList.get(i).getIsSelected()) {

                    if (tempList.get(i).getTimerType().equals("Monday")) {

                        if ((j) % 2 == 0)
                            repeatTime = repeatTime.concat("Mon").concat(",");
                        else
                            repeatTime = repeatTime.concat("Mon");

                    }
                    if (tempList.get(i).getTimerType().equals("Tuesday")) {
                        if ((j) % 2 == 0)
                            repeatTime = repeatTime.concat("Tue").concat(",");
                        else
                            repeatTime = repeatTime.concat("Tue");
                    }
                    if (tempList.get(i).getTimerType().equals("Wednesday")) {
                        if ((j) % 2 == 0)
                            repeatTime = repeatTime.concat("Wed").concat(",");
                        else
                            repeatTime = repeatTime.concat("Wed");
                    }
                    if (tempList.get(i).getTimerType().equals("Thursday")) {
                        if ((j) % 2 == 0)
                            repeatTime = repeatTime.concat("Thu").concat(",");
                        else
                            repeatTime = repeatTime.concat("Thu");
                    }
                    if (tempList.get(i).getTimerType().equals("Friday")) {
                        if ((j) % 2 == 0)
                            repeatTime = repeatTime.concat("Fri").concat(",");
                        else
                            repeatTime = repeatTime.concat("Fri");
                    }
                    if (tempList.get(i).getTimerType().equals("Saturday")) {
                        if ((j) % 2 == 0)
                            repeatTime = repeatTime.concat("Sat").concat(",");
                        else
                            repeatTime = repeatTime.concat("Sat");
                    }
                    if (tempList.get(i).getTimerType().equals("Sunday")) {
                        if ((j) % 2 == 0)
                            repeatTime = repeatTime.concat("Sun").concat(",");
                        else
                            repeatTime = repeatTime.concat("Sun");
                    }

                }
            }

            if (cloneList.size() == 7) {
                frequencyTv.setVisibility(View.VISIBLE);
                frequencyTv2.setVisibility(View.GONE);
                frequencyTv.setText("Everyday");
            } else {
                frequencyTv.setVisibility(View.INVISIBLE);
                frequencyTv2.setVisibility(View.VISIBLE);
                frequencyTv2.setText(repeatTime);
            }

        } else {

            if (cloneList.size() == 0) {
                frequencyTv.setVisibility(View.VISIBLE);
                frequencyTv.setText("Never");
                frequencyTv2.setVisibility(View.GONE);
                stopService(new Intent(SettingsActivity.this, AlarmService.class));
            }
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
            Calendar datetime = Calendar.getInstance();
            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            datetime.set(Calendar.MINUTE, minute);

            String AM_PM = null;
            if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                AM_PM = "AM";
            else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                AM_PM = "PM";

            timeTextView.setText(Methods.pad(hourOfDay) + ":" + Methods.pad(minute) + " " + AM_PM);

            getActivity().stopService(new Intent(getActivity(), AlarmService.class));

            getActivity().startService(new Intent(getActivity(), AlarmService.class));

            SharedPrefrnceNotarius.setDataInSharedPrefrence(getActivity(), "alarm_time", Methods.pad(hourOfDay) + ":" + Methods.pad(minute));

            SharedPrefrnceNotarius.setDataInSharedPrefrence(getActivity(), "am_pm", AM_PM);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
