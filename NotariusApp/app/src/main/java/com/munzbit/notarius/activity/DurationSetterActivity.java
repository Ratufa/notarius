package com.munzbit.notarius.activity;

import org.joda.time.DateTime;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.munzbit.notarius.R;
import com.munzbit.notarius.data_manager.DataManager;
import com.munzbit.notarius.data_manager.SharedPrefrnceNotarius;
import com.munzbit.notarius.duration_setter.ClockView;
import com.munzbit.notarius.duration_setter.VisibleForTesting;

@SuppressLint("NewApi")
public class DurationSetterActivity extends Activity implements ClockView.ClockTimeUpdateListener {

    private static ClockView sMinDepartTimeClockView;

    private static ClockView sMaxDepartTimeClockView;

   // private static TextView textView, mSeekArcProgress;

    private static TextView workoutName;

    private String workOutDuration;

    private static Button cancelBtn;

    private static Button addBtn;

    private static Button historyBtn;

    private static Button exitBtn;

    private static SeekBar effortsSeekBar;

    private static String workDate;

    private static String workType;

    private static String workEffort = "1";

    private DataManager dataManager;

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        dataManager = new DataManager(this);
        if (getIntent().getExtras() != null)
            workType = getIntent().getStringExtra("work_type");

        final DateTime minTime = new DateTime(2014, 4, 25, 0, 0);

        final DateTime maxTime = new DateTime(2014, 4, 25, 12, 0);

        sMinDepartTimeClockView = (ClockView) findViewById(R.id.min_depart_time_clock_view);
        sMinDepartTimeClockView.setBounds(minTime, maxTime, false);

        //textView = (TextView) findViewById(R.id.seekValue);
        workoutName = (TextView) findViewById(R.id.workoutName);
        effortsSeekBar = (SeekBar) findViewById(R.id.seekBarEffort);

        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        historyBtn = (Button) findViewById(R.id.saveHistoryBtn);
        exitBtn = (Button) findViewById(R.id.saveExitBtn);
        addBtn = (Button) findViewById(R.id.saveAndNew);

        workoutName.setText(workType + "");

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkOutActivity.workOut.finish();
                onBackPressed();
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workDate = SharedPrefrnceNotarius.getSharedPrefData(DurationSetterActivity.this, "work_date");
                if (SharedPrefrnceNotarius.getSharedPrefData(DurationSetterActivity.this, "time_Str") != null)
                    workOutDuration = SharedPrefrnceNotarius.getSharedPrefData(DurationSetterActivity.this, "time_Str");
                else
                	workOutDuration = "00h 00min";
                dataManager.insertData(workDate, workOutDuration, workType, workEffort);

                WorkOutActivity.workOut.finish();
                MainScreenActivity.mainScreen.finish();
                Intent intent1 = new Intent(DurationSetterActivity.this, WorkOutHistory.class);
                startActivity(intent1);
                finish();
            }
        });


        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workDate = SharedPrefrnceNotarius.getSharedPrefData(DurationSetterActivity.this, "work_date");
                if (SharedPrefrnceNotarius.getSharedPrefData(DurationSetterActivity.this, "time_Str") != null)
                    workOutDuration = SharedPrefrnceNotarius.getSharedPrefData(DurationSetterActivity.this, "time_Str");
                else
                    workOutDuration = "00h 00min";
                dataManager.insertData(workDate, workOutDuration, workType, workEffort);

                WorkOutActivity.workOut.finish();
                MainScreenActivity.mainScreen.finish();
                finish();
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workDate = SharedPrefrnceNotarius.getSharedPrefData(DurationSetterActivity.this, "work_date");
                if (SharedPrefrnceNotarius.getSharedPrefData(DurationSetterActivity.this, "time_Str") != null)
                    workOutDuration = SharedPrefrnceNotarius.getSharedPrefData(DurationSetterActivity.this, "time_Str");
                else
                	workOutDuration = "00h 00min";
                dataManager.insertData(workDate, workOutDuration, workType, workEffort);
                WorkOutActivity.workOut.finish();
                MainScreenActivity.mainScreen.finish();
                Intent intent = new Intent(DurationSetterActivity.this, MainScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });


        effortsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                workEffort = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public static ClockView getMinDepartTimeClockView() {
        return sMinDepartTimeClockView;
    }

    public static ClockView getMaxDepartTimeClockView() {
        return sMaxDepartTimeClockView;
    }

    @VisibleForTesting
    public void changeClockTimeForTests(DateTime dateTime, boolean isMaxTime) {
        if (isMaxTime) {
            sMaxDepartTimeClockView.setClockTimeUpdateListener(this);
            sMaxDepartTimeClockView.setNewCurrentTime(dateTime);
        } else {
            sMinDepartTimeClockView.setClockTimeUpdateListener(this);
            sMinDepartTimeClockView.setNewCurrentTime(dateTime);
        }
    }

    @Override
    public void onClockTimeUpdate(ClockView clockView, DateTime currentTime) {
        if (clockView.equals(sMinDepartTimeClockView)) {
            if (currentTime.compareTo(sMaxDepartTimeClockView.getNewCurrentTime()) >= 0) {
                sMinDepartTimeClockView.setNewCurrentTime(sMinDepartTimeClockView.getNewCurrentTime().minusHours(1));
            }
        } else if (clockView.equals(sMaxDepartTimeClockView)) {
            if (currentTime.compareTo(sMinDepartTimeClockView.getNewCurrentTime()) <= 0) {
                sMaxDepartTimeClockView.setNewCurrentTime(sMaxDepartTimeClockView.getNewCurrentTime().plusHours(1));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
