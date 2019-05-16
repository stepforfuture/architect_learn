package com.test.architect_learn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.test.architect_learn.keep_alive.double_process.AliveRemoteService;
import com.test.architect_learn.keep_alive.double_process.LocalService;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "MainActivity create");
        setContentView(R.layout.activity_main);
//        // 方案one 1个像素保活
//        KeepManager.getInstance().registerKeep(this);
//        //方案two 利用前台服务 提升进程优先级保活
//        startService(new Intent(this, ForegroundService.class));

//        //方案3，利用sticky进行进程的拉活
//        startService(new Intent(this, StickySevice.class));
//          //方案4，利用JobShechuder进行进程的拉活
//          startService(new Intent(this, TestJobService.class));
        //方案5，双进程的方式进行保活,步骤较为麻烦
        startService(new Intent(this, LocalService.class));
        startService(new Intent(this, AliveRemoteService.class));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        KeepManager.getInstance().unregisterKeep(this);
    }
}


