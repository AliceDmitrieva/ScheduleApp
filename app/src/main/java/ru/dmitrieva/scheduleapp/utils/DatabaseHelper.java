package ru.dmitrieva.scheduleapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import ru.dmitrieva.scheduleapp.models.Day;
import ru.dmitrieva.scheduleapp.models.Section;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scheduleInfoManager";

    private interface DayTableColumns extends BaseColumns {

        String KEY_WEEK_DAY = "week_day";

        String TABLE_WEEK_DAYS = "week_days";

        String CREATE_TABLE_DAYS = "CREATE TABLE " + TABLE_WEEK_DAYS
                + "(" + _ID + " INTEGER PRIMARY KEY,"
                + KEY_WEEK_DAY + " INTEGER" + ")";
    }

    private interface SectionsTableColumns extends BaseColumns {

        String KEY_DAY_ID = "day_id";
        String KEY_NAME = "name";
        String KEY_START_TIME = "start_time";
        String KEY_END_TIME = "end_time";
        String KEY_TEACHER = "teacher";
        String KEY_PLACE = "place";
        String KEY_DESCRIPTION = "description";

        String TABLE_SECTIONS = "sections";

        String CREATE_TABLE_SECTIONS = "CREATE TABLE " + TABLE_SECTIONS
                + "(" + _ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_START_TIME + " TEXT,"
                + KEY_END_TIME + " TEXT,"
                + KEY_TEACHER + " TEXT,"
                + KEY_PLACE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DAY_ID + " INTEGER,"
                + " FOREIGN KEY (" + KEY_DAY_ID + ") REFERENCES " + DayTableColumns.TABLE_WEEK_DAYS + "(" + DayTableColumns._ID + "));";
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DayTableColumns.CREATE_TABLE_DAYS);
        db.execSQL(SectionsTableColumns.CREATE_TABLE_SECTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DayTableColumns.TABLE_WEEK_DAYS);
        db.execSQL("DROP TABLE IF EXISTS " + SectionsTableColumns.TABLE_SECTIONS);
        onCreate(db);
    }

    public void clearDatabase() {
        String clearTableWeekDays = "DELETE FROM " + DayTableColumns.TABLE_WEEK_DAYS;
        String clearTableSections = "DELETE FROM " + SectionsTableColumns.TABLE_SECTIONS;
        getReadableDatabase().execSQL(clearTableWeekDays);
        getReadableDatabase().execSQL(clearTableSections);
    }

    public void addData(List<Day> weekDaysData) {
        clearDatabase();
        SQLiteDatabase database = this.getWritableDatabase();

        for (Day day : weekDaysData) {
            ContentValues dayValues = new ContentValues();
            dayValues.put(DayTableColumns.KEY_WEEK_DAY, day.getWeekDay());
            long dayId = database.insert(DayTableColumns.TABLE_WEEK_DAYS, null, dayValues);

            for (Section sections : day.getSectionList()) {
                ContentValues sectionValues = new ContentValues();
                sectionValues.put(SectionsTableColumns.KEY_NAME, sections.getName());
                sectionValues.put(SectionsTableColumns.KEY_START_TIME, sections.getStartTime());
                sectionValues.put(SectionsTableColumns.KEY_END_TIME, sections.getEndTime());
                sectionValues.put(SectionsTableColumns.KEY_TEACHER, sections.getTeacher());
                sectionValues.put(SectionsTableColumns.KEY_PLACE, sections.getPlace());
                sectionValues.put(SectionsTableColumns.KEY_DESCRIPTION, sections.getDescription());
                sectionValues.put(SectionsTableColumns.KEY_DAY_ID, dayId);

                database.insert(SectionsTableColumns.TABLE_SECTIONS, null, sectionValues);
            }
        }
    }

    public List<Day> getData() {
        SQLiteDatabase database = this.getReadableDatabase();
        List<Day> scheduleData = new ArrayList<>();

        Cursor dayCursor = database.query(DayTableColumns.TABLE_WEEK_DAYS, null, null, null,
                null, null, null);

        for (dayCursor.moveToFirst(); !dayCursor.isAfterLast(); dayCursor.moveToNext()) {
            long dayId = dayCursor.getLong(dayCursor.getColumnIndex(DayTableColumns._ID));
            int weekDay = dayCursor.getInt(dayCursor.getColumnIndex(DayTableColumns.KEY_WEEK_DAY));

            List<Section> list = new ArrayList<>();
            Cursor sectionsCursor = database.query(SectionsTableColumns.TABLE_SECTIONS,
                    null,
                    SectionsTableColumns.KEY_DAY_ID + " = " + dayId,
                    null,
                    null,
                    null,
                    null);

            for (sectionsCursor.moveToFirst(); !sectionsCursor.isAfterLast(); sectionsCursor.moveToNext()) {
                String name = sectionsCursor.getString(sectionsCursor.getColumnIndex(SectionsTableColumns.KEY_NAME));
                String startTime = sectionsCursor.getString(sectionsCursor.getColumnIndex(SectionsTableColumns.KEY_START_TIME));
                String endTime = sectionsCursor.getString(sectionsCursor.getColumnIndex(SectionsTableColumns.KEY_END_TIME));
                String teacher = sectionsCursor.getString(sectionsCursor.getColumnIndex(SectionsTableColumns.KEY_TEACHER));
                String place = sectionsCursor.getString(sectionsCursor.getColumnIndex(SectionsTableColumns.KEY_PLACE));
                String description = sectionsCursor.getString(sectionsCursor.getColumnIndex(SectionsTableColumns.KEY_DESCRIPTION));

                list.add(new Section(name, startTime, endTime, teacher, place, description));
            }

            scheduleData.add(new Day(weekDay, list));
        }

        return scheduleData;
    }
}



