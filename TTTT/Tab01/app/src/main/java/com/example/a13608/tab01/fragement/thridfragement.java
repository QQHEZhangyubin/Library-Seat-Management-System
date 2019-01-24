package com.example.a13608.tab01.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a13608.tab01.R;
import com.example.a13608.tab01.avtivity.RoomActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 13608 on 2018/5/6.
 */

public class
thridfragement extends Fragment implements View.OnClickListener{
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private List<String> list;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab03,container,false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        button1=(Button)view.findViewById(R.id.bt_1);
        button2=(Button)view.findViewById(R.id.bt_2);
        button3=(Button)view.findViewById(R.id.bt_3);
        button4=(Button)view.findViewById(R.id.bt_4);
        button5=(Button)view.findViewById(R.id.bt_5);
        button6=(Button)view.findViewById(R.id.bt_6);
        button7=(Button)view.findViewById(R.id.bt_7);
        button8=(Button)view.findViewById(R.id.bt_8);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        initData();
    }

    private void initData() {
        list=new ArrayList<String>();
        list.add("东区401");//0
        list.add("中区206");//1
        list.add("中区201");//2
        list.add("中区211");//3
        list.add("中区101");//4
        list.add("西区207");//5
        list.add("西区401");//6
        list.add("西区408");//7
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_1:
                Intent intent1=new Intent(getActivity(),RoomActivity.class);
                intent1.putExtra("1", list.get(0));
                startActivity(intent1);
                break;
            case R.id.bt_2:
                Intent intent2=new Intent(getActivity(),RoomActivity.class);
                intent2.putExtra("1",list.get(1));
                startActivity(intent2);
                break;
            case R.id.bt_3:
                Intent intent3=new Intent(getActivity(),RoomActivity.class);
                intent3.putExtra("1",list.get(2));
                startActivity(intent3);
                break;
            case R.id.bt_4:
                Intent intent4=new Intent(getActivity(),RoomActivity.class);
                intent4.putExtra("1",list.get(3));
                startActivity(intent4);
                break;
            case R.id.bt_5:
                Intent intent5=new Intent(getActivity(),RoomActivity.class);
                intent5.putExtra("1",list.get(4));
                startActivity(intent5);
                break;
            case R.id.bt_6:
                Intent intent6=new Intent(getActivity(),RoomActivity.class);
                intent6.putExtra("1",list.get(5));
                startActivity(intent6);
                break;
            case R.id.bt_7:
                Intent intent7=new Intent(getActivity(),RoomActivity.class);
                intent7.putExtra("1",list.get(6));
                startActivity(intent7);
                break;
            case R.id.bt_8:
                Intent intent8=new Intent(getActivity(),RoomActivity.class);
                intent8.putExtra("1",list.get(7));
                startActivity(intent8);
                break;
            default:
                break;
    }
}








}
