package com.example.a13608.tab01.View;

import android.app.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.a13608.tab01.R;

/**
 * Created by 13608 on 2018/6/10.
 */

public class ShareDialogFragment extends DialogFragment implements View.OnClickListener {

    TextView takePhoto;
    TextView choosePhoto;
    TextView cancelPhoto;
    private static ShareDialogFragment shareDialogFragment;

    public static ShareDialogFragment newInstance(String title, String message) {
        ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        shareDialogFragment.setArguments(bundle);
        return shareDialogFragment;
    }

    public static ShareDialogFragment newInstance() {
        if (shareDialogFragment == null) {
            ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
        }
        return shareDialogFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_2, container, false);
        takePhoto = (TextView) view.findViewById(R.id.takePhoto);
        choosePhoto = (TextView) view.findViewById(R.id.choosePhoto);
        cancelPhoto = (TextView) view.findViewById(R.id.cancelPhoto);
        takePhoto.setOnClickListener(this);
        choosePhoto.setOnClickListener(this);
        cancelPhoto.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 不带style的构建的dialog边框距离屏幕会有一点缝隙，也许是机型的原因？
        Dialog dialog = new Dialog(getActivity());
//        Dialog dialog = new Dialog(getActivity(), R.style.CustomDatePickerDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);

        // 设置宽度为屏宽、靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        return dialog;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.takePhoto:
                shareClickListener.shareToFacebook();
                break;
            case R.id.choosePhoto:
                shareClickListener.shareToWechat();
                break;
            case R.id.cancelPhoto:
                dismiss();
                break;
        }
    }

    private OnShareClickListener shareClickListener;

    public interface OnShareClickListener {
        void shareToFacebook();

        void shareToWechat();

    }

    public void setOnShareClickListener(OnShareClickListener shareClickListener) {
        this.shareClickListener = shareClickListener;
    }
}
