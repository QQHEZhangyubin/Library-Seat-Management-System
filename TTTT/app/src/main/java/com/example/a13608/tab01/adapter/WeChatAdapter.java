package com.example.a13608.tab01.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a13608.tab01.R;
import com.example.a13608.tab01.entity.WeChatData;
import com.example.a13608.tab01.utils.Glideutil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13608 on 2018/6/3.
 */

public class WeChatAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<WeChatData> weChatDataList;
    private WeChatData weChatData;
    private int width,height;
    private WindowManager windowManager;

    public WeChatAdapter(Context mContext, List<WeChatData> weChatDataList) {
        this.mContext = mContext;
        this.weChatDataList = weChatDataList;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        //width = windowManager.getDefaultDisplay().getWidth();
       // height = windowManager.getDefaultDisplay().getHeight();
    }

    @Override
    public int getCount() {
        return weChatDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return weChatDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view==null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.wechat_list_item,null);
            viewHolder.img = (ImageView) view.findViewById(R.id.iv_firstImg);
            viewHolder.title = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.source = (TextView) view.findViewById(R.id.tv_source);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        weChatData = weChatDataList.get(i);
        viewHolder.title.setText(weChatData.getTitle());
        viewHolder.source.setText(weChatData.getSource());
        //加载图片
        if (!TextUtils.isEmpty(weChatData.getFirstImg())){
            Glideutil.glidesize(mContext,weChatData.getFirstImg(),200,200,viewHolder.img);
        }

        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
    class ViewHolder {
            private ImageView img;
            private TextView title;
            private TextView source;
    }
}
