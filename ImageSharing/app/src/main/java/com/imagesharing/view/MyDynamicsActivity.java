package com.imagesharing.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.imagesharing.R;
import com.imagesharing.adapter.MyDynamicsAdapter;
import com.imagesharing.bean.Record;
import com.imagesharing.response.ApiResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyDynamicsActivity extends AppCompatActivity {

    private ListView myDynamicsListView;
    private MyDynamicsAdapter myDynamicsAdapter;
    private static final String TAG = "MyDynamicsActivity";
    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dynamics);

        userId = getIntent().getLongExtra("userId", -1);

        // 初始化ListView
        myDynamicsListView = findViewById(R.id.myDynamicsListView);

        // 设置 item 之间的间距
        myDynamicsListView.setDivider(getResources().getDrawable(R.drawable.divider));
        myDynamicsListView.setDividerHeight(25); // 设置间距高度

        get();
    }

    private void get() {
        new Thread(() -> {
            RequestQueue queue = Volley.newRequestQueue(this);

            String url = "http://10.70.142.223:8080/share/myself?userId=" + userId;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, Response -> {
                parseResponse(Response);
            }, error -> Log.d("LoginActivity", "Error: " + error.getMessage()));

            queue.add(jsonObjectRequest);
        }).start();
    }

    private void parseResponse(JSONObject response) {
        try {
            if (response.has("data") && !response.isNull("data")) {
                JSONObject data = response.getJSONObject("data");

                JSONArray recordsArray = data.getJSONArray("records");

                List<JSONObject> records = new ArrayList<>();

                for (int i = 0; i < recordsArray.length(); i++) {
                    JSONObject record = recordsArray.getJSONObject(i);
                    records.add(record);
                }

                updateUI(records);

            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // 更新UI的方法
    private void updateUI(List<JSONObject> Records) {
        myDynamicsAdapter = new MyDynamicsAdapter(this, Records);
        myDynamicsListView.setAdapter(myDynamicsAdapter);
    }

}