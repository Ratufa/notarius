package com.munzbit.notarius.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.munzbit.notarius.R;
import com.munzbit.notarius.alarm_manager.AlarmDBHelper;
import com.munzbit.notarius.alarm_manager.AlarmManagerHelper;
import com.munzbit.notarius.alarm_manager.AlarmModel;
import com.munzbit.notarius.data_manager.SharedPrefrnceNotarius;
import com.munzbit.notarius.utility.Methods;

import java.util.Calendar;


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

    //private CheckBox neverCheckbox;

    public int day;

    public static int hour = 20, min = 00;

    public static TextView timeTextView;

    //private DataManager dataManager;

    private Dialog dialog;

    private AlarmModel alarmDetails;

    public static int checkBoxCounter = 0;

    private AlarmDBHelper alarmDBHelper;

    private String repeatDaysString = "", monStr = "Mon", tueStr = "Tue", wedStr = "Wed", thurs = "Thu", fri = "Fri", sat = "Sat", sun = "Sun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        //dataManager = new DataManager(this);
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
        //neverCheckbox = (CheckBox) dialog.findViewById(R.id.repeatCheckbox);

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
                Log.e("mondayCheckbox>>>",monStr);
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
                Log.e("tuesdayCheckbox>>>",tueStr);
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
                Log.e("wednesdayCheckbox>>",wedStr);
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
                Log.e("thursdayCheckbox>>>>",thurs);
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

                Log.e("fridayCheckbox>>>>>>",fri);
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

                Log.e("saturdayCheckbox>>>>>",sat);
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

                Log.e("sundayCheckbox>>>>>>>",sun);
            }
        });
    }

    public void updateCheckBoxes() {
        String id = "0";

        if (SharedPrefrnceNotarius.getIntSharedPrefData(this, "check_box_count") != 0)
            NewSettingsActivity.checkBoxCounter = SharedPrefrnceNotarius.getIntSharedPrefData(this, "check_box_count");

        if (getIntent().getExtras() != null)
            id = getIntent().getStringExtra("_id");

        Log.e("alarm id>>>>>>>", id + "");

        if (id.equals("-1")) {
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

            NewSettingsActivity.checkBoxCounter = 7;

            alarmDetails.isEnabled = true;
            alarmDetails.repeatWeekly = true;

        } else {

            alarmDetails = alarmDBHelper.getAlarm();
            //neverCheckbox.setChecked(alarmDetails.repeatWeekly);
            NewSettingsActivity.checkBoxCounter = SharedPrefrnceNotarius.getIntSharedPrefData(this, "check_box_count");

            if (NewSettingsActivity.checkBoxCounter == 0) {
                alarmDetails.isEnabled = false;
                alarmDetails.repeatWeekly = false;
            } else {
                alarmDetails.isEnabled = true;
                alarmDetails.repeatWeekly = true;
            }
        }

        if (alarmDetails.id < 0) {
            alarmDBHelper.createAlarm(alarmDetails);
            alarmDetails.id=1;
        } else {
            alarmDBHelper.updateAlarm(alarmDetails);
        }

        sundayCheckbox.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SUNDAY));
        mondayCheckbox.setChecked(alarmDetails.getRepeatingDay(AlarmModel.MONDAY));
        tuesdayCheckbox.setChecked(alarmDetails.getRepeatingDay(AlarmModel.TUESDAY));
        wednesdayCheckbox.setChecked(alarmDetails.getRepeatingDay(AlarmModel.WEDNESDAY));
        thursdayCheckbox.setChecked(alarmDetails.getRepeatingDay(AlarmModel.THURSDAY));
        fridayCheckbox.setChecked(alarmDetails.getRepeatingDay(AlarmModel.FRIDAY));
        saturdayCheckbox.setChecked(alarmDetails.getRepeatingDay(AlarmModel.SATURDAY));

        if (NewSettingsActivity.checkBoxCounter == 7) {
            frequencyTv.setText("Everyday");
        } else {
            if(SharedPrefrnceNotarius.getSharedPrefData(this,"repeat_days")!=null){
                repeatDaysString = SharedPrefrnceNotarius.getSharedPrefData(this,"repeat_days");
                frequencyTv.setText(SharedPrefrnceNotarius.getSharedPrefData(this,"repeat_days"));
            }
        }
        AlarmManagerHelper.cancelAlarms(this);
        if(!repeatDaysString.equals("Never"))
        AlarmManagerHelper.setAlarms(this);

        timeTextView.setText(Methods.pad(alarmDetails.timeHour) + ":" + Methods.pad(alarmDetails.timeMinute)+" " + SharedPrefrnceNotarius.getSharedPrefData(this, "am_pm"));

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

        dialog.setCancelable(false);
        dialog.show();

        if (!mondayCheckbox.isChecked()) {
            monStr="";
        }
        if (!tuesdayCheckbox.isChecked()) {
            tueStr="";
        }
        if (!wednesdayCheckbox.isChecked()) {
            wedStr="";
        }
        if (!thursdayCheckbox.isChecked()) {
            thurs="";
        }
        if (!fridayCheckbox.isChecked()) {
            fri="";
        }
        if (!saturdayCheckbox.isChecked()) {
            sat="";
        }
        if (!sundayCheckbox.isChecked()) {
            sun="";
        }

        TextView okBtn = (TextView) dialog.findViewById(R.id.okBtn);

        TextView dissmissBtn = (TextView) dialog.findViewById(R.id.dismissBtn);

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
                if (NewSettingsActivity.checkBoxCounter == 7) {
                    repeatDaysString="Everyday";

                }if (NewSettingsActivity.checkBoxCounter == 0) {
                    repeatDaysString="Never";

                }
                frequencyTv.setText(repeatDaysString);

                SharedPrefrnceNotarius.setDataInSharedPrefrence(NewSettingsActivity.this, "repeat_days", repeatDaysString);

                Log.e("Repeat days string", repeatDaysString + ">>" + NewSettingsActivity.checkBoxCounter);

                updateModelFromLayout();

                AlarmManagerHelper.cancelAlarms(NewSettingsActivity.this);

                if (alarmDetails.id < 0) {
                    alarmDBHelper.createAlarm(alarmDetails);
                    Log.e("db created>>>>>>>","ok");
                } else {
                    Log.e("db updated>>>>>>>","ok");
                    alarmDBHelper.updateAlarm(alarmDetails);
                }

                if(!repeatDaysString.equals("Never"))
                 AlarmManagerHelper.setAlarms(NewSettingsActivity.this);

            }
        });

        dissmissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                /*dialog.dismiss();
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

                if (NewSettingsActivity.checkBoxCounter == 7) {
                    repeatDaysString="Everyday";

                }if (NewSettingsActivity.checkBoxCounter == 0) {
                    repeatDaysString="Never";
                }

                frequencyTv.setText(repeatDaysString);

                SharedPrefrnceNotarius.setDataInSharedPrefrence(NewSettingsActivity.this, "repeat_days", repeatDaysString);

                Log.e("Repeat days string", repeatDaysString + ">>" + NewSettingsActivity.checkBoxCounter);

                updateModelFromLayout();*/

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        alarmDetails.timeMinute = NewSettingsActivity.min;
        alarmDetails.timeHour = NewSettingsActivity.hour;
        AlarmManagerHelper.cancelAlarms(NewSettingsActivity.this);

        if (alarmDetails.id < 0) {
            alarmDBHelper.createAlarm(alarmDetails);
            Log.e("db created>>>>>>>","ok");
        } else {
            Log.e("db updated>>>>>>>","ok");
            alarmDBHelper.updateAlarm(alarmDetails);
        }
        if(!repeatDaysString.equals("Never"))
        AlarmManagerHelper.setAlarms(NewSettingsActivity.this);
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

        alarmDetails.timeMinute = NewSettingsActivity.min;
        alarmDetails.timeHour = NewSettingsActivity.hour;
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
            //Set the alarms
        } else {
            alarmDetails.isEnabled = true;
            alarmDetails.repeatWeekly = true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
