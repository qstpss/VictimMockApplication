package com.qstpss.victimmockapplication.job;

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
                .setMinimumLatency(60000)
                .setOverrideDeadline(70000)
                .setPersisted(true)//enhance to probability to run task after reboot because boot receiver doesn't work well
                .build();
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(jobInfo);
    }
}
