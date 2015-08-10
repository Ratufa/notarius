package com.munzbit.notarius.datamanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ratufa.Manish on 8/5/2015.
 */
public class DataHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "NotariusDB";

    public static final String WORKOUT_DATE="workout_date";

    public static final String WORKOUT_DURATION="workout_duration";

    public static final String WORKOUT_TYPE="workout_type";

    public static final String WORKOUT_EFFORT="workout_effort";

    public static final String TABLE_NAME="WorkOutHistory";

    public static final String FREQUENCY_TABLE_NAME="FrequencyTable";

    public static final String FREQUENCY_DAY="frequency_day";

    public static final int DB_VERSION = 2;

    public DataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+FREQUENCY_TABLE_NAME+"(id integer primary key AUTOINCREMENT, "+FREQUENCY_DAY+" text)");
        db.execSQL("create table "+TABLE_NAME+"(id integer primary key AUTOINCREMENT, "+WORKOUT_DATE+" text,"+WORKOUT_DURATION+" text,"+WORKOUT_TYPE+" text, "+WORKOUT_EFFORT+" text)");

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS WorkOutHistory");

        onCreate(db);
    }


}
