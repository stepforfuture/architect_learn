package com.test.architect_learn.keep_alive.sticky_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 利用粘性服务进行 进程的拉活
 * Service 第一次被异常杀死后会在5秒内重启，第二次被杀死会在10秒内重启，第三次会在20秒内重启，一旦在短时间内 Service 被杀死达到5次，则系统不再拉起。
 * <br>
 * 实测有效
 * <br>
 * 缺陷：还需要查阅
 */
public class StickySevice extends Service {

    private final static String TAG = StickySevice.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onstartCommand---" + flags);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
