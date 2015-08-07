package com.munzbit.notarius.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

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

    private ScheduleClient scheduleClient;

    private Calendar calendar;

    private int year, month, day;

    private List<String> frequencyList;

    public static TextView timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        frequencyTimes = new ArrayList<TimerModal>();

        toggleNotification = (ToggleButton) findViewById(R.id.toggleButton);

        frequencyTv = (TextView) findViewById(R.id.frequencySpinner);

        timeTextView = (TextView) findViewById(R.id.timeTv);

        frequencyTv.setOnClickListener(this);
        timeTextView.setOnClickListener(this);

        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();


        if(SharedPrefrnceNotarius.getBoolSharedPrefData(this,"notification_on")){
            toggleNotification.setChecked(true);
        }else{
            toggleNotification.setChecked(false);
        }

        if (SharedPrefrnceNotarius.ReadArraylist(this, "frequency_list") != null) {

            if (SharedPrefrnceNotarius.ReadArraylist(this, "frequency_list").size() > 0) {

                repeatTime = "";
                frequencyList = SharedPrefrnceNotarius.ReadArraylist(this, "frequency_list");

                for (int i = 0; i < frequencyList.size(); i++) {

                    repeatTime = repeatTime.concat(frequencyList.get(i));

                    frequencyTv.setText(repeatTime);

                }
            }

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


        toggleNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    SharedPrefrnceNotarius.setBoolInSharedPrefs(SettingsActivity.this,"notification_on",true);
                    scheduleClient.setAlarmForNotification();
                }

                 if (!isChecked) {
                     SharedPrefrnceNotarius.setBoolInSharedPrefs(SettingsActivity.this,"notification_on",false);
                     scheduleClient.doUnbindService();
                   }

                   }

              }

        );

    }

    public void getSelectedItems(ArrayList<TimerModal> data) {

        repeatTime = "";

        frequencyList = new ArrayList<String>();

        for (int i = 0; i < data.size(); i++) {

            if (data.get(i).getIsSelected()) {

                repeatTime = repeatTime.concat(data.get(i).getTimerType().split(" ")[1]).concat(",");

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
            case R.id.timeTv:
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
                break;
        }
    }

    public void showFrequencyPopup() {

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.popup_box);

        dialog.setCancelable(false);

        ListView listView = (ListView) dialog.findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog.dismiss();
                if (position == 0) {
                    repeatTime = "Everyday";
                }
            }
        });

        listView.setAdapter(new PopupAdapter(this, frequencyTimes));

        Button okBtn = (Button) dialog.findViewById(R.id.dismissBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                frequencyTv.setText(repeatTime);

            }
        });

        dialog.show();

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
            timeTextView.setText(hourOfDay+":"+minute);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        scheduleClient.doUnbindService();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
