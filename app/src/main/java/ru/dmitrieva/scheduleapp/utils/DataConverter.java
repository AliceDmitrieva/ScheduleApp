package ru.dmitrieva.scheduleapp.utils;

public class DataConverter {

    public static String weekDayNumberToTitle(int number) {
        String weekDayTitle = "";

        switch (number) {
            case 1:
                weekDayTitle = "Понедельник";
                break;
            case 2:
                weekDayTitle = "Вторник";
                break;
            case 3:
                weekDayTitle = "Среда";
                break;
            case 4:
                weekDayTitle = "Четверг";
                break;
            case 5:
                weekDayTitle = "Пятница";
                break;
            case 6:
                weekDayTitle = "Суббота";
                break;
            case 7:
                weekDayTitle = "Воскресенье";
                break;
        }
        return weekDayTitle;
    }
}
