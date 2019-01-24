package com.example.a13608.tab01.avtivity;

import android.content.Intent;
import android.os.Looper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a13608.tab01.R;
import com.example.a13608.tab01.adapter.MyRecyclerViewAdapter;
import com.example.a13608.tab01.entity.Desk;
import com.example.a13608.tab01.service.MyService;
import com.example.a13608.tab01.utils.ShareUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RoomActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView iv_welcome;
    private List<Desk> deskList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Intent intent = getIntent();
        String position = intent.getStringExtra("1");
        /**
         * 将position发送给服务器端
         */
        iv_welcome=(ImageView)findViewById(R.id.iv_welcome);
        Glide.with(RoomActivity.this).load(R.mipmap.welcome).into(iv_welcome);
        connection(position);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(position);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager
                (6,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(RoomActivity.this,deskList);
        recyclerView.setAdapter(adapter);
    }

    private void connection(String position) {
        String url1="http://192.168.43.123:8080/AndroidWeb/QuaRoom";
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody body=new FormBody.Builder()
                .add("position",position)
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
                initDesk(str);
                Looper.loop();
            }
        });
    }

    private void initDesk(String str) {
        char[] chars = str.toCharArray();
        int length = chars.length;
        for (int i = 0; i < length; i++) {
            switch (chars[i]) {
                case 'A':
                    Desk available = new Desk(i+1 + "",R.drawable.seat_01);
                    deskList.add(available);
                    break;
                case 'R':
                    Desk notavailable = new Desk(i+1 + "", R.drawable.seat_02);
                    deskList.add(notavailable);
                    break;
                case 'T':
                    Desk using = new Desk(i+1 + "", R.drawable.seat_04);
                    deskList.add(using);
                    break;
                case 'U':
                    Desk left = new Desk(i+1 + "",R.drawable.seat_06);
                    deskList.add(left);
                    break;
                default:
                    break;
            }
        }
    }
    /**
     *
     */

}
