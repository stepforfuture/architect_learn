package com.test.architect_learn.keep_alive.job_sheduler;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

/**
 * 利用JobSheduler 进行进程拉活 注意在api level 21(android 5.0)之上才有用,这个整体来说没有看明白
 */
@TargetApi(21)
public class TestJobService extends JobService {
    private final static String TAG = TestJobService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        startJobSheduler();
    }

    public void startJobSheduler() {
        try {
            JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(), TestJobService.class.getName()));
            builder.setPeriodic(5);
            builder.setPersisted(true);
            JobScheduler jobScheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStart");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob");
        return false;
    }
}
