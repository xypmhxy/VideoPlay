package com.video.ren.videoplay.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/09/11 0011
 */

public class HomeBroadcastReceiver extends BroadcastReceiver {

    private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    //    private static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
//    private static final String SYSTEM_DIALOG_REASON_LOCK = "lock";
//    private static final String SYSTEM_DIALOG_REASON_ASSIST = "assist";

    public HomeBroadcastReceiver(OnHomeKeyPressListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason))
                listener.onHomeKeyPress();
        }
    }

    OnHomeKeyPressListener listener;

    public void setOnHomeKeyPressListener(OnHomeKeyPressListener listener) {
        this.listener = listener;
    }

    public interface OnHomeKeyPressListener {
        void onHomeKeyPress();
    }
}
