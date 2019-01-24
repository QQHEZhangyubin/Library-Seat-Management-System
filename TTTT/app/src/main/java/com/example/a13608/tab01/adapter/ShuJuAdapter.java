package com.example.a13608.tab01.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a13608.tab01.R;
import com.example.a13608.tab01.avtivity.ShuJuActivity;
import com.example.a13608.tab01.entity.Qiandao;
import com.example.a13608.tab01.entity.Room;

import java.util.List;

/**
 * Created by 13608 on 2018/6/14.
 */

public class ShuJuAdapter extends BaseAdapter {
    private Context mcontext;
    private List<Qiandao> qiandaos;
    private Qiandao qianda;
    public ShuJuAdapter(Context mcontext, List<Qiandao> roomList) {
        this.mcontext = mcontext;
        this.qiandaos = roomList;
    }

    @Override
    public int getCount() {
        return qiandaos.size();
    }

    @Override
    public Object getItem(int i) {
        return qiandaos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewholder holder =null;
        if (view==null){
            holder=new Viewholder();
            view= LayoutInflater.from(mcontext).inflate(R.layout.shuju_item,null);
            holder.tv_room=(TextView)view.findViewById(R.id.shuju_room);
            holder.tv_seatid=(TextView)view.findViewById(R.id.shuju_seat);
            holder.tv_time=(TextView)view.findViewById(R.id.shuju_time);
            view.setTag(holder);
        }else {
            holder=(Viewholder) view.getTag();
        }
        qianda = qiandaos.get(i);
        holder.tv_seatid.setText(qianda.getSeatid()+"号");
        holder.tv_room.setText(qianda.getRoom()+"自习室");
        holder.tv_time.setText(qianda.getYear()+"年"+qianda.getMonth()+"月"+
                qianda.getDay()+"日   "+qianda.getXingqi()+"    "+qianda.getTime());
        return view;
    }
    class Viewholder {
        TextView tv_seatid;
        TextView tv_time;
        TextView tv_room;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
