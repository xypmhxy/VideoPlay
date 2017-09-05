package com.video.ren.videoplay.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.video.ren.videoplay.R;
import com.video.ren.videoplay.utils.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/4
 */

public class BaseActivity extends AppCompatActivity {

    private static final String KEY_BUNDLE="bundle";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this,ContextCompat.getColor(this, R.color.colorPrimary));
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    protected void launchActivity(Class cls){
        Intent intent=new Intent(this,cls);
        startActivity(intent);
    }

    protected void launchActivity(Class cls,Bundle bundle){
        Intent intent=new Intent(this,cls);
        intent.putExtra(KEY_BUNDLE , bundle);
        startActivity(intent);
    }
}
