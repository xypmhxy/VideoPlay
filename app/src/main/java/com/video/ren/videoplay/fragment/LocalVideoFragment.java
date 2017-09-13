package com.video.ren.videoplay.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.video.ren.videoplay.R;
import com.video.ren.videoplay.activity.PlayActivity;
import com.video.ren.videoplay.adapter.VideoListAdapter;
import com.video.ren.videoplay.beans.Video;
import com.video.ren.videoplay.utils.VideoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by Administrator on 2017/09/05 0005
 */

public class LocalVideoFragment extends Fragment {

    @BindView(R.id.listview_main)
    ListView listView;

    private List<Video> videos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        videos = VideoUtils.getVideos();
        VideoListAdapter adapter = new VideoListAdapter(videos, getContext());
        listView.setAdapter(adapter);
    }

    @OnItemClick(R.id.listview_main)
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), PlayActivity.class);
        intent.putExtra(PlayActivity.KEY_VIDEO, videos.get(position));
        startActivity(intent);
    }
}
