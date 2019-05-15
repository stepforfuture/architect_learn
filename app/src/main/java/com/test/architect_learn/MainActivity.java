package com.test.architect_learn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.architect_learn.keep_alive.ForegroundService;
import com.test.architect_learn.keep_alive.KeepActivity;
import com.test.architect_learn.keep_alive.KeepManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1个像素保活
//        KeepManager.getInstance().registerKeep(this);

        //利用前台服务 提升进程优先级保活
        startService(new Intent(this, ForegroundService.class));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        KeepManager.getInstance().unregisterKeep(this);
    }
}


