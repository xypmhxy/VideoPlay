package com.video.ren.videoplay;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Administrator on 2017/9/5
 */

public class VideoApplication extends Application implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d("rq","activity "+activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d("rq","activity "+activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d("rq","activity "+activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d("rq","activity "+activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d("rq","activity "+activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.d("rq","activity "+activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d("rq","activity "+activity);
    }
}
