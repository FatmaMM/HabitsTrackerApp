package com.example.fatma.habitstrackerapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.fatma.habitstrackerapp.data.HabitContract.HabitEntry;

/**
 * Created by Fatma on 11/07/2017.
 */

public class HabitDbHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = HabitDbHelper.class.getSimpleName();
    /**
     * Name of the database file
     */
    private static final String DATABADE_NAME = "shelter.db";
    /**
     * Database version. If you change the database schema, you must increment the database version.
     **/
    private static final int DATABASE_VERSION = 1;

   public HabitDbHelper(Context context) {
        super(context, DATABADE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HABIT_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + "( "
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL,"
                + HabitEntry.COLUMN_HABIT_FREQUENCY + " INTEGER NOT NULL,"
                + HabitEntry.COLUMN_HABIT_NUM_TIMES + " INTEGER NOT NULL DEFAULT 1);";
        Log.v(LOG_TAG, SQL_CREATE_HABIT_TABLE);
        db.execSQL(SQL_CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
