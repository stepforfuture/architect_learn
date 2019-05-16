package com.test.architect_learn.keep_alive.double_process;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * 熄屏休眠时的唤醒
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobHandleService extends JobService {
    private final static String TAG = JobHandleService.class.getSimpleName();
    private int kJobId = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "jobService create");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "jobService start");
        scheduleJob(getJobInfo());
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        // TODO Auto-generated method stub
        Log.i(TAG, "job start");
//      scheduleJob(getJobInfo());
        boolean isLocalServiceWork = isServiceWork(this, LocalService.class.getName());
        boolean isRemoteServiceWork = isServiceWork(this, AliveRemoteService.class.getName());
//      Log.i("INFO", "localSericeWork:"+isLocalServiceWork);
//      Log.i("INFO", "remoteSericeWork:"+isRemoteServiceWork);
        if (!isLocalServiceWork ||
                !isRemoteServiceWork) {
            this.startService(new Intent(this, LocalService.class));
            this.startService(new Intent(this, AliveRemoteService.class));
            Log.i(TAG, "process start");
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "job stop");
//      Toast.makeText(this, "process stop", Toast.LENGTH_SHORT).show();
        scheduleJob(getJobInfo());
        return true;
    }

    /**
     * Send job to the JobScheduler.
     */
    public void scheduleJob(JobInfo t) {
        Log.i(TAG, "Scheduling job");
        JobScheduler tm =
                (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(t);
    }

    public JobInfo getJobInfo() {
        JobInfo.Builder builder = new JobInfo.Builder(kJobId++, new ComponentName(this, JobHandleService.class));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(false);
        builder.setPeriodic(10);//间隔时间--周期
        return builder.build();
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：com.test.architect_learn.keep_alive.double_process）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
