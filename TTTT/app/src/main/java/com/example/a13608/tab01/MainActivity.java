package com.example.a13608.tab01;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.a13608.tab01.fragement.firstfragement;
import com.example.a13608.tab01.fragement.fourfragement;
import com.example.a13608.tab01.fragement.thridfragement;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private LinearLayout tab01;

    private LinearLayout tab03;
    private LinearLayout tab04;
    private ImageButton tab01_img;

    private ImageButton tab03_img;
    private ImageButton tab04_img;
    private Fragment fragment01;

    private Fragment fragment03;
    private Fragment fragment04;
    private ViewPager mViewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private List<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题，在setContentView（）方法之前
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏

        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        setSelect(0);

    }
    private void initEvent() {
         Connector.getDatabase();
        tab01.setOnClickListener(this);

        tab03.setOnClickListener(this);
        tab04.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tab_01:
                setSelect(0);
                break;
            case R.id.tab_03:
                setSelect(1);
                break;
            case R.id.tab_04:
                setSelect(2);
                break;
        }
    }
    private void setTab(int i){
        restImg();
        /**
         * 将图片设置为暗色
         * 设置内容区域
         */
        switch (i){
            case 0:
                tab01_img.setImageResource(R.drawable.my_preordain_pressed);
                break;
            case 1:
                tab03_img.setImageResource(R.drawable.other_preordain_pressed);
                break;
            case 2:
                tab04_img.setImageResource(R.drawable.menu_more_pressed);
                break;
            default:
                break;
        }
    }
    private void setSelect(int i){
        setTab(i);
        mViewPager.setCurrentItem(i);
    }

    /**
     * 将图片设置为暗色
     */
    private void restImg() {
        tab01_img.setImageResource(R.drawable.my_preordain_normal);
        tab03_img.setImageResource(R.drawable.other_preordain_normal);
        tab04_img.setImageResource(R.drawable.menu_more_normal);
    }
    private void initView() {
        tab01=(LinearLayout)findViewById(R.id.tab_01);
        tab03=(LinearLayout)findViewById(R.id.tab_03);
        tab04=(LinearLayout)findViewById(R.id.tab_04);
        tab01_img=(ImageButton)findViewById(R.id.tab_01_image);
        tab03_img=(ImageButton)findViewById(R.id.tab_03_image);
        tab04_img=(ImageButton)findViewById(R.id.tab_04_image);
        mViewPager=(ViewPager)findViewById(R.id.id_viewpager);
        fragment01=new firstfragement();
        fragment03=new thridfragement();
        fragment04=new fourfragement();

        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(fragment01);
        fragmentList.add(fragment03);
        fragmentList.add(fragment04);
        fragmentPagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentitem=mViewPager.getCurrentItem();
                setTab(currentitem);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
