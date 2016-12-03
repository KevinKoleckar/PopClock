package com.koleckar.popclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by test on 5/21/14.
 */
public class RepeatingAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MyTimer myTimer = new MyTimer(context);
        myTimer.runAndUpdateTheWidget();
    }
}
