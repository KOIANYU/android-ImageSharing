package com.imagesharing.fragment;

import android.content.Context;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.imagesharing.R;
import com.imagesharing.adapter.ListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private Long userId;
    private String username;

    private RequestQueue queue;
    private ListAdapter adapter;

    private GridView shareList;
    private TextView myUsername;

    public HomeFragment(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化RequestQueue
        Context context = requireContext(); // 使用requireContext()确保上下文有效
        queue = Volley.newRequestQueue(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        shareList = view.findViewById(R.id.share_list);
        initShareList();

        myUsername = view.findViewById(R.id.my_username);
        myUsername.setText(username);

        return view;
    }

    public void initShareList() {
        new Thread(() -> {
            String url = "http://10.70.142.223:8080/share" + "?userId="  + userId;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

                parseJsonResponse(response);

            }, error -> Log.d("LoginActivity", error.toString()));

            queue.add(jsonObjectRequest);

        }).start();
    }

    private void parseJsonResponse(JSONObject response) {
        try {
            String msg = response.getString("msg");
            JSONObject data = response.getJSONObject("data");

            JSONArray recordsArray = data.getJSONArray("records");
            // 遍历记录并添加到列表
            List<JSONObject> records = new ArrayList<>();

            for (int i = 0; i < recordsArray.length(); i++) {
                JSONObject record = recordsArray.getJSONObject(i);
                records.add(record);
            }

            // 更新ListView
            adapter = new ListAdapter(records, requireContext(), userId);
            shareList.setAdapter(adapter);

            Log.d("LoginActivity", "分享列表请求" + msg);

        } catch (Exception e) {
            Log.e("LoginActivity", "Error parsing JSON response: " + e.getMessage());
        }
    }

 }