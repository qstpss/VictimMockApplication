package com.qstpss.victimmockapplication.job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.StrictMode;

import com.qstpss.victimmockapplication.mocks.MuteMedia;
import com.qstpss.victimmockapplication.mocks.PopupMessage;
import com.qstpss.victimmockapplication.mocks.Vibration;
import com.qstpss.victimmockapplication.model.MockEvent;
import com.qstpss.victimmockapplication.model.Status;
import com.qstpss.victimmockapplication.model.Type;
import com.qstpss.victimmockapplication.webclient.ClientImpl;
import com.qstpss.victimmockapplication.webclient.IClient;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Response;

public class MockJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        allowToExecuteInMainThread();
        IClient client = new ClientImpl();
        try {
            client.getStartedEvents();
        } catch (IOException e) {
            finishJob(params);
            return false;
        }
        Response response = client.getResponse();
        if (response.isSuccessful()) {

            processActiveEvents(response, this);
        }
        finishJob(params);
        return true;
    }

    private void finishJob(JobParameters params) {
        jobFinished(params, false);
        onStopJob(params);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        MockJobServiceManager mockJobServiceManager =
                new MockJobServiceManager(this);
        mockJobServiceManager.startJob();
        return true;
    }

    private void processActiveEvents(Response response, Context context) {
        List<MockEvent> body = (List<MockEvent>) response.body();
        body.forEach(mockEvent -> doMock(context, mockEvent));
        /*.filter(mockEvent -> mockEvent.getStatus() == Status.PENDING)*/
        disableMocks(body);
    }

    private void doMock(Context context, MockEvent mockEvent) {
        if (mockEvent.getStatus() == Status.IN_PROGRESS) {
            startMock(context, mockEvent);
        } else {
            IClient client = new ClientImpl();
            try {
                client.startMockEvent(mockEvent);
            } catch (IOException e) {
                return;
            }
            if (client.getResponse().isSuccessful()) {
                startMock(context, mockEvent);
            }
        }
    }

    private void startMock(Context context, MockEvent mockEvent) {
        switch (mockEvent.getType()) {
            case MUTE_MEDIA:
                MuteMedia.MOCK.startMock(context);
                break;
            case POPUP_MESSAGE:
                PopupMessage.MOCK.setMessage(mockEvent.getMessage());
                PopupMessage.MOCK.startMock(context);
                break;
            case VIBRATION:
                Vibration.MOCK.startMock(context);
                break;
        }
    }

    private void disableMocks(List<MockEvent> body) {
        List<Type> mockTypes =
                body.stream()
                        .map(MockEvent::getType)
                        .collect(Collectors.toList());

        if (!mockTypes.contains(Type.VIBRATION)) {
            Vibration.MOCK.stopMock();
        }
        if (!mockTypes.contains(Type.MUTE_MEDIA)) {
            MuteMedia.MOCK.stopMock();
        }
        if (!mockTypes.contains(Type.POPUP_MESSAGE)) {
            PopupMessage.MOCK.stopMock();
        }
    }

    private void allowToExecuteInMainThread() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
