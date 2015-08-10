package com.munzbit.notarius.datamanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ratufa.Manish on 8/5/2015.
 */
public class DataManager  {

    private DataHelper dataHelper;

    private SQLiteDatabase sqLiteDatabase;

    public DataManager(Context context){

        dataHelper = new DataHelper(context,DataHelper.DB_NAME,null,DataHelper.DB_VERSION);
    }

    public void insertData(String date,String duration,String type, String effort){

        sqLiteDatabase = dataHelper.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put(DataHelper.WORKOUT_DATE,date);
        contentValues.put(DataHelper.WORKOUT_DURATION,duration);
        contentValues.put(DataHelper.WORKOUT_TYPE,type);
        contentValues.put(DataHelper.WORKOUT_EFFORT,effort);
        sqLiteDatabase.insert(DataHelper.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();

    }

    public void insertFrequency(String freqName){
        sqLiteDatabase = dataHelper.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put(DataHelper.FREQUENCY_DAY,freqName);
        sqLiteDatabase.insert(DataHelper.FREQUENCY_TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public void removeFrequency(String freqName){
        sqLiteDatabase = dataHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from " + DataHelper.FREQUENCY_TABLE_NAME + " where " + DataHelper.FREQUENCY_DAY + "='" + freqName + "'");
        sqLiteDatabase.close();
    }
    public void removeAllFrequency(){
        sqLiteDatabase = dataHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+DataHelper.FREQUENCY_TABLE_NAME+"");
        sqLiteDatabase.close();
    }

    public ArrayList<String> getAllFrequency(){

        ArrayList<String> frequencyList = new ArrayList<String>();

        sqLiteDatabase = dataHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+DataHelper.FREQUENCY_TABLE_NAME+"",null);

        while (cursor.moveToNext()){
            frequencyList.add(cursor.getString(1));
        }
        cursor.close();
        sqLiteDatabase.close();

        return frequencyList;
    }

    public ArrayList<HashMap<String,String>> getWorkoutHistory(){

        ArrayList<HashMap<String,String>> arrayList = new ArrayList<HashMap<String,String>>();

        sqLiteDatabase = dataHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+DataHelper.TABLE_NAME+"",null);

        while (cursor.moveToNext()){
            HashMap<String,String> map = new HashMap<>();
            map.put("workout_date",cursor.getString(1));
            map.put("workout_duration",cursor.getString(2));
            map.put("workout_type",cursor.getString(3));
            map.put("workout_effort",cursor.getString(4));
            arrayList.add(map);
        }
        cursor.close();
        sqLiteDatabase.close();

        return arrayList;
    }

}
