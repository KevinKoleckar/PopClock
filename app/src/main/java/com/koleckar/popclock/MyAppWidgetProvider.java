package com.koleckar.popclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by test on 5/21/14.
 */
public class MyAppWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        Intent intent = new Intent(context, RepeatingAlarm.class);
        PendingIntent sender = PendingIntent
                .getBroadcast(context, 0, intent, 0);

        // We want <span id="IL_AD8" class="IL_AD">the alarm</span> to go off 1 seconds from now.
        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 1000;

        // <span id="IL_AD10" class="IL_AD">Schedule</span> the alarm!
        AlarmManager am = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME, firstTime, 60000,
                sender);

    }
}
