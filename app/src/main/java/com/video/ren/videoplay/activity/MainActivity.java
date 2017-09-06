package com.video.ren.videoplay.activity;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.video.ren.videoplay.R;
import com.video.ren.videoplay.adapter.VideoListAdapter;
import com.video.ren.videoplay.beans.Video;
import com.video.ren.videoplay.fragment.LocalVideoFragment;
import com.video.ren.videoplay.utils.VideoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,SearchView.OnQueryTextListener{

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

    private List<Video>list=new ArrayList<>();
    private VideoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container, new LocalVideoFragment()).commit();
        search.setOnQueryTextListener(this);
        radioGroupMenu.setOnCheckedChangeListener(this);
        adapter=new VideoListAdapter(list,this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radiobtn_local :
                break ;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.trim().length()==0){
            listView.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);
            return false;
        }else{
            listView.setVisibility(View.VISIBLE);
            container.setVisibility(View.GONE);
        }
        List<Video>videos= VideoUtils.getVideos();
        list.clear();
        if (!videos.isEmpty()){
            for (Video video : videos) {
                if (video.getName().contains(newText))
                    list.add(video);
            }
            adapter.notifyDataSetInvalidated();
        }
        return false;
    }
}
