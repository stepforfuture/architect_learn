package com.test.architect_learn.keep_alive.one_pixel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;


/**
 * Description:
 * <br>Author：tian
 * <br>Time: 2019/5/14 20:20
 */
public class KeepReceiver extends BroadcastReceiver {

    private final String TAG = KeepReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Log.i(TAG, "on receive:" + action);

        if (TextUtils.equals(action, Intent.ACTION_SCREEN_ON)) {//屏幕点亮的时候打开1像素页面
            KeepManager.getInstance().startKeep(context);
        } else if (TextUtils.equals(action, Intent.ACTION_SCREEN_OFF)) {//屏幕关闭的时候需要关闭1像素页面
            KeepManager.getInstance().finishKeep();
        }

    }
}
