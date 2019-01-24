package com.example.a13608.tab01.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a13608.tab01.R;
import com.example.a13608.tab01.avtivity.ScanActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 13608 on 2018/5/6.
 */

public class firstfragement extends Fragment implements View.OnClickListener{
    private Button img_sign_in;
    private Button img_leave;
    private Button img_keep;
    private Button img_end;
    private boolean is=true;
    private boolean iss=false;
    private boolean IS=false;
    private static String state="signin";
    private static String state1="leave";
    private static String state2="keep";
    private static String state3="end";
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab01,container,false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        img_sign_in=(Button)view.findViewById(R.id.img_sign_in);
        img_leave=(Button) view.findViewById(R.id.img_leave);
        img_keep=(Button)view.findViewById(R.id.img_keep);
        img_end=(Button)view.findViewById(R.id.img_end);
        img_sign_in.setOnClickListener(this);
        img_leave.setOnClickListener(this);
        img_keep.setOnClickListener(this);
        img_end.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_sign_in:

                if (is){
                    startActivity(new Intent(getActivity(), ScanActivity.class));
                    /**
                     * 二维码扫描签到
                     * 给服务器发送状态信息
                     */
                    iss=true;
                    is=false;
                }else {
                    Toast.makeText(getActivity(),"不可以重复点击签到",Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.img_leave:
                /**
                 * 给服务器发送状态信息
                 */
                if (iss){

                    send("暂离");
                    IS=true;
                }else {

                    Toast.makeText(getActivity(),"请先签到",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.img_keep:
                /**
                 * 给服务器发送状态信息
                 */
                if (iss && IS){
                    send("续座");
                }else {
                    Toast.makeText(getActivity(),"没有签到和暂离，不能续座",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.img_end:
                /**
                 * 给服务器发送状态信息
                 */
                if (iss){
                    send("结束使用");
                    iss=false;
                    is=true;

                }else {
                    Toast.makeText(getActivity(),"请先点击签到再次使用",Toast.LENGTH_SHORT).show();
                }
                /**
                 * 暂离，续座，不可点击
                 */
                break;
        }
    }

    /**
     * 点击按钮之后，发送状态
     */
    private void send(String str) {
        String url1="";
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody body=new FormBody.Builder()
                .add("state",str)
                .build();
        Request request=new Request.Builder()
                .url(url1)
                .post(body)
                .build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                String str=response.body().string();
                /**
                 * 根据服务器返回结果弹出Toast
                 */
                Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT).show();

                Looper.loop();
            }
        });
    }


}
