package com.qstpss.victimmockapplication.mocks;

import android.content.Context;
import android.os.Vibrator;
public enum Vibration implements Mock {
    MOCK;

    private Vibrator vibrator;
    private static long[] pattern = {0, 2000, 10000, 2000};

    @Override
    public void startMock(Context context) {
        /*isStarted = true;
        thread = new Thread(() -> {
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator.hasVibrator()) {
                while (isStarted) {
                    vibrator.vibrate(pattern, 2);
                }
                vibrator.cancel();
            }
        });*/
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(pattern, 2);
        }
    }

    @Override
    public void stopMock() {
        vibrator.cancel();

    }
}
