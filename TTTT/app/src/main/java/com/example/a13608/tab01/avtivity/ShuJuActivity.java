package com.example.a13608.tab01.avtivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a13608.tab01.R;
import com.example.a13608.tab01.adapter.ShuJuAdapter;
import com.example.a13608.tab01.entity.Qiandao;

import org.litepal.LitePal;

import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ShuJuActivity extends BaseActivity {
    private ListView lv_shuju;
    private String ti;//作为删除数据库的标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shu_ju);
        final List<Qiandao> qiandaos = LitePal.findAll(Qiandao.class);
        Collections.reverse(qiandaos);
        /**
         * 得到签到保存的数据
         */
        lv_shuju=(ListView)findViewById(R.id.lv_shuju);
        ShuJuAdapter shuJuAdapter =new ShuJuAdapter(ShuJuActivity.this,qiandaos);
        lv_shuju.setAdapter(shuJuAdapter);
        lv_shuju.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(ShuJuActivity.this,qiandaos.get(i).getTime(),Toast.LENGTH_SHORT).show();
                ti=qiandaos.get(i).getTime();
                new SweetAlertDialog(ShuJuActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("删除这条记录?")
                        .setContentText("每个记录都是你努力的见证者!")
                        .setConfirmText("丢弃")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                LitePal.deleteAll(Qiandao.class, "time = ?" , ti);
                                sDialog
                                        .setTitleText("已删除")
                                        .setContentText("曾经的那段时间无法找回")
                                        .setConfirmText("好吧")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();

                return false;
            }
        });
    }
}
