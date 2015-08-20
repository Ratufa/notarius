package com.munzbit.notarius.datamanager;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by Ratufa.Manish on 8/5/2015.
 */
public class DataHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "NotariusDB";

    public static final String WORKOUT_DATE="workout_date";

    public static final String WORKOUT_DURATION="workout_duration";

    public static final String WORKOUT_TYPE="workout_type";

    public static final String WORKOUT_EFFORT="workout_effort";

    public static final String HISTORY_TABLE="WorkOutHistory";
    
    public static final String ACTIVITY_TABLE="WorkOutList";

    public static final String FREQUENCY_TABLE="FrequencyTable";

    public static final String FREQUENCY_DAY="frequency_day";
    
    public static final String ACTIVITY_NAME="activity_name";

    public static final int DB_VERSION = 4;

    public DataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ACTIVITY_TABLE+"(id integer primary key AUTOINCREMENT, "+ACTIVITY_NAME+" text, is_selected text)");
        db.execSQL("create table "+FREQUENCY_TABLE+"(id integer primary key AUTOINCREMENT, "+FREQUENCY_DAY+" text, is_selected text)");
        db.execSQL("create table "+HISTORY_TABLE+"(id integer primary key AUTOINCREMENT, "+WORKOUT_DATE+" text,"+WORKOUT_DURATION+" text,"+WORKOUT_TYPE+" text, "+WORKOUT_EFFORT+" text)");

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS WorkOutHistory");
        db.execSQL("DROP TABLE IF EXISTS FrequencyTable");
        db.execSQL("DROP TABLE IF EXISTS WorkOutList");

        onCreate(db);
    }


}
