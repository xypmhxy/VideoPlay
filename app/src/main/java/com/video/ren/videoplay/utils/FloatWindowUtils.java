package com.video.ren.videoplay.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

    private static FloatWindowUtils instance;

    private FloatWindowUtils() {

    }

    public static FloatWindowUtils getInstance() {
        if (instance == null)
            return instance = new FloatWindowUtils();
        return instance;
    }

    public void startFloatWindow(Context context, Video video) {
        view = LayoutInflater.from(context).inflate(R.layout.float_play, null, false);
        videoPlayer = (NiceVideoPlayer) view.findViewById(R.id.video_player);
        videoPlayer.setOnTouchListener(this);
        ImageView close = (ImageView) view.findViewById(R.id.image_close_float);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                wm.removeView(view);
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
        wm.addView(view, params);
    }

    private void initVideoPlayer(Video video) {
        controller.setTitle(video.getName());
        long time = DateUtils.parseDate(video.getDurtion());
        controller.setLenght(time);
        videoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        videoPlayer.setUp(video.getData(), null);
        videoPlayer.setController(controller);
        videoPlayer.isFloatPlay(true);
        videoPlayer.start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
//                Log.d("rq", params.x + "==" + params.y);
                Log.e("rq", (int) (event.getRawY() - videoPlayer.pressY) + "");
                params.x += (int) (event.getRawX() - videoPlayer.pressX);
                params.y += (int) (event.getRawY() - videoPlayer.pressY);
                Log.d("rq", params.y + "");
                wm.updateViewLayout(view, params);
                return true;
            case MotionEvent.ACTION_UP:
                params.x = params.y = 0;
                videoPlayer.pressX = videoPlayer.pressY = 0;
                break;
        }
        return false;
    }
}
