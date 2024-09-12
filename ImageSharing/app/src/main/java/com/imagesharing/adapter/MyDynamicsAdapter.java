package com.imagesharing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.imagesharing.R;
import com.imagesharing.bean.Record;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyDynamicsAdapter extends BaseAdapter {
    private List<JSONObject> records;
    private Context context;

    public MyDynamicsAdapter(Context context, List<JSONObject> records) {
        this.records = records;
        this.context = context;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int i) {
        return records.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_my_dynamics, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView contentTextView = convertView.findViewById(R.id.contentTextView);
        TextView timeTextView = convertView.findViewById(R.id.timeTextView);
        ImageView imageView = convertView.findViewById(R.id.imageView);

        JSONObject record = records.get(position);

        try {
            titleTextView.setText(record.getString("title"));
            contentTextView.setText(record.getString("content"));
            timeTextView.setText(record.getString("createTime"));

            if (record.isNull("imageUrl")) {
                JSONArray imageUrlList = record.getJSONArray("imageUrlList");
                loadImages(imageUrlList, imageView);
            } else {
                imageView.setImageResource(R.drawable.default_image2);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        return convertView;
    }

    private void loadImages(JSONArray imageUrlList, ImageView ivImage) throws JSONException {
        int count = Math.min(imageUrlList.length(), 3);
        for (int i = 0; i < count; i++) {
            String imageUrl = imageUrlList.getString(i);
            Glide.with(context)
                    .load(imageUrl)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_miss))
                    .into(ivImage);
        }
    }

}
