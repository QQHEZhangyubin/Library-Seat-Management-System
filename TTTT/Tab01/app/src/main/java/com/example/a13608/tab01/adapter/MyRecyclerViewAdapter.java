package com.example.a13608.tab01.adapter;

import android.content.Context;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a13608.tab01.R;
import com.example.a13608.tab01.avtivity.RoomActivity;
import com.example.a13608.tab01.entity.Desk;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 13608 on 2018/5/13.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<Desk> deskslist;
    private Context mContext;
    AppCompatSpinner spinner1;
    AppCompatSpinner spinner2;
    AppCompatSpinner spinner3;
    private List<String> list=new ArrayList<>();
    private List<String> listt=new ArrayList<>();
    private String string="";
    private String value1,value2,value3;


    static class ViewHolder extends RecyclerView.ViewHolder{
        View deskView;
        TextView textView;
        ImageView imageView;
        public ViewHolder(View view){
            super(view);
            deskView=view;
            textView=view.findViewById(R.id.desk_number);
            imageView=view.findViewById(R.id.desk_image);
        }
    }
    public MyRecyclerViewAdapter(Context context,List<Desk> deskList){
        mContext=context;
        deskslist=deskList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_item, parent, false);
        final ViewHolder viewHolder=new ViewHolder(view);

        //ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.deskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p=viewHolder.getAdapterPosition();
                Desk desk=deskslist.get(p);
                //Toast.makeText(view.getContext(),"you clicked view "+desk.getNumber(),Toast.LENGTH_SHORT).show();
                /**
                 *
                 */
                Popdialog(view,desk.getNumber());

                //connection(view,desk.getNumber());
            }

            private void Popdialog(final View v,final String number) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater=LayoutInflater.from(v.getContext());
                final View view=inflater.inflate(R.layout.select,null);
                builder.setIcon(R.drawable.time);
                builder.setTitle("预约位置号"+number+"\n"+"请选择预约时间");
                builder.setView(view);
                spinner1=(AppCompatSpinner)view.findViewById(R.id.sp_select_email);
                spinner2=(AppCompatSpinner)view.findViewById(R.id.sp_select_email1);
                spinner3=(AppCompatSpinner)view.findViewById(R.id.sp_select_email2);
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String []value=mContext.getResources().getStringArray(R.array.week);
                        value1=value[i];//得到今日和明日
                        //Toast.makeText(MainActivity.this,value1,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                if (list.size()==0){
                    for(int ii=8;ii<=21;ii++){
                        list.add(String.format("%02d",ii));
                    }
                }
                ArrayAdapter<String> arrayAdapter2=new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_item,list);
                arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter2);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        value2=list.get(i);

                        // Toast.makeText(MainActivity.this,value3,Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
        /*
        使用自定义的apapter
        myadapter myadapter=new myadapter(MainActivity.this,R.layout.item_select,list);
        spinner2.setAdapter(myadapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value2=list.get(i);//得到小时
                stringBuffer.append(value2);
                //Toast.makeText(MainActivity.this,value2,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        */
                if (listt.size()==0){
                    for (int j=0;j<60;j++){
                        listt.add(String.format("%02d",j));
                    }
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_item,listt);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(arrayAdapter);
                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        value3=listt.get(i);

                        // Toast.makeText(MainActivity.this,value3,Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /**
                         * 将选择的时间提交给服务器
                         */
                        //Toast.makeText(v.getContext(),number+value1+value2+value3,Toast.LENGTH_SHORT).show();
                        sendmessage(number,value1+value2+value3);

                    }

                    private void sendmessage(String number, String s) {
                        String url1="http://172.21.57.82:8080/AndroidWeb/Reserve";
                        OkHttpClient okHttpClient=new OkHttpClient();
                        RequestBody body=new FormBody.Builder()
                                .add("position",number)
                                .add("time",s)
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
                                Toast.makeText(mContext,str,Toast.LENGTH_SHORT).show();

                                Looper.loop();
                            }
                        });


                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Toast.makeText(mContext,"你取消了当前座位的预定",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }

            private void connection(final View view1, String number) {

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Desk desk=deskslist.get(position);
        holder.imageView.setImageResource(desk.getImgId());
        holder.textView.setText(desk.getNumber());
    }

    @Override
    public int getItemCount() {
        return deskslist.size();
    }

}