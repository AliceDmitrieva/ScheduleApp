package ru.dmitrieva.scheduleapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import ru.dmitrieva.scheduleapp.fragments.WeekFragment;
import ru.dmitrieva.scheduleapp.models.Day;
import ru.dmitrieva.scheduleapp.utils.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements GettingScheduleAsyncTask.GettingScheduleListener {

    private static final String TAG = "logs";
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        new GettingScheduleAsyncTask(MainActivity.this).execute();
    }

    @Override
    public void onDataRequestSuccess(List<Day> result) {
        databaseHelper.addData(result);
        changeFragment(result);
    }

    @Override
    public void onDataRequestError(Exception error) {
        Log.e(TAG, "Error occurred while retrieving data from the server", error);
        List<Day> data = databaseHelper.getData();
        changeFragment(data);
    }

    public void changeFragment(List<Day> data) {
        WeekFragment fragment = WeekFragment.newInstance(data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
