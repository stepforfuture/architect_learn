package com.test.architect_learn.keep_alive;

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

        Log.e(TAG, "on receive:" + action);

        if (TextUtils.equals(action, Intent.ACTION_SCREEN_ON)) {
            KeepManager.getInstance().startKeep(context);
        } else if (TextUtils.equals(action, Intent.ACTION_SCREEN_ON)) {
            KeepManager.getInstance().finishKeep();
        }

    }
}
