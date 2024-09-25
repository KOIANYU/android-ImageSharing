package com.imagesharing.view;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.imagesharing.R;
import com.imagesharing.adapter.SecondCommentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecondCommentActivity extends AppCompatActivity {

    private ListView secondCommentList;
    private EditText etContent;
    private Button btnComment;

    // 一级评论id
    private Long parentCommentId = 6513L;
    // 一级评论的用户id
    private Long parentCommentUserId = 1826656600132292608L;
    // 被回复的评论id
    private Long replyCommentId = 6513L;
    // 被回复的评论的用户id
    private Long replyCommentUserId = 1826656600132292608L;
    // 图文分享的主键id
    private Long shareId = 8009L;
    // 评论人userId
    private Long userId = 1826656600132292608L;
    // 评论人userId
    private String userName = "heguizhang";

    private Long commentId = 6513L;

    private int shareListCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_second_comment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        secondCommentList = findViewById(R.id.second_comment_list);
        etContent = findViewById(R.id.et_content);
        btnComment = findViewById(R.id.btn_comment);

        initSecondCommentList();
        btnCommentClick();

    }

    // 回复按钮点击事件
    private void btnCommentClick() {
        btnComment.setOnClickListener(v -> {
            if (etContent.getText().toString().isEmpty()) {
                Toast.makeText(this, "你还没有填写任何内容", Toast.LENGTH_SHORT).show();
            } else {
                addSecondComment();
                etContent.setText("");
                initSecondCommentList();
            }
        });
    }

    /**
     * 获取二级评论列表
     */
    private void initSecondCommentList() {
        new Thread(() -> {
            String url = "http://10.70.142.223:8080/comment/second?commentId=" + commentId + "&shareId=" + shareId;

            RequestQueue queue = Volley.newRequestQueue(this);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    this::parseJsonResponse,
                    error -> Log.e("SecondCommentActivity", error.toString()));

            queue.add(request);
        }).start();
    }

    /**
     * 解析JSON响应
     * @param response 响应体信息
     */
    private void parseJsonResponse(JSONObject response) {
        try {
            String msg = response.getString("msg");

            if (response.has("data") && !response.isNull("data")) {
                JSONObject data = response.getJSONObject("data");

                JSONArray recordsArray = data.getJSONArray("records");
                // 遍历记录并添加到列表
                List<JSONObject> records = new ArrayList<>();

                for (int i = 0; i < recordsArray.length(); i++) {
                    JSONObject record = recordsArray.getJSONObject(i);
                    records.add(record);
                }

                // 更新firstCommentList
                SecondCommentAdapter secondCommentAdapter = new SecondCommentAdapter(records, this);
                secondCommentList.setAdapter(secondCommentAdapter);

            }
            Log.d("SecondCommentActivity", "二级评论列表请求" + msg);

        } catch (JSONException e) {
            Log.e("SecondCommentActivity", "Error response: " + e.getMessage());
        }
    }

    /**
     * 添加二级评论
     */
    private void addSecondComment() {
        new Thread(() -> {
            String url = "http:///10.70.142.223:8080/comment/second";

            RequestQueue queue = Volley.newRequestQueue(this);

            JSONObject params = new JSONObject();
            try {
                params.put("content", etContent.getText().toString());
                params.put("parentCommentId", parentCommentId);
                params.put("parentCommentUserId", parentCommentUserId);
                params.put("replyCommentId", replyCommentId);
                params.put("replyCommentUserId", replyCommentUserId);
                params.put("shareId", shareId);
                params.put("userId", userId);
                params.put("userName", userName);

            } catch (Exception e) {
                Log.d("SecondCommentActivity", e.toString());
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                    response -> {
                        Log.d("SecondCommentActivity", response.toString());
                        try {
                            shareListCode = response.getInt("code");
                            Log.d("shareListCode", "添加二级评论或回复请求 " + shareListCode);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }, error -> Log.d("SecondCommentActivity", error.toString())
            );

            queue.add(jsonObjectRequest);

        }).start();
    }


}