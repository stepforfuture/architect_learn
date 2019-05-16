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
 * 本地服务
 */
public class LocalService extends Service {

    private final static String TAG = "keepAlive";
    private LocalBinder mBinder;
    private ServiceConnection localServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                Log.i(TAG, "与AliveRemoteService连接成功 " + IRemoteConnection.Stub.asInterface(service).getProcessName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "RemoteService服务被干掉了~~~~断开连接！");
            //启动被干掉的
            LocalService.this.startService(new Intent(LocalService.this, AliveRemoteService.class));
            LocalService.this.bindService(new Intent(LocalService.this, AliveRemoteService.class), localServiceConn, Context.BIND_IMPORTANT);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mBinder == null) {
            mBinder = new LocalBinder();
        }
        Log.i(TAG,"LocalService onCreate() --");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"LocalService onStartCommand() --");
        LocalService.this.bindService(new Intent(LocalService.this, AliveRemoteService.class), localServiceConn, Context.BIND_IMPORTANT);

//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setContentTitle("From LocalService!")
//                .setAutoCancel(true)
//                .setWhen(System.currentTimeMillis());
//        //把RemoteService设置为前台服务,提升所在进程的优先级
//        startForeground(startId, builder.build());
        return START_STICKY;//粘性服务
//        return super.onStartCommand(intent, flags, startId);
    }

    class LocalBinder extends IRemoteConnection.Stub {
        @Override
        public String getProcessName() throws RemoteException {
            return "LocalService";
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"LocalService onDestroy() --");
    }
}
