package com.munzbit.notarius.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.munzbit.notarius.R;
import com.munzbit.notarius.alarm_manager.AlarmDBHelper;
import com.munzbit.notarius.alarm_manager.AlarmModel;
import com.munzbit.notarius.data_manager.DataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ratufa.Manish on 8/4/2015.
 */
public class WorkOutHistory extends Activity implements View.OnClickListener {


    private Button registerWorkOut;

    private Intent intent;

    private DataManager dataManager;

    private ArrayList<HashMap<String, String>> arrayList;

    private TableLayout tableLayout;

    private ImageView settings;
    private AlarmDBHelper alarmDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_history);
        dataManager = new DataManager(this);
        alarmDBHelper = new AlarmDBHelper(this);
        arrayList = new ArrayList<HashMap<String, String>>();
        arrayList = dataManager.getWorkoutHistory();

        registerWorkOut = (Button) findViewById(R.id.registerWorkBtn);

        tableLayout = (TableLayout) findViewById(R.id.historyTableLayout);

        settings = (ImageView) findViewById(R.id.settingImage);

        createTableLayout();

        registerWorkOut.setOnClickListener(this);
        settings.setOnClickListener(this);
    }

    private TableLayout createTableLayout() {

        TableRow tableRow1 = new TableRow(this);

        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.width = TableLayout.LayoutParams.MATCH_PARENT;
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;

        TextView tvDate1 = new TextView(this);
        tvDate1.setGravity(Gravity.CENTER | Gravity.LEFT);

        TextView tvDuration1 = new TextView(this);
        tvDuration1.setGravity(Gravity.CENTER | Gravity.LEFT);

        TextView tvType1 = new TextView(this);
        tvType1.setGravity(Gravity.CENTER | Gravity.LEFT);

        TextView tvEffort1 = new TextView(this);
        tvEffort1.setGravity(Gravity.CENTER | Gravity.LEFT);

        tvDate1.setText("Date");
        tvDate1.setTextColor(Color.BLACK);
        tvDate1.setBackgroundColor(Color.parseColor("#cccccc"));
        tableRow1.addView(tvDate1, tableRowParams);

        tvDuration1.setText("Duration");
        tvDuration1.setTextColor(Color.BLACK);
        tvDuration1.setBackgroundColor(Color.parseColor("#cccccc"));
        tableRow1.addView(tvDuration1, tableRowParams);

        tvType1.setText("Type");
        tvType1.setTextColor(Color.BLACK);
        tvType1.setBackgroundColor(Color.parseColor("#cccccc"));
        tableRow1.addView(tvType1, tableRowParams);

        tvEffort1.setText("Effort");
        tvEffort1.setTextColor(Color.BLACK);
        tvEffort1.setBackgroundColor(Color.parseColor("#cccccc"));
        tableRow1.addView(tvEffort1, tableRowParams);
        //tableRow1.setLayoutParams(tableRowParams);
        tableLayout.addView(tableRow1);

        for (int i = 0; i < arrayList.size(); i++) {

            TableRow tableRow = new TableRow(this);

            for (int j = 0; j < 4; j++) {

                if (j == 0) {

                    if (i % 2 != 0) {

                        TextView tvDate = new TextView(this);
                        tvDate.setBackgroundColor(Color.parseColor("#EEEEEE"));
                        tvDate.setTextColor(Color.BLACK);
                        tvDate.setGravity(Gravity.CENTER | Gravity.LEFT);

                        TextView tvDuration = new TextView(this);
                        tvDuration.setBackgroundColor(Color.parseColor("#EEEEEE"));
                        tvDuration.setTextColor(Color.BLACK);
                        tvDuration.setGravity(Gravity.CENTER | Gravity.LEFT);

                        TextView tvType = new TextView(this);
                        tvType.setBackgroundColor(Color.parseColor("#EEEEEE"));
                        tvType.setTextColor(Color.BLACK);
                        tvType.setGravity(Gravity.CENTER | Gravity.LEFT);

                        TextView tvEffort = new TextView(this);
                        tvEffort.setBackgroundColor(Color.parseColor("#EEEEEE"));
                        tvEffort.setTextColor(Color.BLACK);
                        tvEffort.setGravity(Gravity.CENTER | Gravity.LEFT);

                        tvDate.setText(arrayList.get(i).get("workout_date"));
                        tableRow.addView(tvDate, tableRowParams);

                        tvDuration.setText(arrayList.get(i).get("workout_duration"));
                        tableRow.addView(tvDuration, tableRowParams);

                        tvType.setText(arrayList.get(i).get("workout_type"));
                        tableRow.addView(tvType, tableRowParams);

                        tvEffort.setText(arrayList.get(i).get("workout_effort"));
                        tableRow.addView(tvEffort, tableRowParams);


                    } else {

                        TextView tvDate = new TextView(this);
                        tvDate.setBackgroundColor(Color.WHITE);
                        tvDate.setTextColor(Color.BLACK);
                        tvDate.setGravity(Gravity.CENTER | Gravity.LEFT);

                        TextView tvDuration = new TextView(this);
                        tvDuration.setBackgroundColor(Color.WHITE);
                        tvDuration.setTextColor(Color.BLACK);
                        tvDuration.setGravity(Gravity.CENTER | Gravity.LEFT);

                        TextView tvType = new TextView(this);
                        tvType.setBackgroundColor(Color.WHITE);
                        tvType.setTextColor(Color.BLACK);
                        tvType.setGravity(Gravity.CENTER | Gravity.LEFT);

                        TextView tvEffort = new TextView(this);
                        tvEffort.setBackgroundColor(Color.WHITE);
                        tvEffort.setTextColor(Color.BLACK);
                        tvEffort.setGravity(Gravity.CENTER | Gravity.LEFT);

                        tvDate.setText(arrayList.get(i).get("workout_date"));
                        tableRow.addView(tvDate, tableRowParams);

                        tvDuration.setText(arrayList.get(i).get("workout_duration"));
                        tableRow.addView(tvDuration, tableRowParams);

                        tvType.setText(arrayList.get(i).get("workout_type"));
                        tableRow.addView(tvType, tableRowParams);

                        tvEffort.setText(arrayList.get(i).get("workout_effort"));
                        tableRow.addView(tvEffort, tableRowParams);
                    }


                }
            }

            tableLayout.addView(tableRow);
        }

        return tableLayout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerWorkBtn:

                intent = new Intent(this, MainScreenActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.settingImage:
              /*  Database.init(this);
                List<Alarm> alarms = Database.getAll();
                Intent intent = new Intent(this, AlarmPreferencesActivity.class);
                if (alarms.size() != 0)
                    intent.putExtra("alarm", alarms.get(0));
                startActivity(intent);*/

                List<AlarmModel> alarms = alarmDBHelper.getAlarms();

                if(alarms!=null){
                    Log.e("alarms size>>", alarms.size() + ">>");
                    Intent intent = new Intent(WorkOutHistory.this,
                            NewSettingsActivity.class);
                    intent.putExtra("_id","1");
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(WorkOutHistory.this,
                            NewSettingsActivity.class);
                    intent.putExtra("_id","-1");

                    startActivity(intent);
                }

                break;


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
        finish();
    }
}
