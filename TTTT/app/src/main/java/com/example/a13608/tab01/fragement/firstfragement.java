package com.example.a13608.tab01.fragement;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.example.a13608.tab01.R;
import com.example.a13608.tab01.avtivity.webActivity;
import com.example.a13608.tab01.entity.Qiandao;
import com.example.a13608.tab01.service.MyService;
import com.example.a13608.tab01.utils.ShareUtils;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xqrcode.ui.CaptureActivity;

import org.litepal.tablemanager.Connector;

import java.io.IOException;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
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
    private Button img_state;
    private Boolean success;
    private String content;
    private String state;
    private Dialog dialog;
    private SwipeRefreshLayout one_refresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        success=ShareUtils.getBoolean(getContext().getApplicationContext(),"married",false);
        //Toast.makeText(getContext(),success+"",Toast.LENGTH_SHORT).show();
        SharedPreferences preferences =getContext().getSharedPreferences("data",Context.MODE_PRIVATE);
        state= preferences.getString("STATE","0");
        if (!success)
            state="0";
        switch (state){
            case "0":
                content="welcome to Library Room";
                break;
            case "1":
                content="你当前的图书馆自习室座位是签到状态，请继续使用";
                break;
            case "2":
                content="你当前的图书馆自习室座位是暂离状态，务必要在30分钟内完成续座操作";
                break;
            case "3":
                content="你当前的图书馆自习室座位是续座状态，请接着使用";
                break;
            default:
                break;
        }
        //Toast.makeText(getContext(),state,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        success=ShareUtils.getBoolean(getContext().getApplicationContext(),"married",false);
        ///Toast.makeText(getContext(),success+"",Toast.LENGTH_SHORT).show();
        SharedPreferences preferences =getContext().getSharedPreferences("data",Context.MODE_PRIVATE);
        state= preferences.getString("STATE","0");
        if (!success)
            state="0";
        switch (state){
            case "0":
                content="welcome to Library Room";
                break;
            case "1":
                content="你当前的图书馆自习室座位是签到状态，请继续使用";
                break;
            case "2":
                content="你当前的图书馆自习室座位是暂离状态，务必要在30分钟内完成续座操作";
                break;
            case "3":
                content="你当前的图书馆自习室座位是续座状态，请接着使用";
                break;
            default:
                break;
        }
        //Toast.makeText(getContext(),state,Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.tab01,container,false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        one_refresh= (SwipeRefreshLayout) view.findViewById(R.id.one_refresh);
        one_refresh.setEnabled(false);
        img_sign_in=(Button)view.findViewById(R.id.img_sign_in);
        img_leave=(Button) view.findViewById(R.id.img_leave);
        img_keep=(Button)view.findViewById(R.id.img_keep);
        img_end=(Button)view.findViewById(R.id.img_end);
        img_sign_in.setOnClickListener(this);
        img_leave.setOnClickListener(this);
        img_keep.setOnClickListener(this);
        img_end.setOnClickListener(this);
        img_state=(Button)view.findViewById(R.id.img_state);
        img_state.setOnClickListener(this);
    }

    private void refresh() {
        one_refresh.setColorSchemeResources(R.color.colorPrimary);
        one_refresh.setRefreshing(true);
        refreshState();
        onResume();
    }
    private void refreshState() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        one_refresh.setRefreshing(false);
                    }
                });

            }
        }).start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (requestCode){
           case 1:
               if (data != null) {
                   Bundle bundle = data.getExtras();
                   if (bundle != null) {
                       if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                           String result = bundle.getString(XQRCode.RESULT_DATA);
                           //ToastUtils.toast("解析结果:" + result, Toast.LENGTH_LONG);
                           //
                           if (isUrl(result)){
                               Intent intent=new Intent(getContext(),webActivity.class);
                               intent.putExtra("url", result);
                               startActivity(intent);
                           }else {
                               switch (result){
                                   case "E401":
                                       send("签到",result);
                                       break;
                                   case "M206":
                                       send("签到",result);
                                       break;
                                   case "M201":
                                       send("签到",result);
                                       break;
                                   case "M211":
                                       send("签到",result);
                                       break;
                                   case "M101":
                                       send("签到",result);
                                       break;
                                   case "W408":
                                       send("签到",result);
                                       break;
                                   case "W401":
                                       send("签到",result);
                                       break;
                                   case "W207":
                                       send("签到",result);
                                       break;
                                   default:
                                       Toast.makeText(getContext(),"解析结果:" + result,Toast.LENGTH_SHORT).show();
                                       break;
                               }
                           }
                       } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                           //ToastUtils.toast("解析二维码失败", Toast.LENGTH_LONG);
                           Toast.makeText(getContext(),"解析二维码失败",Toast.LENGTH_SHORT).show();
                       }
                   }
               }
               break;
       }

    }
    /**
     * 点击按钮之后，发送状态
     */
    private void send(String value, String result) {
        String url1="http://192.168.43.123:8080/AndroidWeb/CETS";
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody body=new FormBody.Builder()
                .add("state",value)
                .add("room",result)
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
                if(str.equals("签到成功")){
                    Toast.makeText(getContext(),"签到已成功，请返回上一步",Toast.LENGTH_SHORT).show();
                    ShareUtils.putBoolean(getContext().getApplicationContext(),"married",true);

                }else {
                    Toast.makeText(getContext(),"签到失败",Toast.LENGTH_SHORT).show();
                }

                Looper.loop();
            }
        });
    }

    /**
     * 判断二维码扫描的结果是否为url
     * @param result
     * @return
     */
    private boolean isUrl(String result) {
        result=result.toLowerCase();
        String [] regex= {"http://","https://"};
        boolean isUrl=false;
        for(int i=0;i<regex.length;i++){
            isUrl=isUrl || (result.contains(regex[i]))&& result.indexOf(regex[i])==0;
        }
        return isUrl;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_state:
                Toast.makeText(getActivity(),content,Toast.LENGTH_SHORT).show();
                /**
                 * 把预约成功的具体信息保存到本地数据库里面，供数据分析用
                 */
                SHUJUKU();
                break;
            case R.id.img_sign_in:
                        /**
                         * 把预约成功的具体信息保存到本地数据库里面，供数据分析用
                         */
                        //SHUJUKU();
                            if (state.equals("0") && !success) {
                                SharedPreferences.Editor editor4 = getContext().getSharedPreferences("data", Context.MODE_PRIVATE)
                                        .edit();
                                editor4.putString("STATE", "1");
                                editor4.apply();
                                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                                startActivityForResult(intent, 1);
                            }else {
                                Toast.makeText(getContext(),"已签到",Toast.LENGTH_SHORT).show();
                            }

                break;
            case R.id.img_leave:
                        if (state.equals("1")){
                            DELEte();
                            SharedPreferences.Editor editor = getContext().getSharedPreferences("data", Context.MODE_PRIVATE)
                                    .edit();
                            editor.putString("STATE","2");
                            editor.apply();
                            send("暂离");
                            /**
                             * 弹出一个对话框
                             */
                            new SweetAlertDialog(getContext())
                                    .setTitleText("温馨提示")
                                    .setContentText("学习之余也别忘记休息，给自己三十分钟的放松时间吧")
                                    .show();
                            Intent intent1=new Intent(getContext(), MyService.class);
                            getContext().startService(intent1);
                        }else{
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("对不起...")
                                    .setContentText("请确认自己操作无误，欢迎去QQ群向管理员提交BUG")
                                    .show();
                        }
                break;
            case R.id.img_keep:

                        if (state.equals("2")){
                            DELEte();
                            SharedPreferences.Editor editor2 = getContext().getSharedPreferences("data", Context.MODE_PRIVATE)
                                    .edit();
                            editor2.putString("STATE","3");
                            editor2.apply();
                            send("续座");
                            /**
                             * 刷新
                             */
                            refresh();

                        }else {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("对不起...")
                                    .setContentText("请确认自己操作无误，欢迎去QQ群向管理员提交BUG")
                                    .show();
                        }
                break;
            case R.id.img_end:
                        send("结束");
                        ShareUtils.deleShare(getContext().getApplicationContext(),"married");
                        SharedPreferences.Editor editor1 = getContext().getSharedPreferences("data", Context.MODE_PRIVATE)
                                .edit();
                       editor1.clear().apply();
                    /**
                     * 刷新
                     */
                    refresh();
                    /**
                 * 暂离，续座，不可点击
                 */

                break;
        }
    }

    /**
     * 数据库操作
     */
    private void SHUJUKU() {

        Qiandao qiandao= new Qiandao();
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month= now.get(Calendar.MONTH)+1;
        int day =now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute =now.get(Calendar.MINUTE);
        int second =now.get(Calendar.SECOND);
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        int w=now.get(Calendar.DAY_OF_WEEK)-1;
        if (w<0){
            w=0;
        }
        qiandao.setYear(year+"");//存年
        qiandao.setMonth(month+"");//存月
        qiandao.setDay(day+"");//存日
        qiandao.setXingqi(weekDays[w]);//存星期
        qiandao.setTime(hour+":"+minute+":"+second);//存时间点
        qiandao.setRoom("中区206");//存房间
        qiandao.setSeatid(56+"");//存座位号
        qiandao.save();//保存
    }


    private void DELEte() {
        SharedPreferences.Editor editor = getContext().getSharedPreferences("data", Context.MODE_PRIVATE)
                .edit();
        editor.clear().apply();
    }
    /**
     * 点击按钮之后，发送状态
     */
    private void send(String str) {
        String url1="http://192.168.43.123:8080/AndroidWeb/CETS";
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
