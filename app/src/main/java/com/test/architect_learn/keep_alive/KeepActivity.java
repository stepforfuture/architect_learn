package com.test.architect_learn.keep_alive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Description: 1个像素的保活方案 中1个像素的activity
 * <br>Author：tian
 * <br>Time: 2019/5/14 20:10
 */
public class KeepActivity extends AppCompatActivity {

    private final String TAG = KeepActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "启动app");

        Window window = getWindow();
        window.setGravity(Gravity.START | Gravity.TOP);

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = 1;
        params.height = 1;
        params.x = 0;
        params.y = 0;
        window.setAttributes(params);
        KeepManager.getInstance().setmKeep(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "关闭KeepActivity");
    }
}
