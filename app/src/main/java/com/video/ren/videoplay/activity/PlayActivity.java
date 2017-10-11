package com.video.ren.videoplay.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.video.ren.videoplay.Broadcast.HomeBroadcastReceiver;
import com.video.ren.videoplay.R;
import com.video.ren.videoplay.beans.Video;
import com.video.ren.videoplay.utils.BrandUtils;
import com.video.ren.videoplay.utils.DateUtils;
import com.video.ren.videoplay.utils.DensityUtil;
import com.video.ren.videoplay.utils.FloatWindowUtils;
import com.video.ren.videoplay.utils.PermissionUtils;
import com.video.ren.videoplay.utils.PreferencesUtils;
import com.video.ren.videoplay.utils.ScreenUtils;
import com.xiao.nicevideoplayer.NiceUtil;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import butterknife.BindView;

import static android.view.OrientationEventListener.ORIENTATION_UNKNOWN;

/**
 * Created by Administrator on 2017/9/6
 */

public class PlayActivity extends BaseActivity implements HomeBroadcastReceiver.OnHomeKeyPressListener, ScreenUtils.onOrientationChangedListener {

    public static final String KEY_VIDEO = "video";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.video_player)
    NiceVideoPlayer videoPlayer;

    private Video video;
    private HomeBroadcastReceiver receiver;
    private boolean isFloatPlaying;
    private SensorManager sm = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        video = intent.getParcelableExtra(KEY_VIDEO);
        initToolBar();
        initVideoPlayer();
        initHomeKeyBroadcast();
        if (PreferencesUtils.getAutoOrientation(this))
            ScreenUtils.registerSensorListener(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFloatPlaying) {
            FloatWindowUtils.getInstance().release();
            isFloatPlaying = false;
            videoPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        unregisterReceiver(receiver);
        ScreenUtils.unRegisterSensorListener(this);
    }

    private void initToolBar() {
        toolbar.setTitle(video.getName());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initVideoPlayer() {
        videoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle(video.getName());
        long time = DateUtils.parseDate(video.getDurtion());
        controller.setLenght(time);
        controller.imageView().setImageBitmap(video.getThumbnail());
        videoPlayer.setUp(video.getData(), null);
        videoPlayer.setController(controller);
        if (PreferencesUtils.getPlayNow(this))
            videoPlayer.start();
    }

    private void initHomeKeyBroadcast() {
        receiver = new HomeBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHomeKeyPress() {
        if (!PreferencesUtils.getFloatWindow(this))
            return;
        boolean canOpenFloatWindow = PermissionUtils.checkAlertWindowPermission(this);
        if (videoPlayer.isPlaying())
            if (canOpenFloatWindow) {
                FloatWindowUtils.getInstance().startFloatWindow(this, video);
                isFloatPlaying = true;
            } else {
                Intent intent = new Intent();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    intent.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                else
                    intent = BrandUtils.getBrandAppIntent(this);
                startActivity(intent);
                Toast.makeText(this, "请在设置页面手动开启悬浮窗O(∩_∩)O", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ViewGroup.LayoutParams params = videoPlayer.getLayoutParams();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            params.height = DensityUtil.dip2px(this, 220);
        }
        videoPlayer.setLayoutParams(params);
    }

    @Override
    public void onOrientationChanged(int orientation) {
        if (orientation == ScreenUtils.LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
