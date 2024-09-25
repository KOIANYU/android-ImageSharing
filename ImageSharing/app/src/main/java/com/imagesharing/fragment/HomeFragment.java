package com.imagesharing.fragment;

import android.content.Context;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

    private RequestQueue queue;
    private ListAdapter adapter;

    private GridView shareList;
    private SwipeRefreshLayout swipeRefreshLayout;

    public HomeFragment(Long userId) {
            this.userId = userId;
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
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        // 初始化首页列表数据
        initShareList();

        // 处理下拉刷新页面
        reLoadShareList();

        return view;
    }

    // 下拉刷新页面数据
    private void reLoadShareList() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // 开始刷新时显示提示
            swipeRefreshLayout.setRefreshing(true);
            new Thread(() -> {
                // 模拟网络请求时间
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 在主线程更新UI
                getActivity().runOnUiThread(() -> {
                    // 重新加载数据
                    initShareList();
                    // 刷新结束，关闭刷新动画
                    swipeRefreshLayout.setRefreshing(false);
                });
            }).start();
        });
    }

    // 初始化首页列表数据
    public void initShareList() {
        new Thread(() -> {
            String url = "http://10.70.142.223:8080/share" + "?userId="  + userId;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    this::parseJsonResponse,
                    error -> Log.d("HomeFragment", error.toString()));

            queue.add(jsonObjectRequest);

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

                // 更新ListView
                adapter = new ListAdapter(records, requireContext(), userId);
                shareList.setAdapter(adapter);
            }
            Log.d("HomeFragment", "分享列表请求" + msg);

        } catch (Exception e) {
            Log.e("HomeFragment", "Error parsing JSON response: " + e.getMessage());
        }
    }

    public void setQueue(RequestQueue queue) {
        this.queue = queue;
    }

 }