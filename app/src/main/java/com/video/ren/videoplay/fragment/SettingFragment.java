package com.video.ren.videoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.video.ren.videoplay.R;
import com.video.ren.videoplay.utils.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by Administrator on 2017/9/13
 */

public class SettingFragment extends Fragment {


    @BindView(R.id.switch_play_now)
    SwitchCompat playNow;
    @BindView(R.id.switch_float_play)
    SwitchCompat floatPlay;
    @BindView(R.id.switch_scan_time)
    SwitchCompat scan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        playNow.setChecked(PreferencesUtils.getPlayNow(getContext()));
        floatPlay.setChecked(PreferencesUtils.getFloatWindow(getContext()));
        scan.setChecked(PreferencesUtils.getScanTime(getContext()));
    }

    @OnCheckedChanged({R.id.switch_play_now, R.id.switch_float_play, R.id.switch_scan_time})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_play_now:
                PreferencesUtils.savePlayNow(getContext(), isChecked);
                break;
            case R.id.switch_float_play:
                PreferencesUtils.saveFloatWindow(getContext(), isChecked);
                break;
            case R.id.switch_scan_time:
                PreferencesUtils.saveScanTime(getContext(), isChecked);
                break;
        }
    }
}
