package com.test.architect_learn.keep_alive.double_process;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.test.architect_learn.IRemoteConnection;

/**
 * 双进程保活中的远程服务,
 *
 */
public class AliveRemoteService extends Service {

    class AliveBinder extends IRemoteConnection.Stub {
        @Override
        public String getProcessName() throws RemoteException {
            return "AliveRemoteService";
        }
    }

    private final static String TAG = "keepAlive";
    private AliveBinder mBinder;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                Log.i(TAG, "与LocalService连接成功 " + IRemoteConnection.Stub.asInterface(service).getProcessName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "与LocalService连接断开----");
            AliveRemoteService.this.startService(new Intent(AliveRemoteService.this, LocalService.class));

            //Context.Bind_IMPORTANT 标识服务对客户端是非常重要,会将服务提升至前台进程优先级,通常情况下,即时客户端是前台优先级,服务最多也只能被提升至可见进程优先级
            AliveRemoteService.this.bindService(new Intent(AliveRemoteService.this, LocalService.class), mServiceConnection, Context.BIND_IMPORTANT);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        if (mBinder == null) {
            mBinder = new AliveBinder();
        }

        Log.i(TAG,"AliveRemoteService onCreate() --");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG,"AliveRemoteService onStartCommand() --");
        AliveRemoteService.this.bindService(new Intent(AliveRemoteService.this, LocalService.class), mServiceConnection, Context.BIND_IMPORTANT);
//
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setContentTitle("From AliveRemoteService")
//                .setAutoCancel(true)
//                .setWhen(System.currentTimeMillis());
//        //把RemoteService设置为前台服务,提升所在进程的优先级
//        startForeground(startId, builder.build());
        return START_STICKY;//使用粘性服务标记，进程在被杀死后会尝试重启

//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"AliveRemoteService onDestroy --");
    }
}
