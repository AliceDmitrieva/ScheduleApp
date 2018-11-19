package ru.dmitrieva.scheduleapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.dmitrieva.scheduleapp.R;
import ru.dmitrieva.scheduleapp.adapters.DayAdapter;
import ru.dmitrieva.scheduleapp.models.Day;
import ru.dmitrieva.scheduleapp.models.Section;

public class DayFragment extends Fragment {

    private static final String ARGUMENT_SECTION_LIST = "section list";

    public static DayFragment newInstance(Day day) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_SECTION_LIST, new ArrayList<>(day.getSectionList()));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.day_fragment, null);
        RecyclerView recyclerView = view.findViewById(R.id.content_fragment);

        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("arguments == null");
        }

        ArrayList<Section> sectionList = arguments.getParcelableArrayList(ARGUMENT_SECTION_LIST);
        if (sectionList == null) {
            throw new IllegalArgumentException("section list == null");
        }

        DayAdapter adapter = new DayAdapter(sectionList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
