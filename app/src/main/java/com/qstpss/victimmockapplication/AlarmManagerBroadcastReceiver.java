package com.qstpss.victimmockapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String bootAction = "android.intent.action.BOOT_COMPLETED";
        if (action != null && action.equalsIgnoreCase(bootAction)) {
            Intent intent2 = new Intent(context, AlarmManagerBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent2, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);

            int repeatInterval = 1000 * 60 * 5;
            long startTime = System.currentTimeMillis();
            alarmManager.setInexactRepeating(AlarmManager.RTC, startTime, repeatInterval, pendingIntent);

        } else {
            //TODO : add all supported methods in IClient and ClientImpl, make a jar lib and call REST using this jar lib
            //add model package
        }

    }
}
