package com.qstpss.victimmockapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;

import com.qstpss.victimmockapplication.model.MockEvent;
import com.qstpss.victimmockapplication.model.Type;
import com.qstpss.victimmockapplication.webclient.ClientImpl;
import com.qstpss.victimmockapplication.webclient.IClient;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;

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
            allowToExecuteInMainThread();
            IClient client = new ClientImpl();
            Call<List<MockEvent>> startedEvents;
            try {
                startedEvents = client.getStartedEvents();
            } catch (IOException e) {
                //TODO
            }
            Response response = client.getResponse();
            if (response.isSuccessful()) {
                processActiveEvents(response);

            }

        }

    }

    private void processActiveEvents(Response response) {
        List<MockEvent> body = (List<MockEvent>) response.body();
        body.stream()
                .map(MockEvent::getType)
                .forEach(eventType -> {
            switch (eventType) {
                case VIBRATION:
                    vibrate();
                    break;
                case MUTE_ALARM:
                    muteAlarm();
                    break;
                case MUTE_MEDIA:
                    muteMedia();
                    break;
            }
        });
        if (body.isEmpty()) {
            //todo shutdown all
        }
    }

    private void muteMedia() {
    }

    private void muteAlarm() {
    }

    private void vibrate() {
        //todo is running? if yes do or not
    }

    private void allowToExecuteInMainThread() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
