package com.example.taskmanagerproject.Utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    public static final int START_DATE = 1900;
    public static final int END_DATE = 2020;
    public static final int START_TIME = 0;
    public static final int END_TIME = 24;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Date randomDate() {


        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(START_DATE, END_DATE);
        int hour = randBetween(START_TIME, END_TIME);
        int Month = randBetween(1, 12);
        int minute = randBetween(1, 59);
        int date = randBetween(1, 30);
        int second = randBetween(1, 59);

        gc.set(year, Month, date, hour, minute, second);


        return gc.getTime();


    }


    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));


    }

    public static String getCurrentDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        return dateFormat.format(date);


    }

    public static String getCurrentTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(date);


    }

    public static Date getCurrentDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getCurrentTime(String str) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        try {
            return format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
