package com.example.a13608.tab01.fragement;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.a13608.tab01.R;
import com.example.a13608.tab01.View.ShareDialogFragment;
import com.example.a13608.tab01.avtivity.FriendActivity;
import com.example.a13608.tab01.avtivity.PhotoActivity;
import com.example.a13608.tab01.avtivity.ShuJuActivity;
import com.example.a13608.tab01.avtivity.UpdateActivity;
import com.example.a13608.tab01.avtivity.WeiXinActivity;
import com.example.a13608.tab01.avtivity.YiJianActivity;
import com.example.a13608.tab01.entity.Person;
import com.example.a13608.tab01.utils.UtilTools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 13608 on 2018/5/6.
 */

public class fourfragement extends Fragment implements View.OnClickListener{
    private ShareDialogFragment fragment;
    private Button bt_data;//修改信息
    private Button bt_friend;//研友圈
    private Button bt_weixin;//微信精选文章
    private Button bt_shuju;//数据分析
    private TextView photo_plan;//照片墙计划
    private TextView yijian;//反馈意见
    private CircleImageView touxiang;//修改头像
    private Dialog dialog;
    private TextView choosePhoto;
    private TextView takePhoto;
    private TextView cancelPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab04,container,false);
        TextView wang = view.findViewById(R.id.tv_EE);//网名展示
        initView(view);
        return view;
    }

    private void initView(View view) {
        dialog=new Dialog(getContext(),R.style.ActionSheetDialogStyle);
        dialog.setCancelable(false);
        View inflate =LayoutInflater.from(getContext()).inflate(R.layout.dialog_2,null);
        //初始化控件
        cancelPhoto = (TextView)inflate.findViewById(R.id.cancelPhoto);
        cancelPhoto.setOnClickListener(this);
        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);



        bt_friend=(Button)view.findViewById(R.id.bt_friend);
        bt_weixin=(Button)view.findViewById(R.id.bt_weixin);
        bt_shuju=(Button)view.findViewById(R.id.bt_shuju);
        photo_plan=(TextView)view.findViewById(R.id.photo_plan);
        yijian=(TextView)view.findViewById(R.id.yijian);
        bt_data=(Button)view.findViewById(R.id.bt_data);//
        touxiang=(CircleImageView)view.findViewById(R.id.touxiang);
        UtilTools.getImageToShare(getActivity(),touxiang);
        fragment = new ShareDialogFragment();
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.show(getFragmentManager(), "");
            }
        });
        fragment.setOnShareClickListener(new ShareDialogFragment.OnShareClickListener() {
            @Override
            public void shareToFacebook() {
                Toast.makeText(getContext(), "拍照", Toast.LENGTH_SHORT).show();
                toCamera();

            }

            @Override
            public void shareToWechat() {
                Toast.makeText(getContext(), "相册", Toast.LENGTH_SHORT).show();
                toPicture();

            }
        });
        yijian.setOnClickListener(this);
        bt_friend.setOnClickListener(this);
        bt_shuju.setOnClickListener(this);
        photo_plan.setOnClickListener(this);
        bt_data.setOnClickListener(this);
        bt_weixin.setOnClickListener(this);
    }
    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用的话就进行储存
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        fragment.dismiss();
    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;
    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        fragment.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机数据
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageToView(data);
                        //既然已经设置了图片，我们原先的就应该删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }

                    }
                    break;
            }
        }
    }

    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            //做头像的上传
            final Bitmap bitmap = bundle.getParcelable("data");
            touxiang.setImageBitmap(bitmap);
            try{
                upload(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
        //头像上传
    private void upload(Bitmap bitmap) throws IOException{
        //bitmap--->file--->url

        String imgpath =UtilTools.savePhoto(bitmap,Environment.getExternalStorageDirectory().getAbsolutePath(),
                String.valueOf(System.currentTimeMillis()));
        //Toast.makeText(getContext(),"imgpath:"+imgpath,Toast.LENGTH_LONG).show();
        //打印图片在手机中的存储路径
        if (imgpath!=null){
            //拿着图片路径上传到服务器
            OkHttpClient mOkhttpClient =new OkHttpClient();
            RequestBody body =new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("user","2016021051")
                    .addFormDataPart("screenname","夜空中最亮的星")
                    .addFormDataPart("mPhoto","2016021051.png",RequestBody.create(MediaType.parse("image/png"),
                            new File(imgpath)))
                    .build();
            Request request=new Request.Builder()
                    //.addHeader("","")//添加cookies
                    .url("http://192.168.43.230:8080/Demo/uploadInfo")//接受图片的url,
                    .post(body)
                    .build();
            mOkhttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Looper.prepare();
                    String result = response.body().string();
                    if (result.equals("操作失败")){
                    }else {
                        Toast.makeText(getContext(),"头像上传成功",Toast.LENGTH_SHORT).show();
                    }
                    Looper.loop();
                }
            });
        }

    }

    //裁剪
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            //L.e("uri == null");
            Log.e("T","uri == null");
            return;
        }


        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_data:
                /**
                 * 修改信息
                 */
               startActivity(new Intent(getContext(),UpdateActivity.class));
                break;

            case R.id.bt_friend:
                startActivity(new Intent(getContext(), FriendActivity.class));
                break;
            case R.id.bt_weixin:
                startActivity(new Intent(getContext(), WeiXinActivity.class));
                break;
            case R.id.bt_shuju:
                startActivity(new Intent(getContext(), ShuJuActivity.class));
                break;
            case R.id.photo_plan:
                startActivity(new Intent(getContext(), PhotoActivity.class));
                break;
            case R.id.yijian:
                startActivity(new Intent(getContext(), YiJianActivity.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //保存
        UtilTools.putImageToShare(getActivity(),touxiang);
    }
}
