package com.video.ren.videoplay.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.video.ren.videoplay.Broadcast.HomeBroadcastReceiver;
import com.video.ren.videoplay.R;
import com.video.ren.videoplay.beans.Video;
import com.video.ren.videoplay.utils.DateUtils;
import com.video.ren.videoplay.utils.FloatWindowUtils;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/6
 */

public class PlayActivity extends BaseActivity implements HomeBroadcastReceiver.OnHomeKeyPressListener {

    public static final String KEY_VIDEO = "video";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.video_player)
    NiceVideoPlayer videoPlayer;

    private Video video;
    private HomeBroadcastReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        video = intent.getParcelableExtra(KEY_VIDEO);
        initToolBar();
        initVideoPlayer();
        initHomeKeyBroadcast();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        unregisterReceiver(receiver);
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
        videoPlayer.setUp(video.getData(), null);
        controller.imageView().setImageBitmap(video.getThumbnail());
        videoPlayer.setController(controller);
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onHomeKeyPress() {
        if (videoPlayer.isPlaying()) {
            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
            FloatWindowUtils.getInstance().startFloatWindow(this, video);
        }
    }
}
