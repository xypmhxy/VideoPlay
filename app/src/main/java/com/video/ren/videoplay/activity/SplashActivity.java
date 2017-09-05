package com.video.ren.videoplay.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.video.ren.videoplay.R;
import com.video.ren.videoplay.utils.PermissionUtils;
import com.video.ren.videoplay.utils.VideoUtils;
import com.video.ren.videoplay.widget.PermissionDialog;

/**
 * Created by Administrator on 2017/9/5
 */

public class SplashActivity extends AppCompatActivity implements PermissionDialog.OnUserChooseListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.checkPermission(this)) {
                VideoUtils.findAllVideo(this);
                IntentMainActivity();
            }
        } else {
            VideoUtils.findAllVideo(this);
            IntentMainActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.REQUESTCODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                VideoUtils.findAllVideo(this);
                IntentMainActivity();
            } else {
                PermissionDialog dialog = new PermissionDialog(this);
                dialog.setOnUserChooseListener(this);
                dialog.show();
            }
        }
    }

    @Override
    public void onComfir() {
        PermissionUtils.requestPermission(this);
//        VideoUtils.findAllVideo(this);
    }

    @Override
    public void onCancel() {
        finish();
    }

    private void IntentMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
