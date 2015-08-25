package com.munzbit.notarius.data_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.munzbit.notarius.modal.TimerModal;
import com.munzbit.notarius.modal.WorkOutModal;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ratufa.Manish on 8/5/2015.
 */
public class DataManager {

	private DataHelper dataHelper;

	private SQLiteDatabase sqLiteDatabase;

	public DataManager(Context context) {

		dataHelper = new DataHelper(context, DataHelper.DB_NAME, null,
				DataHelper.DB_VERSION);
	}

	public void insertData(String date, String duration, String type,
			String effort) {

		sqLiteDatabase = dataHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DataHelper.WORKOUT_DATE, date);
		contentValues.put(DataHelper.WORKOUT_DURATION, duration);
		contentValues.put(DataHelper.WORKOUT_TYPE, type);
		contentValues.put(DataHelper.WORKOUT_EFFORT, effort);
		sqLiteDatabase.insert(DataHelper.HISTORY_TABLE, null, contentValues);
		sqLiteDatabase.close();

	}

	public void insertFrequency() {

		sqLiteDatabase = dataHelper.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put(DataHelper.FREQUENCY_DAY, "Monday");
		contentValues.put("is_selected", "false");
		sqLiteDatabase.insert(DataHelper.FREQUENCY_TABLE, null, contentValues);

		ContentValues contentValues1 = new ContentValues();
		contentValues1.put(DataHelper.FREQUENCY_DAY, "Tuesday");
		contentValues1.put("is_selected", "false");
		sqLiteDatabase.insert(DataHelper.FREQUENCY_TABLE, null, contentValues1);

		ContentValues contentValues2 = new ContentValues();
		contentValues2.put(DataHelper.FREQUENCY_DAY, "Wednesday");
		contentValues2.put("is_selected", "false");
		sqLiteDatabase.insert(DataHelper.FREQUENCY_TABLE, null, contentValues2);

		ContentValues contentValues3 = new ContentValues();
		contentValues3.put(DataHelper.FREQUENCY_DAY, "Thursday");
		contentValues3.put("is_selected", "false");
		sqLiteDatabase.insert(DataHelper.FREQUENCY_TABLE, null, contentValues3);

		ContentValues contentValues4 = new ContentValues();
		contentValues4.put(DataHelper.FREQUENCY_DAY, "Friday");
		contentValues4.put("is_selected", "false");
		sqLiteDatabase.insert(DataHelper.FREQUENCY_TABLE, null, contentValues4);

		ContentValues contentValues5 = new ContentValues();
		contentValues5.put(DataHelper.FREQUENCY_DAY, "Saturday");
		contentValues5.put("is_selected", "false");
		sqLiteDatabase.insert(DataHelper.FREQUENCY_TABLE, null, contentValues5);

		ContentValues contentValues6 = new ContentValues();
		contentValues6.put(DataHelper.FREQUENCY_DAY, "Sunday");
		contentValues6.put("is_selected", "false");
		sqLiteDatabase.insert(DataHelper.FREQUENCY_TABLE, null, contentValues6);

		sqLiteDatabase.close();
	}

	public void insertActivityIntoDB(ArrayList<String> activityData) {
		
		sqLiteDatabase = dataHelper.getWritableDatabase();

		for (int i = 0; i < activityData.size(); i++) {
			
			ContentValues contentValues6 = new ContentValues();
			contentValues6.put(DataHelper.ACTIVITY_NAME, activityData.get(i));
			contentValues6.put("is_selected", "false");
			sqLiteDatabase.insert(DataHelper.ACTIVITY_TABLE, null,
					contentValues6);
		}

		sqLiteDatabase.close();
	}

	public void updateFrequency(String freqName, String status) {
		ContentValues cv = new ContentValues();
		cv.put("is_selected", status);
		sqLiteDatabase = dataHelper.getWritableDatabase();
		
		sqLiteDatabase.update(DataHelper.FREQUENCY_TABLE, cv,
				"frequency_day = ?", new String[] { freqName });
		sqLiteDatabase.close();
	}

	public void updateActivity(String freqName, String status) {
		ContentValues cv = new ContentValues();
		cv.put("is_selected", status);
		sqLiteDatabase = dataHelper.getWritableDatabase();
	
		sqLiteDatabase.update(DataHelper.ACTIVITY_TABLE, cv,
				"activity_name = ?", new String[] { freqName });
		sqLiteDatabase.close();
	}

	public void removeAllFrequency() {
		sqLiteDatabase = dataHelper.getWritableDatabase();
		sqLiteDatabase
				.execSQL("delete from " + DataHelper.FREQUENCY_TABLE + "");
		sqLiteDatabase.close();
	}

	public ArrayList<TimerModal> getAllFrequency() {

		ArrayList<TimerModal> frequencyList = new ArrayList<TimerModal>();

		sqLiteDatabase = dataHelper.getReadableDatabase();

		Cursor cursor = sqLiteDatabase.rawQuery("select * from "
				+ DataHelper.FREQUENCY_TABLE + "", null);

		while (cursor.moveToNext()) {
			TimerModal timerModal = new TimerModal();
			if (cursor.getString(2).equals("true"))
				timerModal.setIsSelected(true);
			else
				timerModal.setIsSelected(false);

			timerModal.setTimerType(cursor.getString(1));
			frequencyList.add(timerModal);
		}
		cursor.close();
		sqLiteDatabase.close();

		return frequencyList;
	}
	
	public ArrayList<WorkOutModal> getAllActivity() {

		ArrayList<WorkOutModal> activityList = new ArrayList<WorkOutModal>();

		sqLiteDatabase = dataHelper.getReadableDatabase();

		Cursor cursor = sqLiteDatabase.rawQuery("select * from "
				+ DataHelper.ACTIVITY_TABLE + "", null);

		while (cursor.moveToNext()) {

			WorkOutModal workOutModal = new WorkOutModal();

			if (cursor.getString(2).equals("true"))
				workOutModal.setSelected(true);
			else
				workOutModal.setSelected(false);

			workOutModal.setWorkOutTitle(cursor.getString(1));
			activityList.add(workOutModal);
		}
		cursor.close();
		sqLiteDatabase.close();

		return activityList;
	}

	public ArrayList<HashMap<String, String>> getWorkoutHistory() {

		ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();

		sqLiteDatabase = dataHelper.getReadableDatabase();

		Cursor cursor = sqLiteDatabase.rawQuery("select * from "
				+ DataHelper.HISTORY_TABLE + "", null);

		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<>();
			map.put("workout_date", cursor.getString(1));
			map.put("workout_duration", cursor.getString(2));
			map.put("workout_type", cursor.getString(3));
			map.put("workout_effort", cursor.getString(4));
			arrayList.add(map);
		}
		cursor.close();
		sqLiteDatabase.close();

		return arrayList;
	}

}
