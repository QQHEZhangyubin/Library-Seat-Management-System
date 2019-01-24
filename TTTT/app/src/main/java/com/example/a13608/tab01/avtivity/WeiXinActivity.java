package com.example.a13608.tab01.avtivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a13608.tab01.R;
import com.example.a13608.tab01.adapter.WeChatAdapter;
import com.example.a13608.tab01.entity.WeChatData;
import com.example.a13608.tab01.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeiXinActivity extends AppCompatActivity {
    private ListView lv_wechat;
    private List<WeChatData> dataList = new ArrayList<>();
    private List<String> titlelist = new ArrayList<>();
    private List<String> urllist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_xin);
        lv_wechat =(ListView) findViewById(R.id.lv_wechat);
        String wechat_url ="http://v.juhe.cn/weixin/query?key= "+ StaticClass.WECHAT_KEY+"?48";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request= new Request.Builder()
                .url(wechat_url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                try {
                    parseJson(str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //点击事件
        lv_wechat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(WeiXinActivity.this,Web_wechatActivity.class);
                intent.putExtra("title",titlelist.get(i));
                intent.putExtra("url",urllist.get(i));
                startActivity(intent);
            }
        });
    }

    private void parseJson(String str) throws JSONException {
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONObject object = jsonObject.getJSONObject("result");
            JSONArray jsonArray =object.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                WeChatData data =new WeChatData();
                String title = jsonObject1.getString("title");
                String url = jsonObject1.getString("url");
                data.setFirstImg(jsonObject1.getString("firstImg"));
                data.setTitle(title);
                data.setSource(jsonObject1.getString("source"));

                titlelist.add(title);
                urllist.add(url);
                dataList.add(data);

            }
            WeChatAdapter adapter =new WeChatAdapter(WeiXinActivity.this,dataList);
            lv_wechat.setAdapter(adapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
