package com.example.fatma.habitstrackerapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fatma.habitstrackerapp.data.HabitContract.HabitEntry;
import com.example.fatma.habitstrackerapp.data.HabitDbHelper;

public class EditeDatabase extends AppCompatActivity {
    /**
     * EditText field to enter the habit's name
     */
    private EditText mNameEditText;

    /**
     * EditText field to enter the habit's number of times
     */
    private EditText mNumTimesEditText;


    /**
     * EditText field to enter the habit's frequent
     */
    private Spinner mFrequencySpinner;

    /**
     * Frequency of the habit. The possible values are:
     * 0 for daily gender, 1 for weekly, 2 for monthly.
     */
    private int freq = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_database);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditeDatabase.this, MainActivity.class);
                startActivity(intent);
            }
        });
// Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_habit_name);
        mNumTimesEditText = (EditText) findViewById(R.id.num_times);
        mFrequencySpinner = (Spinner) findViewById(R.id.spinner_freq);
        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.frequency_option, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mFrequencySpinner.setAdapter(spinnerAdapter);
        mFrequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.Repeats_monthly))) {
                        freq = HabitEntry.GENDER_MONTHLY;
                    } else if (selection.equals(getString(R.string.Repeats_weekly))) {
                        freq = HabitEntry.GENDER_WEEKLY;
                    } else {
                        freq = HabitEntry.GENDER_DAILY;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                freq = 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:
                insertData();
                finish();
                return true;
            case R.id.action_delete:
                finish();
                startActivity(getIntent());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertData() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String name = mNameEditText.getText().toString().trim();
        String numberOfTimes = mNumTimesEditText.getText().toString().trim();
        int num = 1;
        if (!"".equals(numberOfTimes))
            num = Integer.parseInt(numberOfTimes);

        // Create a ContentValues object where column names are the keys,
        // and habit attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, name);
        values.put(HabitEntry.COLUMN_HABIT_FREQUENCY, freq);
        values.put(HabitEntry.COLUMN_HABIT_NUM_TIMES, num);
        // Create database helper
        HabitDbHelper mDbHelper = new HabitDbHelper(this);
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving Habit", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
}