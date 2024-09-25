package com.imagesharing.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imagesharing.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SecondCommentAdapter extends BaseAdapter {

    private final List<JSONObject> records;
    private final Context context;

    public SecondCommentAdapter(List<JSONObject> records, Context context) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(context, R.layout.item_second_comment, null);
        }

        JSONObject record = records.get(i);

        TextView tvUsername = view.findViewById(R.id.tv_username);
        TextView tvTime = view.findViewById(R.id.tv_time);
        TextView tvContent = view.findViewById(R.id.tv_content);

        try {
            tvUsername.setText(record.getString("userName"));
            tvTime.setText(record.getString("createTime"));
            tvContent.setText(record.getString("content"));

            //tvContentClick(tvContent, tvUsername, view);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return view;
    }

}
