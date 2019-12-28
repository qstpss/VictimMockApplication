package com.qstpss.victimmockapplication.mocks;

import android.content.Context;
import android.widget.Toast;

public enum PopupMessage implements Mock {
    MOCK;

    private volatile boolean isStarted;
    private String textMessage;

    @Override
    public void startMock(Context context) {
        stopMock();
        isStarted = true;
        Toast message = Toast.makeText(context, textMessage, Toast.LENGTH_LONG);
        new Thread(() -> {
            while (isStarted) {
                message.show();
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    //it never happens
                }
            }
        }).start();
    }

    @Override
    public void stopMock() {
        if (isStarted) {
            isStarted = false;
        }
    }

    public void setMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
