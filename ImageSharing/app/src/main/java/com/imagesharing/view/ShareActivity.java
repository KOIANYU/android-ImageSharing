package com.imagesharing.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.imagesharing.R;
import com.imagesharing.adapter.NewImageAdapter;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class ShareActivity extends AppCompatActivity {

    private String id;
    private String pUserId;
    private String imageCode;
    private String title;
    private String content;

    private GridView gvImage;
    private Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_share);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 获取其他数据
        id = getIntent().getStringExtra("id");
        pUserId = getIntent().getStringExtra("pUserId");
        imageCode = getIntent().getStringExtra("imageCode");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");

        // 获取 imageUrlList
        @SuppressWarnings("unchecked")
        List<String> imageUrlList = (List<String>) getIntent().getSerializableExtra("imageUrlList");

        EditText etTitle = findViewById(R.id.et_title);
        EditText etContent = findViewById(R.id.et_content);
        gvImage = findViewById(R.id.gv_image);
        btnSend = findViewById(R.id.btn_send);

        NewImageAdapter adapter = new NewImageAdapter(this, imageUrlList);
        gvImage.setAdapter(adapter);

        etTitle.setText(title);
        etContent.setText(content);

        sendShare();

    }

    private void sendShare() {
        btnSend.setOnClickListener(v -> {
            String url = "http://10.70.142.223:8080/share/add";

            RequestQueue queue = Volley.newRequestQueue(this);

            // 构建请求参数
            JSONObject params = new JSONObject();
            try {
                params.put("content", content);
                params.put("id", id);
                params.put("imageCode", imageCode);
                params.put("pUserId", pUserId);
                params.put("title", title);

            } catch (Exception e) {
                Log.e("ShareActivity", Objects.requireNonNull(e.getMessage()));
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                    url,
                    params,
                    response -> Log.d("ShareActivity", response.toString()),
                    error -> Log.d("ShareActivity", error.toString())
            );

            queue.add(jsonObjectRequest);
        });
    }


}