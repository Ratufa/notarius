package com.munzbit.notarius.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;

import com.munzbit.notarius.R;
import com.munzbit.notarius.data_manager.DataManager;
import com.munzbit.notarius.data_manager.SharedPrefrnceNotarius;

/**
 * Created by Ratufa.Manish on 8/4/2015.
 */
public class WorkoutDetails extends Activity implements View.OnClickListener {


    private Button cancelBtn;

    private Button addBtn;

    private Button historyBtn;

    private Button exitBtn;

    private SeekBar effortsSeekBar;

    private NumberPicker hourNumberPicker;

    private NumberPicker minuteNumberPicker;

    private String workDuration;

    private String workDate;

    private String workType;

    private String workEffort="1";

    private int hours, min;

    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_details);
        dataManager = new DataManager(this);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        historyBtn = (Button) findViewById(R.id.saveHistoryBtn);

        exitBtn = (Button) findViewById(R.id.saveExitBtn);

        addBtn = (Button) findViewById(R.id.saveAndNew);

        effortsSeekBar = (SeekBar) findViewById(R.id.seekBarEffort);

        hourNumberPicker = (NumberPicker) findViewById(R.id.hourPicker);

        minuteNumberPicker = (NumberPicker) findViewById(R.id.minPicker);

        if (getIntent().getExtras() != null)
            workType = getIntent().getStringExtra("work_type");

        hourNumberPicker.setMaxValue(12);

        minuteNumberPicker.setMaxValue(59);

        hourNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                hours = newVal;
            }
        });


        minuteNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                min = newVal;
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

        cancelBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cancelBtn:
                WorkOutActivity.workOut.finish();
                onBackPressed();
                break;

            case R.id.saveAndNew:
                if (hours < 10 && min > 10) {
                    workDuration = "0" + hours + ":" + min;
                } else if (hours > 10 && min < 10) {
                    workDuration = hours + ":0" + min;
                } else if (hours < 10 && min < 10) {
                    workDuration = "0" + hours + ":0" + min;
                }else{
                    workDuration = hours + ":" + min;
                }
                workDate = SharedPrefrnceNotarius.getSharedPrefData(this, "work_date");
                dataManager.insertData(workDate, workDuration, workType, workEffort);

                WorkOutActivity.workOut.finish();
                MainScreenActivity.mainScreen.finish();
                Intent intent = new Intent(this, MainScreenActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.saveExitBtn:
                if (hours < 10 && min > 10) {
                    workDuration = "0" + hours + ":" + min;
                } else if (hours > 10 && min < 10) {
                    workDuration = hours + ":0" + min;
                } else if (hours < 10 && min < 10) {
                    workDuration = "0" + hours + ":0" + min;
                }else{
                    workDuration = hours + ":" + min;
                }
                workDate = SharedPrefrnceNotarius.getSharedPrefData(this,"work_date");
                dataManager.insertData(workDate, workDuration, workType, workEffort);

                WorkOutActivity.workOut.finish();
                MainScreenActivity.mainScreen.finish();
                finish();
                break;

            case R.id.saveHistoryBtn:
                if (hours < 10 && min > 10) {
                    workDuration = "0" + hours + ":" + min;
                } else if (hours > 10 && min < 10) {
                    workDuration = hours + ":0" + min;
                } else if (hours < 10 && min < 10) {
                    workDuration = "0" + hours + ":0" + min;
                }else{
                    workDuration = hours + ":" + min;
                }

                workDate = SharedPrefrnceNotarius.getSharedPrefData(this,"work_date");
                dataManager.insertData(workDate, workDuration, workType, workEffort);

                WorkOutActivity.workOut.finish();
                MainScreenActivity.mainScreen.finish();
                Intent intent1 = new Intent(this, WorkOutHistory.class);
                startActivity(intent1);
                finish();
                break;

        }

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
