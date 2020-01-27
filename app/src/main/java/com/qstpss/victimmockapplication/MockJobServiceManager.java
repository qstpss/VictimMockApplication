package com.qstpss.victimmockapplication;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

public class MockJobServiceManager {

    private Context context;

    public MockJobServiceManager(Context context) {
        this.context = context;
    }

    public void startJob() {
        ComponentName serviceName = new ComponentName(context, MockJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(1, serviceName)
                //.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setMinimumLatency(5000)
                .setOverrideDeadline(10000)
                .build();
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(jobInfo);
    }
}
