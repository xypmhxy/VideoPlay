package com.video.ren.videoplay.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.video.ren.videoplay.R;
import com.video.ren.videoplay.adapter.VideoListAdapter;
import com.video.ren.videoplay.beans.Video;
import com.video.ren.videoplay.fragment.LocalVideoFragment;
import com.video.ren.videoplay.utils.VideoUtils;
import com.xiao.nicevideoplayer.NiceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.xiao.nicevideoplayer.NiceUtil.getSavedCurrentVideo;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, SearchView.OnQueryTextListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.search)
    SearchView search;
    @BindView(R.id.listview_search)
    ListView listView;
    @BindView(R.id.radio_group_menu)
    RadioGroup radioGroupMenu;
    @BindView(R.id.image_play_main_float)
    ImageView play;

    private List<Video> list = new ArrayList<>();
    private VideoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container, new LocalVideoFragment()).commit();
        search.setOnQueryTextListener(this);
        radioGroupMenu.setOnCheckedChangeListener(this);
        adapter = new VideoListAdapter(list, this);
        listView.setAdapter(adapter);
    }

    @OnClick(R.id.image_play_main_float)
    public void onClick(View view) {
//        String url = NiceUtil.getSavedCurrentVideo(this);
//        if (url != null) {
//            Video video = VideoUtils.findVideoByUrl(this, url);
//            if (video != null) {
//                Intent intent = new Intent(this, PlayActivity.class);
//                intent.putExtra(PlayActivity.KEY_VIDEO, video);
//                startActivity(intent);
//            }
//        }
//        ImageView imageView = new ImageView(this);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"点击了我",Toast.LENGTH_SHORT).show();
//            }
//        });
//        imageView.setImageResource(R.mipmap.ic_launcher);
        View v = LayoutInflater.from(this).inflate(R.layout.activity_play, null);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(300, 300);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.format = PixelFormat.RGBX_8888;
        wm.addView(v, params);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radiobtn_local:
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.trim().length() == 0) {
            listView.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);
            return false;
        } else {
            listView.setVisibility(View.VISIBLE);
            container.setVisibility(View.GONE);
        }
        List<Video> videos = VideoUtils.getVideos();
        list.clear();
        if (!videos.isEmpty()) {
            for (Video video : videos) {
                if (video.getName().contains(newText))
                    list.add(video);
            }
            adapter.notifyDataSetInvalidated();
        }
        return false;
    }
}
