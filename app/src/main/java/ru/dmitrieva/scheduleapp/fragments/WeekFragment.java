package ru.dmitrieva.scheduleapp.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.dmitrieva.scheduleapp.R;
import ru.dmitrieva.scheduleapp.adapters.ContentFragmentPagerAdapter;
import ru.dmitrieva.scheduleapp.models.Day;

public class WeekFragment extends Fragment {

    private static final String ARGUMENT_DAY_LIST = "day list";
    private ViewPager viewPager;

    public static WeekFragment newInstance(List<Day> dayList) {
        WeekFragment fragment = new WeekFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_DAY_LIST, new ArrayList<>(dayList));
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.week_fragment, null);
        viewPager = view.findViewById(R.id.viewpager);

        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("arguments == null");
        }

        ArrayList<Day> dayList = arguments.getParcelableArrayList(ARGUMENT_DAY_LIST);
        if (dayList == null) {
            throw new IllegalArgumentException("day list == null");
        }

        ContentFragmentPagerAdapter contentFragmentPagerAdapter = new ContentFragmentPagerAdapter(getChildFragmentManager(), dayList);
        viewPager.setAdapter(contentFragmentPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
