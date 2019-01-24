package com.example.a13608.tab01.avtivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a13608.tab01.R;
import com.example.a13608.tab01.utils.ShareUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateActivity extends AppCompatActivity {
    private TextView et_wangming;
    private TextView et_sign;
    private TextView et_xueyaun;
    private TextView et_class;
    private TextView et_sex;
    private TextView et_email;
    private Button btn_update_ok;
    private String s0,s1,s2,s3,s4,s5;//从注册里得到的
    private String wangming,sign,xueyuan,cl,eamil,sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getdata();//得到注册时保存的用户信息
        et_wangming=(TextView)findViewById(R.id.et_wangming);
        et_wangming.setText(s0);
        et_sign=(TextView)findViewById(R.id.et_sign);
        et_sign.setText(s4);
        et_xueyaun=(TextView)findViewById(R.id.et_xueyaun);
        et_xueyaun.setText(s2);
        et_class=(TextView)findViewById(R.id.et_class);
        et_class.setText(s1);
        et_sex=(TextView)findViewById(R.id.et_sex);
        et_sex.setText(s5);
        et_email=(TextView)findViewById(R.id.et_email);
        et_email.setText(s3);
        btn_update_ok=(Button)findViewById(R.id.btn_update_ok);

        btn_update_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 wangming = et_wangming.getText().toString();//网名
                sign=et_sign.getText().toString();//签名
                xueyuan = et_xueyaun.getText().toString();//学院
                 cl=et_class.getText().toString();//班级
                 eamil=et_email.getText().toString();//邮箱
                 sex =et_sex.getText().toString();//性别
                /**
                 * 得到数据传给服务器
                 */
                sen(wangming,sex,sign,xueyuan,cl,eamil);
            }
        });
    }

    private void getdata() {
        s0=ShareUtils.getString(getApplicationContext(),"WW","");//网名
        s1=ShareUtils.getString(getApplicationContext(),"BB","");//班级
        s2= ShareUtils.getString(getApplicationContext(),"XX","");//学院
        s3= ShareUtils.getString(getApplicationContext(),"EE","");//邮箱
        s4=ShareUtils.getString(getApplicationContext(),"SI","");//个性签名
        s5=ShareUtils.getString(getApplicationContext(),"SS","");//性别
    }

    private void sen(final String wangming, final String sex, final String sign, final String xueyuan, final String cl, final String eamil) {
        String update_url="";
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody body =new FormBody.Builder()
                .add("w",wangming)
                .add("sex",sex)
                .add("sign",sign)
                .add("x",xueyuan)
                .add("cl",cl)
                .add("e",eamil)
                .build();
        Request request=new Request.Builder()
                .url(update_url)
                .post(body)
                .addHeader("","")   //添加cookies值
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回值，弹出提示,删除保存的旧数据，保存新数据
                ShareUtils.deleShare(getApplicationContext(),"WW");
                ShareUtils.deleShare(getApplicationContext(),"BB");
                ShareUtils.deleShare(getApplicationContext(),"XX");
                ShareUtils.deleShare(getApplicationContext(),"EE");
                ShareUtils.deleShare(getApplicationContext(),"SI");
                ShareUtils.deleShare(getApplicationContext(),"SS");
                //保存到SharePreference
                ShareUtils.putString(getApplicationContext(),"WW",wangming);//网名
                ShareUtils.putString(getApplicationContext(),"BB",cl);//班级
                ShareUtils.putString(getApplicationContext(),"XX",xueyuan);//学院
                ShareUtils.putString(getApplicationContext(),"SS",sex);//性别
                ShareUtils.putString(getApplicationContext(),"SI",sign);//个性签名
                ShareUtils.putString(getApplicationContext(),"EE",eamil);//邮箱

            }
        });
    }
}
