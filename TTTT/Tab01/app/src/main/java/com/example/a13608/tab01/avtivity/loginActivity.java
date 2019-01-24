package com.example.a13608.tab01.avtivity;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
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
    /*
    private Handler handler=new Handler(){
        public void handleMessage(Message mag){
            if (mag.what==1){
                isuser=true;
            }
        }
    };
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        //initAnimator();
    }
    private void initAnimator() {
        /**
         * a-->b
         */
        ObjectAnimator animator1=ObjectAnimator.ofFloat(imageView1,"alpha",1.0f,0f);
        ObjectAnimator animator2=ObjectAnimator.ofFloat(imageView2,"alpha",0f,1.0f);
        ObjectAnimator animatorScale1=ObjectAnimator.ofFloat(imageView1,"scaleX",1.0f,1.3f);
        ObjectAnimator animatorScale2=ObjectAnimator.ofFloat(imageView1,"scaleY",1.0f,1.3f);
        AnimatorSet animatorSet1=new AnimatorSet();
        animator1.setDuration(5000);
        animatorSet1.play(animator1).with(animator2).with(animatorScale1).with(animatorScale2);
        /**
         * b-->c
         */
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(imageView2, "alpha", 1.0f, 0f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(imageView3, "alpha", 0f, 1.0f);
        ObjectAnimator animatorScale3 = ObjectAnimator.ofFloat(imageView2, "scaleX", 1.0f, 1.3f);
        ObjectAnimator animatorScale4 = ObjectAnimator.ofFloat(imageView2, "scaleY", 1.0f, 1.3f);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.setDuration(5000);
        animator2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                imageView1.setScaleX(1.0f);
                imageView1.setScaleY(1.0f);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet2.play(animator3).with(animator4).with(animatorScale3).with(animatorScale4);

        /**
         * c-->a
         */
        ObjectAnimator animator7 = ObjectAnimator.ofFloat(imageView3, "alpha", 1.0f, 0f);
        ObjectAnimator animator8 = ObjectAnimator.ofFloat(imageView1, "alpha", 0f, 1.0f);
        ObjectAnimator animatorScale7 = ObjectAnimator.ofFloat(imageView3, "scaleX", 1.0f, 1.3f);
        ObjectAnimator animatorScale8 = ObjectAnimator.ofFloat(imageView3, "scaleY", 1.0f, 1.3f);
        AnimatorSet animatorSet4 = new AnimatorSet();
        animatorSet4.setDuration(5000);
        animatorSet4.play(animator7).with(animator8).with(animatorScale7).with(animatorScale8);
        /**
         *
         */
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playSequentially(animatorSet1,animatorSet2,animatorSet4);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // 将放大的View 复位
                imageView2.setScaleX(1.0f);
                imageView2.setScaleY(1.0f);
                imageView3.setScaleX(1.0f);
                imageView3.setScaleY(1.0f);

                // 循环播放
                animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animatorSet.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                final String user=et_name.getText().toString().trim();
                String password=et_password.getText().toString().trim();
                if(!TextUtils.isEmpty(user) & !TextUtils.isEmpty(password)){

                    isuser(user,password);
                   /*
                    if (isuser){


                    }else {

                    }
                    */
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
        final String login_url="http://172.21.57.82:8080/AndroidWeb/Landing";
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody body= new FormBody.Builder()
                .add("user",user)
                .add("password",password)
                .build();
        final Request request=new Request.Builder()
                .url(login_url)
                .post(body)
                .build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


               // Log.d("T",);
                //Toast.makeText(loginActivity.this,,Toast.LENGTH_SHORT).show();
                String str=response.body().string();
                //Message message=new Message();
                if (str.equals("登陆成功")){
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
                    Looper.prepare();
                    Toast.makeText(loginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
               // handler.sendMessage(message);

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
