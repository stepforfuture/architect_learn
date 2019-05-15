package com.test.architect_learn.keep_alive;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Description:
 * <br>Author：tian
 * <br>Time: 2019/5/14 20:53
 */
public class ForegroundService extends Service {
    private static final String TAG = ForegroundService.class.getSimpleName();
    private static final int SERVICE_ID = 1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "ForegroundService 启动了");
        startForeground(SERVICE_ID, new Notification());
    }
}
