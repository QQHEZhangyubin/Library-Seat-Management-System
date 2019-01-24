package com.example.a13608.tab01.avtivity;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.a13608.tab01.R;
import com.example.a13608.tab01.utils.ShareUtils;
import com.example.a13608.tab01.utils.StaticClass;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class registerActivity extends AppCompatActivity {
    private EditText et_user,et_colleague,et_class,et_age,et_desc,et_pass,et_password,et_email;
    private Button btnRegistered;
    private RadioGroup mRadioGroup;

    private String sex="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
       btnRegistered.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String xuehao=et_user.getText().toString().trim();
               String xueyuan=et_colleague.getText().toString().trim();
               String banji=et_class.getText().toString().trim();
               String wangming=et_age.getText().toString().trim();
               String yuanyan=et_desc.getText().toString().trim();
               String mima=et_pass.getText().toString().trim();
               String mima2=et_password.getText().toString().trim();
               String youxiang=et_email.getText().toString().trim();
               if (!TextUtils.isEmpty(xuehao) & !TextUtils.isEmpty(xueyuan) & !TextUtils.isEmpty(banji)
                       & !TextUtils.isEmpty(wangming) & !TextUtils.isEmpty(yuanyan) & !TextUtils.isEmpty(mima)
                       & !TextUtils.isEmpty(mima2) & !TextUtils.isEmpty(youxiang)){
                   if (mima.equals(mima2)){
                       mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                           @Override
                           public void onCheckedChanged(RadioGroup radioGroup, int i) {
                               if (i==R.id.rb_boy){
                                   sex="男";
                               }else if(i==R.id.rb_girl){
                                   sex="女";
                               }
                           }
                       });
                       /**
                        * 发送注册信息给服务器
11                        */
                       sendmessage(xuehao,xueyuan,banji,wangming,yuanyan,sex,mima,youxiang);

                   }else {
                       Toast.makeText(registerActivity.this,"输入密码不一致",Toast.LENGTH_SHORT).show();
                   }

               }else {
                   Toast.makeText(registerActivity.this,"注册信息不能为空",Toast.LENGTH_SHORT).show();
               }

           }

           private void sendmessage(String xuehao, final String xueyuan, final String banji, final String wangming, final String yuanyan, final String sex, String mima, final String youxiang) {
                String url= StaticClass.REGIISTER_URL;
                OkHttpClient okHttpClient=new OkHttpClient();
                RequestBody body= new FormBody.Builder()
                       .add("xuehao",xuehao)
                       .add("xueyuan",xueyuan)
                       .add("banji",banji)
                       .add("wangming",wangming)
                       .add("yuanyan",yuanyan)
                       .add("sex",sex)
                       .add("mima",mima)
                       .add("youxiang",youxiang)
                       .build();
               Request request=new Request.Builder()
                       .url(url)
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
                       String result=response.body().string();
                       Toast.makeText(registerActivity.this,result,Toast.LENGTH_SHORT).show();
                       //保存到SharePreference
                       ShareUtils.putString(getApplicationContext(),"WW",wangming);//网名
                       ShareUtils.putString(getApplicationContext(),"BB",banji);//班级
                       ShareUtils.putString(getApplicationContext(),"XX",xueyuan);//学院
                       ShareUtils.putString(getApplicationContext(),"SS",sex);//性别
                       ShareUtils.putString(getApplicationContext(),"SI",yuanyan);//个性签名
                       ShareUtils.putString(getApplicationContext(),"EE",youxiang);//邮箱
                       Looper.loop();
                   }
               });
           }
       });
    }



    private void initView() {
        et_user=findViewById(R.id.et_user);
        et_colleague=findViewById(R.id.et_colleague);
        et_class=findViewById(R.id.et_class);
        et_age=findViewById(R.id.et_age);
        et_desc=findViewById(R.id.et_desc);
        et_pass=findViewById(R.id.et_pass);
        et_password=findViewById(R.id.et_password);
        et_email=findViewById(R.id.et_email);
        btnRegistered=findViewById(R.id.btnRegistered);
        mRadioGroup=findViewById(R.id.mRadioGroup);
    }
}
