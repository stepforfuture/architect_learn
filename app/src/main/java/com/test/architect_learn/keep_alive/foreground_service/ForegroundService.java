package com.test.architect_learn.keep_alive.foreground_service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.test.architect_learn.R;

/**
 * 利用前台服务保活的方案,这是提高进程的优先级，如下直接用
 *
 * <pre>
 * public class MainActivity extends AppCompatActivity {
 *     protected void onCreate(Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *         setContentView(R.layout.activity_main);
 *         startService(new Intent(this, ForegroundService.class));
 *     }
 *
 *     protected void onDestroy() {
 *         super.onDestroy();
 *     }
 * }
 * 上面这做过后会出现，在把app放入到后台的时候发现进程的优先级提高了 oom_adj值为3（没有这么操作的时候为11）
 *
 * 但是这么做过后在通知栏上显示一个通知,去掉通知的原理如下
 *
 * 对于 API level < 18 ：调用startForeground(ID， new Notification())，发送空的Notification ，图标则不会显示
 * 对于 API level >= 18：在需要提优先级的service A启动一个InnerService，两个服务同时startForeground，且绑定同样的 ID。Stop 掉InnerService ，这样通知栏图标即被移除
 *
 * 在api<25之下 android 7.1之前存在一个漏洞，如果2个service通过同样的id设置为前台进程，而其中一个通过stopForeground
 * 取消前台显示，结果是保留一个前台服务，但不在状态栏显示通知，这样就不会被用户感知，这种手段是比较常用的保活手段。
 * 但是在android 7.1和之后就修复了这个漏洞
 *
 * <pre/>
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
        Log.i(TAG, "ForegroundService 启动了");

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            startForeground(SERVICE_ID, new Notification());
        } else {
            //API 18以上，发送Notification并将其置为前台后，启动InnerService
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            startForeground(SERVICE_ID, builder.build());
            startService(new Intent(this, InnerService.class));
        }
    }

    public class InnerService extends Service {

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();

            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            startForeground(SERVICE_ID, builder.build());

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopForeground(true);
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.cancel(SERVICE_ID);
                    stopSelf();
                }
            }, 100);
        }
    }

}
