package com.example.a13608.tab01.avtivity;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13608.tab01.MainActivity;
import com.example.a13608.tab01.R;
import com.example.a13608.tab01.utils.ShareUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.a13608.tab01.utils.StaticClass.LOGIN_URL;

public class loginActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private EditText et_name;
    private EditText et_password;
    private CheckBox keep_password;
    private Button btnLogin;
    private Button btn_registered;
    private TextView tv_forget;
    private boolean isuser;
    private SweetAlertDialog pDialog;
    //private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                final String user=et_name.getText().toString().trim();
                String password=et_password.getText().toString().trim();
                if(!TextUtils.isEmpty(user) & !TextUtils.isEmpty(password)){
                    pDialog=new SweetAlertDialog(loginActivity.this,SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading...");
                    pDialog.setCancelable(true);
                    pDialog.show();
                    isuser(user,password);
                }else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_registered:
                startActivity(new Intent(loginActivity.this,registerActivity.class));
                break;
            case R.id.tv_forget:
                AlertDialog.Builder dialog=new AlertDialog.Builder(loginActivity.this);
                dialog.setTitle("小助手");
                dialog.setMessage("我们建立了一个QQ群提供帮助！");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
                break;
            default:
                break;
        }
    }

    private void isuser(String user, String password) {
        final String login_url=LOGIN_URL;
        OkHttpClient okHttpClient;
        OkHttpClient.Builder builder =new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        Toast.makeText(loginActivity.this,cookies+"",Toast.LENGTH_SHORT).show();
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();

                    }
                });
        RequestBody body= new FormBody.Builder()
                .add("user",user)
                .add("password",password)
                .build();
        final Request request=new Request.Builder()
                .url(login_url)
                .post(body)
                .build();
        okHttpClient=builder.build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                String str=response.body().string();
                if (str.equals("登陆成功")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pDialog.dismissWithAnimation();
                                }
                            });

                    ShareUtils.putBoolean(loginActivity.this,"keeppass",keep_password.isChecked());
                    if (keep_password.isChecked()) {
                        //记住用户名和密码
                        ShareUtils.putString(loginActivity.this, "name", et_name.getText().toString().trim());
                        ShareUtils.putString(loginActivity.this, "password", et_password.getText().toString().trim());
                    } else {
                        ShareUtils.deleShare(loginActivity.this, "name");
                        ShareUtils.deleShare(loginActivity.this, "password");
                    } ShareUtils.putBoolean(loginActivity.this,"keeppass",keep_password.isChecked());
                    if (keep_password.isChecked()) {
                        //记住用户名和密码
                        ShareUtils.putString(loginActivity.this, "name", et_name.getText().toString().trim());
                        ShareUtils.putString(loginActivity.this, "password", et_password.getText().toString().trim());
                    } else {
                        ShareUtils.deleShare(loginActivity.this, "name");
                        ShareUtils.deleShare(loginActivity.this, "password");
                    }
                    startActivity(new Intent(loginActivity.this, MainActivity.class));
                }else {

                    Toast.makeText(loginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();

                }
               // handler.sendMessage(message);
                Looper.loop();
            }
        });
       // return isuser;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initView() {
        et_name=(EditText)findViewById(R.id.et_name);
        et_password=findViewById(R.id.et_password);
        btnLogin=findViewById(R.id.btnLogin);
        btn_registered=findViewById(R.id.btn_registered);
        tv_forget= findViewById(R.id.tv_forget);
        btnLogin.setOnClickListener(this);
        btn_registered.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        keep_password=findViewById(R.id.keep_password);

        boolean iskeep= ShareUtils.getBoolean(this,"keeppass",false);
        keep_password.setChecked(iskeep);
        if (iskeep){
            String name=ShareUtils.getString(this,"name","");
            String password=ShareUtils.getString(this,"password","");
            et_name.setText(name);
            et_password.setText(password);
        }
    }
}
