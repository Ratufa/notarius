package com.munzbit.notarius.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.munzbit.notarius.R;
import com.munzbit.notarius.adapter.WorkoutAdapter;
import com.munzbit.notarius.data_manager.DataManager;
import com.munzbit.notarius.modal.WorkOutModal;

/**
 * Created by Ratufa.Manish on 8/4/2015.
 */
public class WorkOutList extends Activity implements View.OnClickListener {


    private TextView doneBtn;

    private TextView counter;

    private ImageView backImage;

    private Intent intent;

    public static Activity workOut;

    private ListView listView;

    private DataManager dataManager;

    public static int checkCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_list);
        workOut = this;
        dataManager = new DataManager(this);

        doneBtn = (TextView) findViewById(R.id.doneBtn);

        counter = (TextView) findViewById(R.id.textCounter);

        listView = (ListView) findViewById(R.id.workoutList);

        backImage = (ImageView) findViewById(R.id.backImage);

        doneBtn.setOnClickListener(this);
        backImage.setOnClickListener(this);

        if (getIntent().getExtras() != null) {
            WorkOutList.checkCounter = getIntent().getIntExtra("counter_size", 0);
        }

        counter.setText(WorkOutList.checkCounter + "/6");

        ArrayList<WorkOutModal> workOutModalArrayList = new ArrayList<WorkOutModal>();

        for (int i = 1; i < dataManager.getAllActivity().size(); i++) {
            workOutModalArrayList.add(dataManager.getAllActivity().get(i));
        }

        listView.setAdapter(new WorkoutAdapter(this, workOutModalArrayList));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cyclingBtn:
                intent = new Intent(this, WorkoutDetails.class);
                intent.putExtra("work_type", "cycling");
                startActivity(intent);
                break;
            case R.id.runningBtn:
                intent = new Intent(this, WorkoutDetails.class);
                intent.putExtra("work_type", "running");
                startActivity(intent);
                break;

            case R.id.weightBtn:
                intent = new Intent(this, WorkoutDetails.class);
                intent.putExtra("work_type", "weights");
                startActivity(intent);
                break;
            case R.id.backImage:
                finish();
                break;
            case R.id.doneBtn:
                finish();
                break;
        }
    }

    ArrayList<WorkOutModal> arrayList;

    public boolean sizeExceeds(boolean isChecked) {

        arrayList = new ArrayList<WorkOutModal>();

        boolean exceeds = false;

        if (isChecked && WorkOutList.checkCounter <= 6) {
            WorkOutList.checkCounter++;

        } else if (!isChecked) {
            WorkOutList.checkCounter--;

        }

        if (WorkOutList.checkCounter > 6) {
            exceeds = true;
            WorkOutList.checkCounter--;
            Toast.makeText(this, "Unable to add activity.Maximum limit(6)is reached", Toast.LENGTH_SHORT).show();
        } else {
            exceeds = false;
        }
        counter.setText(WorkOutList.checkCounter + "/6");
       
        return exceeds;
    }
}
