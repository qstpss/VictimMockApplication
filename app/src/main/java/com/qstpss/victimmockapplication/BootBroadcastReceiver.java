package com.qstpss.victimmockapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.qstpss.victimmockapplication.job.MockJobServiceManager;

public class BootBroadcastReceiver extends BroadcastReceiver {

    public BootBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        new MockJobServiceManager(context).startJob();
    }
}
