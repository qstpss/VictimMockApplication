package com.qstpss.victimmockapplication.mocks;

import android.content.Context;
import android.os.Vibrator;
public enum Vibration implements Mock {
    MOCK;

    private Vibrator vibrator;
    private static long[] pattern = {0, 4000, 10000, 2000};

    @Override
    public void startMock(Context context) {
        stopMock();
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(pattern, 2);
        }
    }

    @Override
    public void stopMock() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}
