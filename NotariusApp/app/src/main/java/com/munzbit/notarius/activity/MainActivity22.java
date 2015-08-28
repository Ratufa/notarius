package com.munzbit.notarius.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.munzbit.notarius.R;
import com.munzbit.notarius.custom_views.CircularSeekBar;
import com.munzbit.notarius.custom_views.CircularSeekBarNew;
import com.munzbit.notarius.custom_views.SeekArc;
import com.munzbit.notarius.data_manager.DataManager;
import com.munzbit.notarius.data_manager.SharedPrefrnceNotarius;
import com.munzbit.notarius.utility.Methods;

/**
 * Created by Ratufa.Aditya on 8/20/2015.
 */
public class MainActivity22 extends Activity implements View.OnClickListener {

    private CircularSeekBar circularSeekBar;

    private TextView textView, mSeekArcProgress;

    private TextView workoutName;

    private String workOutDuration;

    private Button cancelBtn;

    private Button addBtn;

    private Button historyBtn;

    private Button exitBtn;

    private SeekBar effortsSeekBar;

    private String workDate;

    private String workType;

    private String workEffort = "1";

    private DataManager dataManager;

    public static int progressCounter = 0;

    private int[] colors = new int[]{R.color.red, R.color.dark_red,
            R.color.pink, R.color.cyan, R.color.green, R.color.gold,
            R.color.violet, R.color.indigo, R.color.sky_blue, R.color.purple,
            R.color.blue};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_details_new2);
        dataManager = new DataManager(this);
        circularSeekBar = (CircularSeekBar) findViewById(R.id.circularSeekBar1);

        textView = (TextView) findViewById(R.id.seekValue);
        workoutName = (TextView) findViewById(R.id.workoutName);
        effortsSeekBar = (SeekBar) findViewById(R.id.seekBarEffort);
        circularSeekBar.setMax(60);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        historyBtn = (Button) findViewById(R.id.saveHistoryBtn);
        exitBtn = (Button) findViewById(R.id.saveExitBtn);
        addBtn = (Button) findViewById(R.id.saveAndNew);


        circularSeekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {

            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {

                int hour = 0, min = 0;

                Log.e("progress>>>>>>>", String.valueOf(progress) + ">>" + circularSeekBar.getMax());

                progress = ((int) Math.round(progress / 5)) * 5;

                if (progress < 60)
                    min = progress;
                else {
                    progress = 0;
                }

                if (progress == 55) {
                    progress = 0;
                    circularSeekBar.setProgress(progress);
                    MainActivity22.progressCounter++;
                    min = 0;
                    if (MainActivity22.progressCounter < 12) {
                        if (MainActivity22.progressCounter < 11)
                            circularSeekBar.setCircleProgressColor(colors[MainActivity22.progressCounter]);
                        circularSeekBar.setMax(circularSeekBar.getMax() + 60);
                    }
                }

                Log.e("progressCounter>>>>>>>", String.valueOf(progressCounter) + ">>");

                if (MainActivity22.progressCounter < 12) {
                    textView.setText(Methods.pad(MainActivity22.progressCounter) + "h " + Methods.pad(min) + "min");
                }

                workOutDuration = Methods.pad(MainActivity22.progressCounter) + "h " + Methods.pad(min) + "min";

            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

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

        if (getIntent().getExtras() != null)
            workType = getIntent().getStringExtra("work_type");

        workoutName.setText(workType + "");

        cancelBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity22.progressCounter = 0;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cancelBtn:
                WorkOutActivity.workOut.finish();
                onBackPressed();
                break;

            case R.id.saveAndNew:
                workDate = SharedPrefrnceNotarius.getSharedPrefData(this, "work_date");
                dataManager.insertData(workDate, workOutDuration, workType, workEffort);

                WorkOutActivity.workOut.finish();
                MainScreenActivity.mainScreen.finish();
                Intent intent = new Intent(this, MainScreenActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.saveExitBtn:
                workDate = SharedPrefrnceNotarius.getSharedPrefData(this, "work_date");
                dataManager.insertData(workDate, workOutDuration, workType, workEffort);

                WorkOutActivity.workOut.finish();
                MainScreenActivity.mainScreen.finish();
                finish();
                break;

            case R.id.saveHistoryBtn:
                workDate = SharedPrefrnceNotarius.getSharedPrefData(this, "work_date");
                dataManager.insertData(workDate, workOutDuration, workType, workEffort);

                WorkOutActivity.workOut.finish();
                MainScreenActivity.mainScreen.finish();
                Intent intent1 = new Intent(this, WorkOutHistory.class);
                startActivity(intent1);
                finish();
                break;

        }

    }
}
