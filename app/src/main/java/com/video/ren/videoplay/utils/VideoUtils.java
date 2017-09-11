package com.video.ren.videoplay.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

import com.video.ren.videoplay.beans.Video;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/9/4
 */

public class VideoUtils {

    private static final Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI; //视频
    private static List<Video> videos = new ArrayList<>();

    public static void findAllVideo(Activity activity) {
        ContentResolver cr = activity.getContentResolver();
        Cursor cursor = cr.query(uri, null, MediaStore.Video.Media.DURATION + ">?", new String[]{"" + 2 * 1000}, null);
        if (cursor == null)
            return;
        videos.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
            long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
            String duration = DateUtils.formatDate(time);
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(data, MediaStore.Video.Thumbnails.MICRO_KIND);
            Video video = new Video(id, name, data, size, duration, bitmap);
            videos.add(video);
        }
        cursor.close();
    }

    public static Video findVideoByUrl(Context context, String url) {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(uri, null, MediaStore.Video.Media.DATA + "=?", new String[]{url}, null);
        if (cursor == null)
            return null;
        cursor.moveToNext();
        int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
        String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
        String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
        long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
        String duration = DateUtils.formatDate(time);
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(data, MediaStore.Video.Thumbnails.MICRO_KIND);
        cursor.close();
        return new Video(id, name, data, size, duration, bitmap);
    }

    public static List<Video> getVideos() {
        return videos;
    }
}
