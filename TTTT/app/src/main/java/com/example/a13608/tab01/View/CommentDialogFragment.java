package com.example.a13608.tab01.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13608.tab01.R;
import com.example.a13608.tab01.adapter.GridAdapter;
import com.example.a13608.tab01.avtivity.FriendActivity;
import com.example.a13608.tab01.intent.PhotoPickerIntent;
import com.example.a13608.tab01.pp.PhotoPickerActivity;
import com.example.a13608.tab01.pp.PhotoPreviewActivity;
import com.example.a13608.tab01.pp.SelectModel;
import com.example.a13608.tab01.utils.UtilTools;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static com.example.a13608.tab01.fragement.fourfragement.IMAGE_REQUEST_CODE;
import static com.example.a13608.tab01.fragement.fourfragement.RESULT_REQUEST_CODE;

public class CommentDialogFragment extends DialogFragment {
    private GridView iv_addphoto;
    private EditText editText;
    private Button btn_evluate;
    private static CommentDialogFragment commentDialogFragment;
    private  String imgpath;//存图片路径
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private GridAdapter gridAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 不带style的构建的dialog边框距离屏幕会有一点缝隙，也许是机型的原因？
        //Dialog dialog = new Dialog(getActivity());
       Dialog dialog = new Dialog(getActivity(), R.style.CustomDatePickerDialog);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comment_dialog, container, false);
        iv_addphoto=(GridView) view.findViewById(R.id.iv_addphoto);
        editText=(EditText)view.findViewById(R.id.editText);
        btn_evluate=(Button)view.findViewById(R.id.btn_evluate);
        initEvent();
        return view;
    }

    private void initEvent() {
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3: cols;
        iv_addphoto.setNumColumns(cols);
        iv_addphoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgs = (String) parent.getItemAtPosition(position);
                if ("paizhao".equals(imgs) ){
                    PhotoPickerIntent intent = new PhotoPickerIntent(getContext());
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(1); // 最多选择照片数量，默认为6
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                }else{
                    //Toast.makeText(getContext(),imagePaths.get(position),Toast.LENGTH_SHORT).show();
                }
            }
        });
        imagePaths.add("paizhao");
        gridAdapter = new GridAdapter(imagePaths,getActivity());
        iv_addphoto.setAdapter(gridAdapter);
        btn_evluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),editText.getText().toString(),Toast.LENGTH_SHORT).show();
                //得到的输入内容
                Toast.makeText(getContext(), imagePaths.get(0),Toast.LENGTH_SHORT).show();
                //得到的图片路径
                //提交到服务器上

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    loadAdpater(ListExtra);
                    break;
            }
        }
    }

    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths!=null&& imagePaths.size()>0){
            imagePaths.clear();
        }
        if (paths.contains("paizhao")){
            paths.remove("paizhao");
        }
        paths.add("paizhao");
        imagePaths.addAll(paths);
        gridAdapter  = new GridAdapter(imagePaths,getActivity());
        iv_addphoto.setAdapter(gridAdapter);
        try{
            JSONArray obj = new JSONArray(imagePaths);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }


    //带着图片提交数据，提交完成后清空imgpath


}
