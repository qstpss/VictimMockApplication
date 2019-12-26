package com.qstpss.victimmockapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmManagerBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);


        //todo implement this value customizable with host application
        int repeatInterval = 1000 * 60 * 1;
        long startTime = System.currentTimeMillis() + 1000 * 5;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, startTime, repeatInterval, pendingIntent);
        this.finish();
    }
}
