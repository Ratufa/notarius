package com.munzbit.notarius.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.munzbit.notarius.R;
import com.munzbit.notarius.adapter.QuickListAdapter;
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

	private ListView listView;

	private DataManager dataManager;

	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout_type_new);
		workOut = this;
		dataManager = new DataManager(this);

		View headerView = getLayoutInflater().inflate(
				R.layout.work_out_list_header, null);

		cyclingBtn = (TextView) headerView.findViewById(R.id.cyclingBtn);

		runningBtn = (TextView) headerView.findViewById(R.id.runningBtn);

		weightsBtn = (TextView) headerView.findViewById(R.id.weightBtn);

		plusBtn = (TextView) headerView.findViewById(R.id.plusBtn);

		listView = (ListView) findViewById(R.id.quickList);

		listView.addHeaderView(headerView);

		dateTv = (TextView) findViewById(R.id.dateTv);

		editWorkOut = (ImageView) findViewById(R.id.editWork);

		dateTv.setText("Add a workout for "
				+ SharedPrefrnceNotarius.getSharedPrefData(this, "work_date"));

		cyclingBtn.setOnClickListener(this);
		editWorkOut.setOnClickListener(this);
		runningBtn.setOnClickListener(this);
		plusBtn.setOnClickListener(this);
		weightsBtn.setOnClickListener(this);

		ArrayList<WorkOutModal> workOutModalArrayList = dataManager.getAllActivity();
		listView.setAdapter(new QuickListAdapter(this, workOutModalArrayList));

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
