package com.example.a13608.tab01.avtivity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a13608.tab01.R;

import java.util.ArrayList;
import java.util.List;

public class guideActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager mViewPager;
    private List<View> mList=new ArrayList<>();
    private View view1,view2,view3;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
    }

    private void initView() {
        mViewPager=findViewById(R.id.mViewPager);
        view1=View.inflate(this,R.layout.pager_item_one,null);
        view2=View.inflate(this,R.layout.pager_item_two,null);
        view3=View.inflate(this,R.layout.pager_item_three,null);
        view3.findViewById(R.id.btn_start).setOnClickListener(this);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);
        mViewPager.setAdapter(new GuideApater());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        iv_back.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
            case R.id.iv_back:
                startActivity(new Intent(this,loginActivity.class));
                finish();
                break;
        }
    }

    private class GuideApater extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=mList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
