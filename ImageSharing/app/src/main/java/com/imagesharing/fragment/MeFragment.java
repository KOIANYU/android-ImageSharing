package com.imagesharing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.imagesharing.view.CollectionActivity;
import com.imagesharing.view.DraftsActivity;
import com.imagesharing.view.FocusActivity;
import com.imagesharing.view.LikesActivity;
import com.imagesharing.view.MyDynamicsActivity;
import com.imagesharing.R;
import com.imagesharing.view.SettingsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MeFragment extends Fragment {

    private LinearLayout releaseLayout;
    private LinearLayout aboutLayout;
    private LinearLayout downloadsLayout;
    private LinearLayout collectionsLayout;
    private LinearLayout historyLayout;
    private LinearLayout settingsLayout;

    private final Long userId;
    private final String username;

    public MeFragment(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_me, container, false);

        // 初始化组件
        releaseLayout = view.findViewById(R.id.release);
        aboutLayout = view.findViewById(R.id.about_layout);
        downloadsLayout = view.findViewById(R.id.downloads_layout);
        collectionsLayout = view.findViewById(R.id.collections_layout);
        historyLayout = view.findViewById(R.id.history_layout);
        settingsLayout = view.findViewById(R.id.settings_layout);

        TextView myUsername = view.findViewById(R.id.my_username);
        myUsername.setText(username);

        // 设置点击事件
        setupReleaseClicks();
        setupAboutClicks();
        setupDownloadsClicks();
        setupCollectionsClicks();
        setupHistoryClicks();
        setupSettingsClicks();

        get();
        fetchFollowedActivities();

        return view;
    }

    // 获取用户动态分享列表
    private void get() {
        new Thread(() -> {
            RequestQueue queue = Volley.newRequestQueue(requireContext());

            String url = "http://10.70.142.223:8080/share/myself?userId=" + userId;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, Response -> {
                int length = parseResponse(Response);

                TextView releaseTextView = getView().findViewById(R.id.release_num);

                releaseTextView.setText(String.valueOf(length));

            }, error -> Log.d("LoginActivity", "Error: " + error.getMessage()));

            queue.add(jsonObjectRequest);

        }).start();
    }

    private void fetchFollowedActivities() {
        new Thread(() -> {
            RequestQueue queue = Volley.newRequestQueue(requireContext());

            String url = "http://10.70.142.223:8080/focus?userId=" + userId;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, Response -> {
                int length = parseResponse(Response);

                TextView focusTextView = getView().findViewById(R.id.focus_num);

                focusTextView.setText(String.valueOf(length));
            }, error -> Log.d("LoginActivity", "Error: " + error.getMessage()));

            queue.add(jsonObjectRequest);

        }).start();
    }

    /**
     * 解析JSON响应
     * @param response 响应体信息
     */
    private int parseResponse(JSONObject response) {
        try {
            if (response.has("data") && !response.isNull("data")) {
                JSONObject data = response.getJSONObject("data");

                Log.d("MeFragment", response.toString());

                int code = response.getInt("code");

                if (code == 200) {
                    JSONArray recordsArray = data.getJSONArray("records");

                    return recordsArray.length();
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    // 处理点击跳转到我的动态页面
    private void setupReleaseClicks() {
        releaseLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MyDynamicsActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });
    }

    // 处理点击跳转到我的关注页面
    private void setupAboutClicks() {
        aboutLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FocusActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });
    }

    private void setupDownloadsClicks() {
        downloadsLayout.setOnClickListener(v -> {
            // 跳转到草稿页面
            startActivity(new Intent(getContext(), DraftsActivity.class));
        });
    }

    private void setupCollectionsClicks() {
        collectionsLayout.setOnClickListener(v -> {
            // 跳转到收藏页面
            startActivity(new Intent(getContext(), CollectionActivity.class));
        });
    }

    private void setupHistoryClicks() {
        historyLayout.setOnClickListener(v -> {
            // 跳转到点赞页面
            startActivity(new Intent(getContext(), LikesActivity.class));
        });
    }

    private void setupSettingsClicks() {
        settingsLayout.setOnClickListener(v -> {
            // 跳转到设置页面
            startActivity(new Intent(getContext(), SettingsActivity.class));
        });
    }

}
