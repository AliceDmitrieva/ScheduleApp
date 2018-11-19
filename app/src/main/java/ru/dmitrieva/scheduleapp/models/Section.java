package ru.dmitrieva.scheduleapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Section implements Parcelable {

    private String name;
    private String startTime;
    private String endTime;
    private String teacher;
    private String place;
    private String description;
    private int weekDay;

    public Section(String name, String startTime, String endTime, String teacher, String place, String description) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.teacher = teacher;
        this.place = place;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getPlace() {
        return place;
    }

    public String getDescription() {
        return description;
    }

    public int getWeekDay() {
        return weekDay;
    }

    protected Section(Parcel in) {
        name = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        teacher = in.readString();
        place = in.readString();
        description = in.readString();
        weekDay = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(teacher);
        dest.writeString(place);
        dest.writeString(description);
        dest.writeInt(weekDay);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Section> CREATOR = new Parcelable.Creator<Section>() {
        @Override
        public Section createFromParcel(Parcel in) {
            return new Section(in);
        }

        @Override
        public Section[] newArray(int size) {
            return new Section[size];
        }
    };
}
