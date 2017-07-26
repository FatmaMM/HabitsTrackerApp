package com.example.fatma.habitstrackerapp.data;

import android.provider.BaseColumns;

/**
 * Created by Fatma on 11/07/2017.
 */

public class HabitContract {
    private HabitContract() {
    }

    public final static class HabitEntry implements BaseColumns {
        public final static String TABLE_NAME = "habits";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HABIT_NAME = "name";
        public final static String COLUMN_HABIT_FREQUENCY = "frequency";
        public final static String COLUMN_HABIT_NUM_TIMES = "number_of_times";

        public final static int GENDER_DAILY = 0;
        public final static int GENDER_MONTHLY = 2;
        public final static int GENDER_WEEKLY = 1;
    }
}
