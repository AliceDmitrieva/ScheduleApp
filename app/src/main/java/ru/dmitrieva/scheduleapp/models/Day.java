package ru.dmitrieva.scheduleapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Day implements Parcelable, Comparable<Day> {

    private int weekDay;
    private List<Section> sectionList;

    public Day(int weekDay, List<Section> sectionList) {
        this.weekDay = weekDay;
        this.sectionList = sectionList;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    protected Day(Parcel in) {
        weekDay = in.readInt();
        if (in.readByte() == 0x01) {
            sectionList = new ArrayList<Section>();
            in.readList(sectionList, Section.class.getClassLoader());
        } else {
            sectionList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(weekDay);
        if (sectionList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(sectionList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Day> CREATOR = new Parcelable.Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

    @Override
    public int compareTo(@NonNull Day o) {
        return Integer.compare(weekDay, o.weekDay);
    }
}
