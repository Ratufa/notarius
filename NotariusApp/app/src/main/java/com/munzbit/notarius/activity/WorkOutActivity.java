package com.munzbit.notarius.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.munzbit.notarius.R;
import com.munzbit.notarius.datamanager.SharedPrefrnceNotarius;

/**
 * Created by Ratufa.Manish on 8/4/2015.
 */
public class WorkOutActivity extends Activity implements View.OnClickListener {


    private Button cyclingBtn;

    private Button runningBtn;

    private Button weightsBtn;

    private Intent intent;

    public static Activity workOut;

    private TextView dateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_type);
        workOut = this;

        cyclingBtn =(Button) findViewById(R.id.cyclingBtn);

        runningBtn =(Button) findViewById(R.id.runningBtn);

        weightsBtn =(Button) findViewById(R.id.weightBtn);

        dateTv = (TextView) findViewById(R.id.dateTv);

        dateTv.setText(SharedPrefrnceNotarius.getSharedPrefData(this, "work_date"));

        cyclingBtn.setOnClickListener(this);
        runningBtn.setOnClickListener(this);
        weightsBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cyclingBtn:
                intent = new Intent(this, WorkoutDetails.class);
                intent.putExtra("work_type","cycling");
                startActivity(intent);
                break;
            case R.id.runningBtn:
                intent = new Intent(this, WorkoutDetails.class);
                intent.putExtra("work_type","running");
                startActivity(intent);
                break;

            case R.id.weightBtn:
                intent = new Intent(this, WorkoutDetails.class);
                intent.putExtra("work_type","weights");
                startActivity(intent);
                break;
        }
    }
}
