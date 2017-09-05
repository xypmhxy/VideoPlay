package com.video.ren.videoplay.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.video.ren.videoplay.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/5
 */

public class PermissionDialog extends Dialog {

    public PermissionDialog(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_permission, null);
        ButterKnife.bind(this, view);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public PermissionDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PermissionDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @OnClick({R.id.text_confirm_dialog_permission , R.id.text_cancel_dialog_permission})
    public void onClick(View view){
        if (view.getId()==R.id.text_confirm_dialog_permission)
            listener.onComfir();
        else
            listener.onCancel();
        dismiss();
    }

    public interface OnUserChooseListener{
        void onComfir();
        void onCancel();
    }

    private OnUserChooseListener listener;

    public void setOnUserChooseListener(OnUserChooseListener listener){
        this.listener=listener ;
    }
}
