package com.imagesharing.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewImageAdapter extends BaseAdapter {

    private Context context;
    private List<String> imageUrlList;

    public NewImageAdapter(Context context, List<String> imageUrlList) {
        this.context = context;
        this.imageUrlList = imageUrlList;
    }

    @Override
    public int getCount() {
        return imageUrlList.size();
    }

    @Override
    public Object getItem(int i) {
        return imageUrlList. get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
        } else {
            imageView = (ImageView) view;
        }

        // 使用 Glide 加载图片
        Glide.with(context)
                .load(imageUrlList.get(i))
                .into(imageView);

        return imageView;
    }
}
