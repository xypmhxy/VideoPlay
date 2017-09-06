package com.video.ren.videoplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.video.ren.videoplay.R;
import com.video.ren.videoplay.beans.Video;
import com.xiao.nicevideoplayer.Clarity;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/6
 */

public class PlayActivity extends BaseActivity {

    public static final String KEY_VIDEO="video";

    @BindView(R.id.video_player)
    NiceVideoPlayer videoPlayer;

    private Video video;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent=getIntent();
        video= (Video) intent.getSerializableExtra(KEY_VIDEO);
        videoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle(video.getName());
        controller.setLenght(video.getSize());
        videoPlayer.setUp(video.getData(),null);
        videoPlayer.setController(controller);
    }
}
