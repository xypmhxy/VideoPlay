package com.video.ren.videoplay.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.video.ren.videoplay.R;
import com.video.ren.videoplay.beans.Video;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

/**
 * Created by Administrator on 2017/09/11 0011
 */

public class FloatWindowUtils implements View.OnTouchListener {

    private NiceVideoPlayer videoPlayer;
    private TxVideoPlayerController controller;
    private WindowManager wm;
    private WindowManager.LayoutParams params;
    private View view;
    private int lastParamsX, lastParamsY;

    private static FloatWindowUtils instance;

    private FloatWindowUtils() {

    }

    public static FloatWindowUtils getInstance() {
        if (instance == null)
            return instance = new FloatWindowUtils();
        return instance;
    }

    public void startFloatWindow(Context context, Video video) {
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        view = LayoutInflater.from(context).inflate(R.layout.float_play, null, false);
        videoPlayer = (NiceVideoPlayer) view.findViewById(R.id.video_player);
        videoPlayer.setOnTouchListener(this);
        ImageView close = (ImageView) view.findViewById(R.id.image_close_float);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                release();
            }
        });
        controller = new TxVideoPlayerController(context);
        initVideoPlayer(video);
        params = new WindowManager.LayoutParams();
        wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;
        params.format = PixelFormat.TRANSPARENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lastParamsX = params.x;
        lastParamsY = params.y;
        wm.addView(view, params);
        videoPlayer.start();
    }

    private void initVideoPlayer(Video video) {
        controller.setTitle(video.getName());
        long time = DateUtils.parseDate(video.getDurtion());
        controller.setLenght(time);
        videoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        videoPlayer.setUp(video.getData(), null);
        videoPlayer.setController(controller);
        videoPlayer.setFloatPlay(true);
    }

    public void release() {
        if (videoPlayer == null)
            return;
        videoPlayer.setFloatPlay(false);
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        wm.removeView(view);
        view = null;
        params = null;
        videoPlayer = null;
        controller = null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                params.x = (int) (lastParamsX + (event.getRawX() - videoPlayer.pressX));
                params.y = (int) (lastParamsY + (event.getRawY() - videoPlayer.pressY));
                wm.updateViewLayout(view, params);
                return true;
            case MotionEvent.ACTION_UP:
                lastParamsX = params.x;
                lastParamsY = params.y;
                videoPlayer.pressX = videoPlayer.pressY = 0;
                break;
        }
        return false;
    }
}
