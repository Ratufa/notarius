package com.munzbit.notarius.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.munzbit.notariusdemo.R;
import com.munzbit.notariusdemo.databases.DataManager;
import com.munzbit.notariusdemo.modal.WorkOutModal;


/**
 * Created by Ratufa.Manish on 8/4/2015.
 */
public class WorkOutList extends Activity implements View.OnClickListener {


    private TextView doneBtn;

    //private TextView counter;

    private ImageView backImage;

    private Intent intent;

    public static Activity workOut;

    //private TextView dateTv;

    private ListView listView;
    
    private DataManager dataManager;

    //private ImageView editWorkOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_list);
        workOut = this;
        dataManager = new DataManager(this);

        doneBtn =(TextView) findViewById(R.id.doneBtn);

        //counter =(TextView) findViewById(R.id.textCounter);

        listView = (ListView) findViewById(R.id.workoutList);

        backImage = (ImageView) findViewById(R.id.backImage);

        doneBtn.setOnClickListener(this);
        backImage.setOnClickListener(this);
        
      
        ArrayList<WorkOutModal> workOutModalArrayList = new ArrayList<WorkOutModal>();
        
        for (int i = 1; i < dataManager.getAllActivity().size(); i++) {
        	workOutModalArrayList.add(dataManager.getAllActivity().get(i));
		}

//        WorkOutModal workOutModal_1 = new WorkOutModal();
//        workOutModal_1.setSelected(false);
//        workOutModal_1.setWorkOutTitle("Aerobic");
//        workOutModalArrayList.add(workOutModal_1);
//
//        WorkOutModal workOutModal_2 = new WorkOutModal();
//        workOutModal_2.setSelected(false);
//        workOutModal_2.setWorkOutTitle("Boxing");
//        workOutModalArrayList.add(workOutModal_2);
//
//        WorkOutModal workOutModal_3 = new WorkOutModal();
//        workOutModal_3.setSelected(false);
//        workOutModal_3.setWorkOutTitle("Badminton");
//        workOutModalArrayList.add(workOutModal_3);
//
//        WorkOutModal workOutModal_4 = new WorkOutModal();
//        workOutModal_4.setSelected(false);
//        workOutModal_4.setWorkOutTitle("Cycling");
//        workOutModalArrayList.add(workOutModal_4);
//
//        WorkOutModal workOutModal_5 = new WorkOutModal();
//        workOutModal_5.setSelected(false);
//        workOutModal_5.setWorkOutTitle("Running");
//        workOutModalArrayList.add(workOutModal_5);
//
//        WorkOutModal workOutModal_6 = new WorkOutModal();
//        workOutModal_6.setSelected(false);
//        workOutModal_6.setWorkOutTitle("Weights");
//        workOutModalArrayList.add(workOutModal_6);

        listView.setAdapter(new WorkoutAdapter(this,workOutModalArrayList));

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
            case R.id.backImage:
                finish();
                break;
            case R.id.doneBtn:
                finish();
                break;
        }
    }
}
