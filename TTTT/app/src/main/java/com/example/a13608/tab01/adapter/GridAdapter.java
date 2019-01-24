package com.example.a13608.tab01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a13608.tab01.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private ArrayList<String> listUrls;
    private LayoutInflater inflater;
    private Context mContext;

    public GridAdapter(ArrayList<String> listUrls, Context mContext) {
        this.listUrls = listUrls;
        this.mContext = mContext;
        if(listUrls.size() == 2){
            listUrls.remove(listUrls.size()-1);
        }
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return listUrls.size();
    }

    @Override
    public Object getItem(int i) {
        return listUrls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.image_item, parent,false);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView11);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final String path=listUrls.get(position);
        if (path.equals("paizhao")){
            holder.image.setImageResource(R.mipmap.find_add_img);
        }else {
            Glide.with(mContext)
                    .load(path)
                    .placeholder(R.mipmap.default_error)
                    .error(R.mipmap.default_error)
                    .centerCrop()
                    .crossFade()
                    .into(holder.image);
        }
        return convertView;
    }
    class ViewHolder {
        ImageView image;
    }
}
