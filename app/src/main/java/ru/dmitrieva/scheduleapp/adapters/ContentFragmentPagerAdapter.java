package ru.dmitrieva.scheduleapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.dmitrieva.scheduleapp.fragments.DayFragment;
import ru.dmitrieva.scheduleapp.models.Day;
import ru.dmitrieva.scheduleapp.utils.DataConverter;

public class ContentFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Day> dayList = new ArrayList<>();

    public ContentFragmentPagerAdapter(FragmentManager fragmentManager, ArrayList<Day> dayList) {
        super(fragmentManager);
        this.dayList = dayList;
    }

    @Override
    public Fragment getItem(int position) {
        return DayFragment.newInstance(dayList.get(position));
    }

    @Override
    public int getCount() {
        return dayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return DataConverter.weekDayNumberToTitle(dayList.get(position).getWeekDay());
    }
}
