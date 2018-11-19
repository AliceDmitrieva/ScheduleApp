package ru.dmitrieva.scheduleapp;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.dmitrieva.scheduleapp.models.Day;
import ru.dmitrieva.scheduleapp.models.Section;
import ru.dmitrieva.scheduleapp.utils.NetworkUtils;

public class GettingScheduleAsyncTask extends AsyncTask<Void, Void, List<Day>> {

    private GettingScheduleListener listener;
    @Nullable
    private Exception occurredError;

    public interface GettingScheduleListener {
        void onDataRequestSuccess(List<Day> result);
        void onDataRequestError(Exception exception);
    }

    public GettingScheduleAsyncTask(GettingScheduleListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Day> doInBackground(Void... arg) {
        String url = "https://sample.fitnesskit-admin.ru/schedule/get_group_lessons_v2/4/";
        String jsonString = NetworkUtils.readUrl(url);

        Gson gson = new Gson();
        Type type = new TypeToken<List<Section>>() {
        }.getType();

        try {
            List<Section> sectionList = gson.fromJson(jsonString, type);
            Map<Integer, List<Section>> dataMap = new HashMap<>();

            for (Section section : sectionList) {
                List<Section> sortedList = dataMap.get(section.getWeekDay());
                if (sortedList == null) {
                    sortedList = new ArrayList<>();
                    dataMap.put(section.getWeekDay(), sortedList);
                }
                sortedList.add(section);
            }

            List<Day> dayList = new ArrayList<>(dataMap.size());
            for (Map.Entry<Integer, List<Section>> entry : dataMap.entrySet()) {
                dayList.add(new Day(entry.getKey(), entry.getValue()));
            }
            Collections.sort(dayList);
            return dayList;

        } catch (Exception e) {
            occurredError = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Day> result) {
        if (occurredError == null) {
            listener.onDataRequestSuccess(result);
        } else {
            listener.onDataRequestError(occurredError);
        }
    }
}

