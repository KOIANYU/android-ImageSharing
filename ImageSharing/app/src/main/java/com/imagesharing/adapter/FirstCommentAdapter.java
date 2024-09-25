package com.imagesharing.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.imagesharing.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstCommentAdapter extends BaseAdapter {

    private final List<JSONObject> records;
    private final Context context;

    public FirstCommentAdapter(List<JSONObject> records, Context context) {
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_first_comment, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        JSONObject record = records.get(position);

        try {
            viewHolder.tvUsername.setText(record.getString("userName"));
            viewHolder.tvTime.setText(record.getString("createTime"));
            viewHolder.tvContent.setText(record.getString("content"));

            Long commentId = record.getLong("id");
            Long shareId = record.getLong("shareId");
            Log.d("FirstCommentAdapter", "一级评论请求" + commentId + " " + shareId);

            if (!viewHolder.isInitialized) {
                initSecondCommentList(viewHolder, commentId, shareId);
                viewHolder.isInitialized = true;
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return convertView;
    }

    private void initSecondCommentList(ViewHolder viewHolder, Long commentId, Long shareId) {
        new Thread(() -> {
            String url = "http://10.33.11.214:8080/comment/second?commentId=" + commentId + "&shareId=" + shareId;

            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> parseSecondCommentResponse(viewHolder, response),
                    error -> Log.d("FirstCommentAdapter", error.toString())
            );

            queue.add(request);
        }).start();
    }

    private void parseSecondCommentResponse(ViewHolder viewHolder, JSONObject response) {
        try {
            ListView secondCommentList = viewHolder.secondCommentList;
            RelativeLayout rlSecondComment = viewHolder.rlSecondComment;
            TextView replyNum = viewHolder.replyNum;

            String msg = response.getString("msg");

            if (response.has("data") && !response.isNull("data")) {
                JSONObject data = response.getJSONObject("data");

                JSONArray recordsArray = data.getJSONArray("records");

                replyNum.setText(String.valueOf(recordsArray.length()));

                List<JSONObject> records = new ArrayList<>();

                for (int i = 0; i < recordsArray.length(); i++) {
                    JSONObject record = recordsArray.getJSONObject(i);
                    records.add(record);
                }

                android.view.ViewGroup.LayoutParams layoutParams = rlSecondComment.getLayoutParams();
                layoutParams.height = 380;
                rlSecondComment.setLayoutParams(layoutParams);

                ChildCommentAdapter childCommentAdapter = new ChildCommentAdapter(records, context);
                secondCommentList.setAdapter(childCommentAdapter);

            }
            Log.d("FirstCommentAdapter", "二级评论列表请求" + msg);

        } catch (JSONException e) {
            Log.e("FirstCommentAdapter", "Error response: " + e.getMessage());
        }
    }

    public static class ViewHolder {
        TextView tvUsername;
        TextView tvTime;
        TextView tvContent;
        ListView secondCommentList;
        RelativeLayout rlSecondComment;
        TextView replyNum;
        boolean isInitialized = false;

        ViewHolder(View itemView) {
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvContent = itemView.findViewById(R.id.tv_content);
            secondCommentList = itemView.findViewById(R.id.second_comment_list);
            rlSecondComment = itemView.findViewById(R.id.rl_second_comment);
            replyNum = itemView.findViewById(R.id.reply_num);
        }
    }
}