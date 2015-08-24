package com.munzbit.notarius.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.munzbit.notarius.R;
import com.munzbit.notarius.alarm_Manager_24.AlarmDBHelper;
import com.munzbit.notarius.alarm_Manager_24.AlarmManagerHelper;
import com.munzbit.notarius.alarm_Manager_24.AlarmModel;
import com.munzbit.notarius.alarm_manager.AlarmService;
import com.munzbit.notarius.datamanager.DataManager;
import com.munzbit.notarius.datamanager.Database;
import com.munzbit.notarius.datamanager.SharedPrefrnceNotarius;
import com.munzbit.notarius.modal.TimerModal;
import com.munzbit.notarius.notification.ScheduleClient;
import com.munzbit.notarius.utility.Methods;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Ratufa.Manish on 8/6/2015.
 */
public class NewSettingsActivity extends FragmentActivity implements View.OnClickListener {

    private TextView frequencyTv;

    private CheckBox mondayCheckbox;

    private CheckBox tuesdayCheckbox;

    private CheckBox wednesdayCheckbox;

    private CheckBox thursdayCheckbox;

    private CheckBox fridayCheckbox;

    private CheckBox saturdayCheckbox;

    private CheckBox sundayCheckbox;

    private CheckBox neverCheckbox;

    public int day;

    public static int hour, min;

    public static TextView timeTextView;

    private DataManager dataManager;

    private Dialog dialog;

    private AlarmModel alarmDetails;

    public static int checkBoxCounter = 0;

    private AlarmDBHelper alarmDBHelper;

    private String repeatDaysString = "", monStr = "", tueStr = "", wedStr = "", thurs = "", fri = "", sat = "", sun = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        dataManager = new DataManager(this);
        alarmDBHelper = new AlarmDBHelper(this);

