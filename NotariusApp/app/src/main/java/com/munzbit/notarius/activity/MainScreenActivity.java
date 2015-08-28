package com.munzbit.notarius.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.munzbit.notarius.R;
import com.munzbit.notarius.alarm_manager.AlarmDBHelper;
import com.munzbit.notarius.alarm_manager.AlarmManagerHelper;
import com.munzbit.notarius.alarm_manager.AlarmModel;
import com.munzbit.notarius.alarm_manager.AlarmService;
import com.munzbit.notarius.data_manager.DataManager;
import com.munzbit.notarius.data_manager.SharedPrefrnceNotarius;
import com.munzbit.notarius.modal.WorkOutModal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Ratufa.Manish on 8/4/2015.
 */
public class MainScreenActivity extends FragmentActivity implements
		View.OnClickListener {

	private Button todayBtn;

	private Button olderbtn;

	private Button historyBtn;

	public static Activity mainScreen;

	private ImageView settingsImage;

	private DataManager dataManager;

	private AlarmDBHelper alarmDBHelper;

	private List<AlarmModel> alarms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		mainScreen = this;
		dataManager = new DataManager(this);
		alarmDBHelper = new AlarmDBHelper(this);

		olderbtn = (Button) findViewById(R.id.olderBtn);

		todayBtn = (Button) findViewById(R.id.todayBtn);

		historyBtn = (Button) findViewById(R.id.workOutHistoryBtn);

		settingsImage = (ImageView) findViewById(R.id.settingImage);


		olderbtn.setOnClickListener(this);
		todayBtn.setOnClickListener(this);
		historyBtn.setOnClickListener(this);
		settingsImage.setOnClickListener(this);
		alarms = alarmDBHelper.getAlarms();

	}

	public void loadWords() throws IOException {

		ArrayList<String> arrayList = new ArrayList<String>();

		final Resources resources = this.getResources();

		InputStream inputStream = resources.getAssets().open(
				"CompleteListActivities.txt");

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));

		try {
			String line = "";
			while ((line = reader.readLine()) != null) {
				arrayList.add(line);
			}
		} finally {
			reader.close();
		}

		if (dataManager.getAllActivity().size() == 0)
			dataManager.insertActivityIntoDB(arrayList);

		if(isCheckedAvailable()==0) {
			dataManager.updateActivity("Running", "true");
			dataManager.updateActivity("Cycling (road)", "true");
			dataManager.updateActivity("Weight training", "true");
		}

		Log.e("arrayList>>", arrayList.toString() + "---");

	}

	@Override
	protected void onPause() {
		super.onPause();
		//stopService(new Intent(this, AlarmService.class));
	}

	public int isCheckedAvailable() {

		ArrayList<WorkOutModal> arrayList = new ArrayList<WorkOutModal>();

		for (int i = 0; i < dataManager.getAllActivity().size(); i++) {
			if (dataManager.getAllActivity().get(i).isSelected())
				arrayList.add(dataManager.getAllActivity().get(i));
		}

		return arrayList.size();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//AlarmManagerHelper.cancelAlarms(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			loadWords();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.olderBtn:
			DialogFragment newFragment = new DatePickerFragment();
			newFragment.show(getSupportFragmentManager(), "datePicker");
			break;

		case R.id.todayBtn:
			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH);
			int year = calendar.get(Calendar.YEAR);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			SharedPrefrnceNotarius.setDataInSharedPrefrence(this, "work_date",
					day + "/" + (month + 1) + "/" + year);
			Intent intent1 = new Intent(this, WorkOutActivity.class);
			startActivity(intent1);
			break;

		case R.id.workOutHistoryBtn:
			Intent intent2 = new Intent(this, WorkOutHistory.class);
			intent2.putExtra("history", true);
			startActivity(intent2);
			finish();
			break;

		case R.id.settingImage:
			List<AlarmModel> alarms = alarmDBHelper.getAlarms();

			if(alarms!=null){
				Log.e("alarms size>>",alarms.size()+">>");
				Intent intent = new Intent(MainScreenActivity.this,
						NewSettingsActivity.class);
				intent.putExtra("_id","1");
				startActivity(intent);
			}else{
				Intent intent = new Intent(MainScreenActivity.this,
						NewSettingsActivity.class);
				intent.putExtra("_id","-1");
				startActivity(intent);
			}
			//List<AlarmModel> alarms = alarmDBHelper.getAlarms();

			/*Database.init(MainScreenActivity.this);
			List<Alarm> alarms = Database.getAll();
			Intent intent = new Intent(MainScreenActivity.this,
					AlarmPreferencesActivity.class);
			if (alarms.size() != 0)
				intent.putExtra("alarm", alarms.get(0));
			startActivity(intent);*/

			break;

		}

	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog picker = new DatePickerDialog(getActivity(), this,
					year, month, day);

			if (Build.VERSION.SDK_INT >= 11) {
				picker.getDatePicker().setMaxDate(c.getTime().getTime());
			}

			return picker;
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			SharedPrefrnceNotarius.setDataInSharedPrefrence(getActivity(),
					"work_date", day + "/" + (month + 1) + "/" + year);

			Intent intent = new Intent(getActivity(), WorkOutActivity.class);
			startActivity(intent);
		}
	}
}
