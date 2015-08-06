package com.munzbit.notarius.datamanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ratufa.Manish on 8/5/2015.
 */
public class DataHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "NotariusDB";

    public static final int DB_VERSION = 1;

    public DataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table WorkOutHistory(id integer primary key AUTOINCREMENT, workout_date text,workout_duration text,workout_type text, workout_effort text)");

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
