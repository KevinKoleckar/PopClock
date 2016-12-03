package com.koleckar.popclock;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.RemoteViews;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Kevin Koleckar.
 */
class MyTimer {
    private RemoteViews remoteViews;
    private Context context;
    private AppWidgetManager appWidgetManager;
    private ComponentName thisWidget;

    MyTimer(Context context) {

        appWidgetManager = AppWidgetManager.getInstance(context);

        this.context = context;

        remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_main);

        thisWidget = new ComponentName(context, MyAppWidgetProvider.class);

    }

    private static String loadDelimPref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.koleckar.popclock.MyAppWidgetProvider", 4);
        String delim = prefs.getString("delimiter", null);
        if (delim != null) {
            return delim;
        } else {
            return context.getString(R.string.delimiter);
        }
    }

    private static String loadColorPref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.koleckar.popclock.MyAppWidgetProvider", 4);
        String color = prefs.getString("color", null);
        if (color != null) {
            return color;
        } else {
            return "BLACK";
        }
    }

    private static String loadSizePref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.koleckar.popclock.MyAppWidgetProvider", 4);
        String size = prefs.getString("size", null);
        if (size != null) {
            return size;
        } else {
            return "50";
        }
    }

    private static String pad(int c) {
        if (c >= 10) {
            return EnglishNumberToWords.convertThisNumber(c);
        } else if (c == 0) {
            return "o'clock";
        } else {
            return "o'" + EnglishNumberToWords.convertThisNumber(c);
        }
    }

    synchronized void runAndUpdateTheWidget() {
        String delim = loadDelimPref(context);
        String color = loadColorPref(context);
        String size = loadSizePref(context);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (final int appWidgetId : allWidgetIds) {

            System.out.println("UPDATING......" + Arrays.toString(getTodaysTime(delim)) + " ID = "
                    + appWidgetId);

            remoteViews.setImageViewBitmap(R.id.imageView_txt,
                    buildUpdate(getTodaysTime(delim), color, size));
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        }

    }

    Bitmap buildUpdate(String[] time, String color, String textSize) {
        int bmpWidth = 500;//250;
        int bmpHeight = 250;//100;
        Bitmap myBitmap = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
        Canvas myCanvas = new Canvas(myBitmap);
        Paint paint = new Paint();
//#################################################################################################
//###### TODO set your font
//#################################################################################################
        Typeface clock = Typeface.createFromAsset(context.getAssets(), "myFont.ttf");
//#################################################################################################
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(clock);
        paint.setStyle(Paint.Style.FILL);

        switch (color) {
            case "BLACK":
                paint.setColor(Color.BLACK);
                break;
            case "RED":
                paint.setColor(Color.RED);
                break;
            case "BLUE":
                paint.setColor(Color.BLUE);
                break;
        }

        paint.setTextSize(Integer.parseInt(textSize));
        paint.setTextAlign(Paint.Align.CENTER);
        myCanvas.drawText(time[0] + time[1], bmpWidth / 2, bmpHeight / 3,
                paint);
        myCanvas.drawText(time[2], bmpWidth / 2, 2 * bmpHeight / 3,
                paint);

        return myBitmap;
    }

    String[] getTodaysTime(String delim) {
        final Calendar c = Calendar.getInstance();
        int hour = convertToNormal(c.get(Calendar.HOUR_OF_DAY));
        int minute = c.get(Calendar.MINUTE);
        //int seconds = c.get(Calendar.SECOND);
        String hours = EnglishNumberToWords.convertThisNumber(hour);
        String mins = pad(minute);
        return new String[]{
                hours + " ",
                delim,
                mins
        };
    }

    private int convertToNormal(int hour) {
        if (hour > 12) {
            hour = hour - 12;
        }
        return hour;
    }

}

