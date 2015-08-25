package com.munzbit.notarius.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.munzbit.notarius.R;
import com.munzbit.notarius.custom_views.CircularSeekBar;
import com.munzbit.notarius.custom_views.HoloCircleSeekBar;
import com.munzbit.notarius.data_manager.DataManager;
import com.munzbit.notarius.data_manager.SharedPrefrnceNotarius;
import com.munzbit.notarius.utility.Methods;

/**
 * Created by Ratufa.Aditya on 8/20/2015.
 */
public class MainActivity extends Activity implements View.OnClickListener {


    private CircularSeekBar circularSeekBar;

    private TextView textView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_details_new);
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


                if (progress == circularSeekBar.getMax()) {
                    progressCounter++;
                    if(progressCounter==1){
                        circularSeekBar.setCircleProgressColor(Color.BLUE);
                    }if(progressCounter==2){
                        circularSeekBar.setCircleProgressColor(Color.CYAN);
                    }if(progressCounter==3){
                        circularSeekBar.setCircleProgressColor(Color.GRAY);
                    }if(progressCounter==4){
                        circularSeekBar.setCircleProgressColor(Color.GREEN);
                    }if(progressCounter==5){
                        circularSeekBar.setCircleProgressColor(Color.RED);
                    }if(progressCounter==6){
                        circularSeekBar.setCircleProgressColor(Color.WHITE);
                    }if(progressCounter==7){
                        circularSeekBar.setCircleProgressColor(Color.YELLOW);
                    }if(progressCounter==8){
                        circularSeekBar.setCircleProgressColor(Color.BLACK);
                    }else{
                        circularSeekBar.setCircleProgressColor(Color.GREEN);
                    }

                    circularSeekBar.setMax(circularSeekBar.getMax() + 60);
                }

                //circularSeekBar.setProgress(progress+5);
                //int hour = progress / 4;

              //  int minute = (progress % 4) * 15;

                textView.setText((progress+5)+"");

               // workOutDuration = Methods.pad(hour) + "h " + Methods.pad(minute) + "min";
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
