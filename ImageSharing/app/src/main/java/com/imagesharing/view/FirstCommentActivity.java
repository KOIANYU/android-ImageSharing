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
import com.imagesharing.adapter.FirstCommentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstCommentActivity extends AppCompatActivity {

    private ListView firstCommentList;
    private EditText etContent;
    private Button btnComment;
    private TextView contentNum;

    private Long shareId = 8009L;
    private Long userId = 1826656600132292608L;
    private String userName = "heguizhang";
    private int shareListCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_first_comment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firstCommentList = findViewById(R.id.first_comment_list);
        etContent = findViewById(R.id.et_content);
        btnComment = findViewById(R.id.btn_comment);
        contentNum = findViewById(R.id.content_num);

        initFirstCommentList();

        btnCommentClick();

    }

    private void btnCommentClick() {
        btnComment.setOnClickListener(v -> {
            if (etContent.getText().toString().isEmpty()) {
                Toast.makeText(this, "你还没有写任何内容", Toast.LENGTH_SHORT).show();
            } else {
                addFirstComment();
                etContent.setText("");
                initFirstCommentList();
            }
        });
    }

    /**
     * 初始化一级评论列表
     */
    private void initFirstCommentList() {
        new Thread(() -> {
            String url = "http://10.33.11.214:8080/comment/first?shareId=" + shareId;

            RequestQueue queue = Volley.newRequestQueue(this);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    this::parseJsonResponse,
                    error -> Log.e("FirstCommentActivity", error.toString()));

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

                // 显示评论数量
                String contentNumStr = "(" + String.valueOf(recordsArray.length()) + ")";
                contentNum.setText(contentNumStr);

                for (int i = 0; i < recordsArray.length(); i++) {
                    JSONObject record = recordsArray.getJSONObject(i);
                    records.add(record);
                }

                // 更新firstCommentList
                FirstCommentAdapter firstCommentAdapter = new FirstCommentAdapter(records, this);
                firstCommentList.setAdapter(firstCommentAdapter);

            }
            Log.d("FirstCommentActivity", "一级评论列表请求" + msg);

        } catch (JSONException e) {
            Log.e("FirstCommentActivity", "Error response: " + e.getMessage());
        }
    }

    /**
     * 添加一级评论
     */
    private void addFirstComment() {
        new Thread(() -> {
            String url = "http://10.33.11.214:8080/comment/first";

            RequestQueue queue = Volley.newRequestQueue(this);

            // 构建请求参数
            JSONObject params = new JSONObject();
            try {
                params.put("content", etContent.getText().toString());
                params.put("shareId", shareId);
                params.put("userId", userId);
                params.put("userName", userName);

            } catch (Exception e) {
                Log.d("FirstCommentActivity", e.toString());
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                    response -> {
                        Log.d("FirstCommentActivity", response.toString());
                        try {
                            shareListCode = response.getInt("code");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }, error -> Log.d("FirstCommentActivity", error.toString())
            );

            queue.add(jsonObjectRequest);


        }).start();
    }

}
