package com.video.ren.videoplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.video.ren.videoplay.R;
import com.video.ren.videoplay.beans.Video;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/9/5
 */

public class VideoListAdapter extends BaseAdapter {
    private List<Video> videos;
    private Context context;
    private LayoutInflater inflater;

    public VideoListAdapter(List<Video> videos, Context context) {
        this.videos = videos;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Video getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        Video video = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_videos_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.name.setText(video.getName());
        viewHolder.time.setText(video.getDurtion());
//        viewHolder.thumImg.setImageBitmap(video.getThumbnail());
        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.text_name_item_videos)
        TextView name;
        @BindView(R.id.text_time_item_videos)
        TextView time;
        @BindView(R.id.image_thum_item_videos)
        ImageView thumImg;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }
}
