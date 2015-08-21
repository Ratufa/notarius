package com.munzbit.notarius.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.munzbit.notarius.R;
import com.munzbit.notarius.adapter.GridAdapter;
import com.munzbit.notarius.adapter.QuickListAdapter;
import com.munzbit.notarius.custom_views.ExpandableGridView;
import com.munzbit.notarius.custom_views.ExpandableListView;
import com.munzbit.notarius.datamanager.DataManager;
import com.munzbit.notarius.datamanager.SharedPrefrnceNotarius;
import com.munzbit.notarius.modal.WorkOutModal;

/**
 * Created by Ratufa.Manish on 8/4/2015.
 */
public class WorkOutActivity extends Activity implements View.OnClickListener {

    private TextView cyclingBtn;

    private TextView runningBtn;

    private TextView weightsBtn;

    private TextView plusBtn;

    private Intent intent;

    public static Activity workOut;

    private TextView dateTv;

    private ImageView editWorkOut;

    private ExpandableListView listView;

    private ExpandableGridView gridView;

    private DataManager dataManager;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_type_new);
        workOut = this;
        dataManager = new DataManager(this);

       /* View headerView = getLayoutInflater().inflate(
                R.layout.work_out_list_header, null);
*/
        //cyclingBtn = (TextView) headerView.findViewById(R.id.cyclingBtn);

        //runningBtn = (TextView) headerView.findViewById(R.id.runningBtn);

        //weightsBtn = (TextView) headerView.findViewById(R.id.weightBtn);

        //plusBtn = (TextView) headerView.findViewById(R.id.plusBtn);

        listView = (ExpandableListView) findViewById(R.id.quickList);

        gridView = (ExpandableGridView) findViewById(R.id.gridView);

        dateTv = (TextView) findViewById(R.id.dateTv);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (tempList.get(position).getWorkOutTitle().equals("+")) {
                    intent = new Intent(WorkOutActivity.this, WorkOutList.class);
                    startActivity(intent);
                }else{
                    intent = new Intent(WorkOutActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        editWorkOut = (ImageView) findViewById(R.id.editWork);

        dateTv.setText("Add a workout for "
                + SharedPrefrnceNotarius.getSharedPrefData(this, "work_date"));

       // cyclingBtn.setOnClickListener(this);
        editWorkOut.setOnClickListener(this);
        //runningBtn.setOnClickListener(this);
        //plusBtn.setOnClickListener(this);
        //weightsBtn.setOnClickListener(this);

    }

    ArrayList<WorkOutModal> tempList;

    @Override
    protected void onResume() {
        super.onResume();
        tempList = new ArrayList<>();
        ArrayList<WorkOutModal> workOutModalArrayList = dataManager.getAllActivity();

        for (int i = 0; i < workOutModalArrayList.size(); i++) {

            if (workOutModalArrayList.get(i).isSelected())
                tempList.add(workOutModalArrayList.get(i));
        }

        if (tempList.size() < 6) {
            WorkOutModal workOutModal = new WorkOutModal();
            workOutModal.setWorkOutTitle("+");
            workOutModal.setSelected(false);
            tempList.add(workOutModal);
        }

        gridView.setAdapter(new GridAdapter(this, tempList));
        listView.setAdapter(new QuickListAdapter(this, workOutModalArrayList));

        listView.setExpanded(true);
        gridView.setExpanded(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
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

            case R.id.editWork:
                intent = new Intent(this, WorkOutList.class);
                intent.putExtra("work_type", "weights");
                startActivity(intent);
                break;

            case R.id.plusBtn:
                intent = new Intent(this, WorkOutList.class);
                intent.putExtra("work_type", "weights");
                startActivity(intent);
                break;
        }
    }
}
