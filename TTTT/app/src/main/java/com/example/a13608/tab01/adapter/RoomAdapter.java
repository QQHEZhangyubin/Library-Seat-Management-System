package com.example.a13608.tab01.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a13608.tab01.R;
import com.example.a13608.tab01.avtivity.RoomActivity;
import com.example.a13608.tab01.entity.Room;

import java.util.List;

/**
 * Created by 13608 on 2018/6/6.
 */

public class RoomAdapter  extends RecyclerView.Adapter<RoomAdapter.ViewHolder>{
    private Context mContext;
    private List<Room> roomList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView roomImg;
        TextView roomName;
        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            roomImg = (ImageView)view.findViewById(R.id.room_img);
            roomName = (TextView)view.findViewById(R.id.room_name);
        }
    }

    public RoomAdapter(List<Room> roomList) {
        this.roomList = roomList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext==null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.room_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Room room =roomList.get(position);
                Intent intent = new Intent(mContext, RoomActivity.class);
                intent.putExtra("1",room.getName());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Room room= roomList.get(position);
        holder.roomName.setText(room.getName());
        Glide.with(mContext).load(room.getImg()).into(holder.roomImg);
    }
    @Override
    public int getItemCount() {
        return roomList.size();
    }
}
