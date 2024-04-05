package com.example.todolist;

import android.icu.util.Calendar;

public class DateFormatter {
    public static String dateFormatFrench(String date){
        String[] str = date.split("-");
        return str[2] + "/" + str[1] + "/" + str[0];
    }

    public static String monthFormat(int month){
        return (month < 10) ? "0" + month : String.valueOf(month);
    }

    public static String dayFormat(int dayOfMonth){
        return (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
    }

    public static String todayDate() {
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1;  // Month is zero-based
        int dayOfMonth = today.get(Calendar.DAY_OF_MONTH);
        return toDate(year, month, dayOfMonth);
    }

    public static String toDate(int year, int month, int dayOfMonth){
        return year + "-" + DateFormatter.monthFormat(month) + "-" + DateFormatter.dayFormat(dayOfMonth);
    }

}
