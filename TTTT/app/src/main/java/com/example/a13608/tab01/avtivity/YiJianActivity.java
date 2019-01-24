package com.example.a13608.tab01.avtivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a13608.tab01.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YiJianActivity extends BaseActivity {
        private Button sunmit_opinion;
        private EditText et_opinion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yi_jian);
        sunmit_opinion = (Button)findViewById(R.id.sunmit_opinion);
        et_opinion = (EditText) findViewById(R.id.et_opinion);
        sunmit_opinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String opinion = et_opinion.getText().toString().trim();
                String url1="http://192.168.43.123:8080/AndroidWeb/Reserve";
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody body =new FormBody.Builder()
                        .add("opinion",opinion)
                        .build();
                Request request =new Request.Builder()
                        .url(url1)
                        .build();
                Call call =okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Looper.prepare();
                        diglog();
                        Looper.loop();
                    }
                    private void diglog() {
                        final AlertDialog dialog =new AlertDialog.Builder(YiJianActivity.this)
                                .setIcon(R.drawable.ic_launcher)
                                .setTitle("提交结果：")
                                .setCancelable(false)
                                .setMessage("提交成功，再次感谢您的支持")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                }).create();
                        dialog.show();
                    }
                });
            }
        });
    }
}
