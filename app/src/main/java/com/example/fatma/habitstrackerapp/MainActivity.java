package com.example.fatma.habitstrackerapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.fatma.habitstrackerapp.data.HabitContract.HabitEntry;
import com.example.fatma.habitstrackerapp.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {
    /**
     * Database helper that will provide us access to the database
     */
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new HabitDbHelper(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        displayDataBaseInfo();
    }

    public Cursor read() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_FREQUENCY,
                HabitEntry.COLUMN_HABIT_NUM_TIMES
        };

        // Perform this raw SQL query "SELECT * FROM habits"
        // to get a Cursor that contains all rows from the habits table.
        Cursor cursor = db.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
        return cursor;
    }

    private void displayDataBaseInfo() {
        Cursor cursor = read();
        TextView displayInfo = (TextView) findViewById(R.id.display_data);

        try {
            displayInfo.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");
            displayInfo.append(HabitEntry._ID + "    " + HabitEntry.COLUMN_HABIT_NAME + "    " +
                    HabitEntry.COLUMN_HABIT_FREQUENCY + "    " + HabitEntry.COLUMN_HABIT_NUM_TIMES + "\n");
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int freqColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_FREQUENCY);
            int numTimesColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NUM_TIMES);
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentId = cursor.getInt(idColumnIndex);
                String currentname = cursor.getString(nameColumnIndex);
                String currentfreq = cursor.getString(freqColumnIndex);
                int currrentNum = cursor.getInt(numTimesColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayInfo.append("\n" + currentId + "    \t\t    " + currentname + "    \t\t   " + currentfreq + "   \t\t\t     " + currrentNum);
            }
        } finally {
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.inset_dummy_data) {
            Intent intent = new Intent(MainActivity.this, EditeDatabase.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
