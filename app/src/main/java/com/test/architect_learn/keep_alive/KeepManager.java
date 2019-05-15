package com.test.architect_learn.keep_alive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.lang.ref.WeakReference;

/**
 * Description:
 * <br>Author：tian
 * <br>Time: 2019/5/14 20:15
 */
public class KeepManager {

    private static final KeepManager mInstance = new KeepManager();

    private KeepReceiver mKeepReceiver;
    private WeakReference<Activity> mkeepActivity;

    private KeepManager() {
    }

    public static KeepManager getInstance() {
        return mInstance;
    }

    public void registerKeep(Context context) {
        IntentFilter filter = new IntentFilter();

        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);

        mKeepReceiver = new KeepReceiver();
        context.registerReceiver(mKeepReceiver, filter);
    }

    public void startKeep(Context context) {
        Intent intent = new Intent(context, KeepActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void finishKeep() {
        if (mkeepActivity == null)
            return;
        Activity activity = mkeepActivity.get();
        if (activity != null) {
            activity.finish();
        }
        mkeepActivity = null;
    }

    public void setmKeep(KeepActivity ac) {
        mkeepActivity = new WeakReference<Activity>(ac);
    }

    public void unregisterKeep(Context context) {
        if (mkeepActivity != null) {
            context.unregisterReceiver(mKeepReceiver);
        }
    }


}