        if (SharedPrefrnceNotarius.getSharedPrefData(this, "alarm_time") == null) {
            SharedPrefrnceNotarius.setDataInSharedPrefrence(this, "am_pm", "PM");
            SharedPrefrnceNotarius.setDataInSharedPrefrence(this, "alarm_time", Methods.pad(20) + ":" + Methods.pad(0));
        }

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_box);
        mondayCheckbox = (CheckBox) dialog.findViewById(R.id.mondayCheckbox);
        tuesdayCheckbox = (CheckBox) dialog.findViewById(R.id.tuesdayCheckbox);
        wednesdayCheckbox = (CheckBox) dialog.findViewById(R.id.wednesdayCheckbox);
        thursdayCheckbox = (CheckBox) dialog.findViewById(R.id.thursdayCheckbox);
        fridayCheckbox = (CheckBox) dialog.findViewById(R.id.fridayCheckbox);
        saturdayCheckbox = (CheckBox) dialog.findViewById(R.id.saturdayCheckbox);
        sundayCheckbox = (CheckBox) dialog.findViewById(R.id.sundayCheckbox);
        neverCheckbox = (CheckBox) dialog.findViewById(R.id.repeatCheckbox);

        frequencyTv = (TextView) findViewById(R.id.frequencySpinner);

        timeTextView = (TextView) findViewById(R.id.timeTv);

        timeTextView.setText(SharedPrefrnceNotarius.getSharedPrefData(this, "alarm_time") + " " + SharedPrefrnceNotarius.getSharedPrefData(this, "am_pm"));

        frequencyTv.setOnClickListener(this);
        timeTextView.setOnClickListener(this);


        updateCheckBoxes();

        mondayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    NewSettingsActivity.checkBoxCounter++;
                    monStr = "Mon";
                }
                if (!isChecked) {
                    monStr = "";
                    NewSettingsActivity.checkBoxCounter--;
                }
            }
        });

        tuesdayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    NewSettingsActivity.checkBoxCounter++;
                    tueStr = "Tue";
                }
                if (!isChecked) {
                    tueStr = "";
                    NewSettingsActivity.checkBoxCounter--;
                }

            }
        });

        wednesdayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    wedStr = "Wed";
                    NewSettingsActivity.checkBoxCounter++;
                }
                if (!isChecked) {
                    wedStr = "";
                    NewSettingsActivity.checkBoxCounter--;
                }
            }
        });

        thursdayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    thurs = "Thu";
                    NewSettingsActivity.checkBoxCounter++;
                }
                if (!isChecked) {
                    thurs = "";
                    NewSettingsActivity.checkBoxCounter--;
                }
            }
        });

        fridayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    fri = "Fri";
                    NewSettingsActivity.checkBoxCounter++;
                }
                if (!isChecked) {
                    fri = "";
                    NewSettingsActivity.checkBoxCounter--;
                }
            }
        });

        saturdayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    sat = "Sat";
                    NewSettingsActivity.checkBoxCounter++;
                }
                if (!isChecked) {
                    sat = "";
                    NewSettingsActivity.checkBoxCounter--;
                }
            }
        });

        sundayCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    sun = "Sun";
                    NewSettingsActivity.checkBoxCounter++;
                }
                if (!isChecked) {
                    sun = "";
                    NewSettingsActivity.checkBoxCounter--;
                }
            }
        });
    }

    public void updateCheckBoxes() {
        long id = 0;

        if (SharedPrefrnceNotarius.getIntSharedPrefData(this, "check_box_count") != 0)
            NewSettingsActivity.checkBoxCounter = SharedPrefrnceNotarius.getIntSharedPrefData(this, "check_box_count");

        if (getIntent().getExtras() != null)
            id = getIntent().getExtras().getLong("id");

        if (id == -1) {
            alarmDetails = new AlarmModel();
            alarmDetails.timeMinute = 00;
            alarmDetails.timeHour = 20;
            alarmDetails.name = "demo";

            alarmDetails.setRepeatingDay(AlarmModel.SUNDAY, true);
            alarmDetails.setRepeatingDay(AlarmModel.MONDAY, true);
            alarmDetails.setRepeatingDay(AlarmModel.TUESDAY, true);
            alarmDetails.setRepeatingDay(AlarmModel.WEDNESDAY, true);
            alarmDetails.setRepeatingDay(AlarmModel.THURSDAY, true);
            alarmDetails.setRepeatingDay(AlarmModel.FRIDAY, true);
            alarmDetails.setRepeatingDay(AlarmModel.SATURDAY, true);

            alarmDetails.isEnabled = true;
            alarmDetails.repeatWeekly = true;

        } else {
            alarmDetails = alarmDBHelper.getAlarm(0);
            timeTextView.setText(alarmDetails.timeMinute + ":" + alarmDetails.timeHour);

            neverCheckbox.setChecked(alarmDetails.repeatWeekly);
            sundayCheckbox.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SUNDAY));
            mondayCheckbox.setChecked(alarmDetails.getRepeatingDay(AlarmModel.MONDAY));
            tuesdayCheckbox.setChecked(alarmDetails.getRepeatingDay(AlarmModel.TUESDAY));
            wednesdayCheckbox.setChecked(alarmDetails.getRepeatingDay(AlarmModel.WEDNESDAY));
            thursdayCheckbox.setChecked(alarmDetails.getRepeatingDay(AlarmModel.THURSDAY));
            fridayCheckbox.setChecked(alarmDetails.getRepeatingDay(AlarmModel.FRIDAY));
            saturdayCheckbox.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SATURDAY));

            if (SharedPrefrnceNotarius.getIntSharedPrefData(this, "check_box_count") == 0) {
                alarmDetails.isEnabled = false;
                alarmDetails.repeatWeekly = false;
            } else {
                alarmDetails.isEnabled = true;
                alarmDetails.repeatWeekly = true;
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

        dialog.show();

        Button okBtn = (Button) dialog.findViewById(R.id.dismissBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                repeatDaysString = "";
                if (!monStr.equals("")) {
                    repeatDaysString = repeatDaysString.concat(monStr).concat(",");
                }
                if (!tueStr.equals("")) {
                    repeatDaysString = repeatDaysString.concat(tueStr).concat(",");
                }
                if (!wedStr.equals("")) {
                    repeatDaysString = repeatDaysString.concat(wedStr).concat(",");
                }
                if (!thurs.equals("")) {
                    repeatDaysString = repeatDaysString.concat(thurs).concat(",");
                }
                if (!fri.equals("")) {
                    repeatDaysString = repeatDaysString.concat(fri).concat(",");
                }
                if (!sat.equals("")) {
                    repeatDaysString = repeatDaysString.concat(sat).concat(",");
                }
                if (!sun.equals("")) {
                    repeatDaysString = repeatDaysString.concat(sun).concat(",");
                }

                if (NewSettingsActivity.checkBoxCounter < 7) {

                    frequencyTv.setText(repeatDaysString);

                } else if (NewSettingsActivity.checkBoxCounter == 7) {

                    frequencyTv.setText("Everyday");

                } else {
                    frequencyTv.setText("Never");
                }

                updateModelFromLayout();

                AlarmManagerHelper.cancelAlarms(NewSettingsActivity.this);

                if (alarmDetails.id < 0) {
                    alarmDBHelper.createAlarm(alarmDetails);
                } else {
                    alarmDBHelper.updateAlarm(alarmDetails);
                }

                AlarmManagerHelper.setAlarms(NewSettingsActivity.this);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

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

            NewSettingsActivity.hour = hourOfDay;
            NewSettingsActivity.min = minute;

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

    private void updateModelFromLayout() {

        SharedPrefrnceNotarius.setIntInSharedPrefs(this, "check_box_count", NewSettingsActivity.checkBoxCounter);

        alarmDetails.timeMinute = NewSettingsActivity.hour;
        alarmDetails.timeHour = NewSettingsActivity.min;
        alarmDetails.name = "demo";

        alarmDetails.setRepeatingDay(AlarmModel.SUNDAY, sundayCheckbox.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.MONDAY, mondayCheckbox.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.TUESDAY, tuesdayCheckbox.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.WEDNESDAY, wednesdayCheckbox.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.THURSDAY, thursdayCheckbox.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.FRIDAY, fridayCheckbox.isChecked());
        alarmDetails.setRepeatingDay(AlarmModel.SATURDAY, saturdayCheckbox.isChecked());

        if (SharedPrefrnceNotarius.getIntSharedPrefData(this, "check_box_count") == 0) {
            alarmDetails.isEnabled = false;
            alarmDetails.repeatWeekly = false;
            List<AlarmModel> alarmModels = alarmDBHelper.getAlarms();
            if (alarmModels != null)
                alarmDBHelper.deleteAlarm(0);
            //Set the alarms
        } else {
            alarmDetails.isEnabled = true;
            alarmDetails.repeatWeekly = true;
        }

        AlarmManagerHelper.setAlarms(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
