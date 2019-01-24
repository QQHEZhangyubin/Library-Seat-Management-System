package com.example.a13608.tab01.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.a13608.tab01.R;
import com.example.a13608.tab01.adapter.RoomAdapter;
import com.example.a13608.tab01.avtivity.RoomActivity;
import com.example.a13608.tab01.entity.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 13608 on 2018/5/6.
 */

public class thridfragement extends Fragment implements View.OnClickListener{
    private SwipeRefreshLayout srl;
    private Room[] rooms={new Room(R.drawable.room1,"东区401自习室"),
            new Room(R.drawable.room1,"中区101自习室"),
            new Room(R.drawable.room1,"中区201自习室"),
            new Room(R.drawable.room1,"中区206自习室"),
            new Room(R.drawable.room1,"中区211自习室"),
            new Room(R.drawable.room1,"西区207自习室"),
            new Room(R.drawable.room1,"西区401自习室"),
            new Room(R.drawable.room1,"西区408自习室")};
    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private String strings="";
    private List<String> stringList=new ArrayList<>();
    private List<Room> roomList = new ArrayList<>();

    @Override
    public void onClick(View view) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab03,container,false);
        findView(view);
        return view;
    }
    private void findView(View view) {
        initData();
        srl=(SwipeRefreshLayout)view.findViewById(R.id.srl);
        srl.setColorSchemeResources(R.color.colorPrimary);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        recyclerView= (RecyclerView)view.findViewById(R.id.rv_two);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        roomAdapter =new RoomAdapter(roomList);
        recyclerView.setAdapter(roomAdapter);
    }

    private void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        roomList.clear();
                        initData();
                        roomAdapter.notifyDataSetChanged();
                        srl.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initData() {
        roomList.clear();
        String str[] = { "0", "1", "2", "3", "4", "5", "6", "7" };
        permutation(str, 0, str.length);
        int length =stringList.size();
        int num=(int)(Math.random()*length);
        char[] chars=stringList.get(num).toCharArray();
        for (int a=0;a<chars.length;a++){
            String value=String.valueOf(chars[a]);
            roomList.add(rooms[Integer.parseInt(value)]);
        }
    }

    private void permutation(String[] str, int start, int end) {
        if (start == end - 1) {
            for (int i = 0; i < end; i++) {
                strings+=str[i];//15236407---->0  ,12304567 ->1
            }
            stringList.add(strings);
            strings="";
        } else {

            for (int i = start; i < end; i++) {

                swap(str, start, i);
                permutation(str, start + 1, end);
                swap(str, start, i);
            }
        }
    }
    private void swap(String[] str, int start, int end){
        String tmep = str[start];
        str[start] = str[end];
        str[end] = tmep;
    }

}
