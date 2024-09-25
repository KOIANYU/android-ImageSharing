package com.imagesharing.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imagesharing.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ChildCommentAdapter extends BaseAdapter {
    private final List<JSONObject> records;
    private final Context context;

    public ChildCommentAdapter(List<JSONObject> records, Context context) {
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
            view = View.inflate(context, R.layout.child_comment, null);
        }

        JSONObject record = records.get(i);

        TextView tvUsername = view.findViewById(R.id.comment_username);
        TextView tvContent = view.findViewById(R.id.comment_content);

        try {
            tvUsername.setText(record.getString("userName"));
            tvContent.setText(record.getString("content"));

            //tvContentClick(tvContent, tvUsername, view);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return view;
    }
}
