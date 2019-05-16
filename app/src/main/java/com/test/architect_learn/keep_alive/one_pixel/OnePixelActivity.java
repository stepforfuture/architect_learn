package com.test.architect_learn.keep_alive.one_pixel;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 一个像素保活方案 ,方案流程
 * <pre>
 *  1.监听屏幕的点亮和熄灭(ACTION_SCREENT_ON,ACTION_SCREENT_OFF)。
 *  2.在屏幕点亮的时候启动一个 一像素的activity设置背景为透明，为什么是1个像素是因为1像素占用的内存最小。
 *
 *  缺点：必须要在用户点亮屏幕的时候，才会去生成1像素页面。
 *  </pre>
 */
public class OnePixelActivity extends Activity {

    private final String TAG = OnePixelActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "启动app");

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
        Log.i(TAG, "关闭KeepActivity");
    }
}
