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
        contentValues.put("workout_date",date);
        contentValues.put("workout_duration",duration);
        contentValues.put("workout_type",type);
        contentValues.put("workout_effort",effort);
        sqLiteDatabase.insert("WorkOutHistory", null, contentValues);
        sqLiteDatabase.close();

    }

    public ArrayList<HashMap<String,String>> getWorkoutHistory(){

        ArrayList<HashMap<String,String>> arrayList = new ArrayList<HashMap<String,String>>();

        sqLiteDatabase = dataHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from WorkOutHistory",null);

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
