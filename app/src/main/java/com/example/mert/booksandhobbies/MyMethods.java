package com.example.mert.booksandhobbies;

import android.content.res.Resources;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mert on 17.02.2017.
 */

public class MyMethods {

    public static ArrayList<Thing> things = new ArrayList<Thing>();



    public static String minutes2TimeString (int minutes) {
        String time = "";

        int m = minutes%60;
        int h = minutes/60;
        if((h>=0 && h<=9)&&(m>=0 && m<=9))
            time = "0" + h + " hr. 0" + m + " min.";
        if((h>9)&&(m>=0 && m<=9))
            time = h + " hr. 0" + m + " min.";
        if((h>=0 && h<=9)&&(m>9))
            time = "0" + h + " hr. " + m + " min.";
        if((h>9)&&(m>9))
            time = h + " hr. " + m + " min.";

        return time;
    }

    public static String seconds2TimeString (int seconds) {
        String time = "";
        String ss="";
        String sm="";
        String sh="";

        int s = seconds%60;
        int m = (seconds/60)%60;
        int h = seconds/3600;

        if(h<10) sh="0"+h;
        else sh=""+h;

        if(m<10) sm="0"+m;
        else sm=""+m;

        if(s<10) ss="0"+s;
        else ss=""+s;

        time = sh + ":" + sm + ":" + ss;


        return time;
    }

    public static String getDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date = format.format(cal.getTime());
        return date;
    }



    public static String getAvgTime(int timeInMinutes, long start, long update) {

        int str = (int) start/86400000;
        int end = (int) update/86400000;
        double avg=0.0;

        if(end-str>0) {
            avg = ((double) timeInMinutes)/(end-str-1);
            return String.format("%.2f", avg);
        }


        return "0.00";
    }

    public static String getAvgPages(int pagesRead, long start, long update) {

        int str = (int) start/86400000;
        int end = (int) update/86400000;
        double avg=0.0;

        if(end-str>0) {
            avg = ((double) pagesRead)/(end-str-1);
            return String.format("%.2f", avg);
        }


        return "0.00";
    }


    public static void updateTheAveragesBook(TextView timeAverage, TextView pageAverage, int index) {
        Book bok = (Book) MyMethods.things.get(index);
        if(bok.getTimeSpent()!=0)
            timeAverage.setText("You have been reading this book for " + MyMethods.getAvgTime(bok.getTimeSpent(), bok.getTimeStarted(), bok.getTimeOfLastUpdate()) + " minutes a day.");
        if(bok.getPagesRead()!=0)
            pageAverage.setText("You have been reading " + MyMethods.getAvgPages(bok.getPagesRead(), bok.getTimeStarted(), bok.getTimeOfLastUpdate()) + " pages a day.");
    }

    public static void updateTheAveragesHobby(TextView timeAverage, int index) {
        Hobby bok = (Hobby) MyMethods.things.get(index);
        if(bok.getTimeSpent()!=0)
            timeAverage.setText("You have been doing this hobby for " + MyMethods.getAvgTime(bok.getTimeSpent(), bok.getTimeStarted(), bok.getTimeOfLastUpdate()) + " minutes a day.");
    }



}
