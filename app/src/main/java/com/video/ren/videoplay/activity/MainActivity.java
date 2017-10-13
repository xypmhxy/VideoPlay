package com.video.ren.videoplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.video.ren.videoplay.R;
import com.video.ren.videoplay.adapter.VideoListAdapter;
import com.video.ren.videoplay.beans.Video;
import com.video.ren.videoplay.fragment.LocalVideoFragment;
import com.video.ren.videoplay.fragment.SettingFragment;
import com.video.ren.videoplay.utils.BrandUtils;
import com.video.ren.videoplay.utils.FloatWindowUtils;
import com.video.ren.videoplay.utils.VideoUtils;
import com.xiao.nicevideoplayer.NiceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

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
    private LocalVideoFragment localVideoFragment;
    private SettingFragment settingFragment;


    @OnClick(R.id.image_play_main_float)
    public void onClick(View view) {
        BrandUtils.getPhoneBrand();
        String url = NiceUtil.getSavedCurrentVideo(this);
        if (url != null) {
            Video video = VideoUtils.findVideoByUrl(this, url);
            if (video != null) {
                Intent intent = new Intent(this, PlayActivity.class);
                intent.putExtra(PlayActivity.KEY_VIDEO, video);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container, localVideoFragment = new LocalVideoFragment()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container, settingFragment = new SettingFragment()).hide(settingFragment).commit();
        search.setOnQueryTextListener(this);
        radioGroupMenu.setOnCheckedChangeListener(this);
        adapter = new VideoListAdapter(list, this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radiobtn_local:
                getSupportFragmentManager().beginTransaction().show(localVideoFragment).hide(settingFragment).commit();
                break;
            case R.id.radiobtn_my:
                getSupportFragmentManager().beginTransaction().show(settingFragment).hide(localVideoFragment).commit();
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

    @OnItemClick(R.id.listview_search)
    public void onItemClick(int position) {
        Video video = adapter.getItem(position);
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(PlayActivity.KEY_VIDEO, video);
        startActivity(intent);
    }
}
