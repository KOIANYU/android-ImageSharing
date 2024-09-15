package com.imagesharing.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.imagesharing.R;
import com.imagesharing.adapter.ImageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShareFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageAdapter adapter;

    private EditText etTitle;
    private EditText etContent;

    private List<Uri> imageUris;
    private List<File> imageFiles;

    private Long imageCode;
    private final Long userId;
    private int shareListCode;

    private Context context;

    public ShareFragment(Long userId) {
        this.userId = userId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_share, container, false);

        context = requireContext();

        etTitle = view.findViewById(R.id.et_title);
        etContent = view.findViewById(R.id.et_content);

        GridView glImages = view.findViewById(R.id.gv_image);
        ImageView ivImage = view.findViewById(R.id.iv_add);

        imageUris = new ArrayList<>();
        imageFiles = new ArrayList<>();

        ivImage.setOnClickListener(v -> selectImagesFromGallery());

        // 初始化适配器
        adapter = new ImageAdapter(view.getContext(), imageUris);
        glImages.setAdapter(adapter);

        // 保存按钮点击事件
        Button btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(v -> saveShare());

        // 发布按钮点击事件
        Button btnSend = view.findViewById(R.id.btn_send);
        btnSend.setOnClickListener(v -> {
            if (shareListCode == 200) {
                getMyShareList();
            } else {
                Toast.makeText(getContext(), "请先保存分享内容", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // 从相册选择图片
    private void selectImagesFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // 允许选择多张图片
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            List<Uri> imageUrisList = new ArrayList<>();

            // 检查是否有多个图片被选择
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    imageUrisList.add(imageUri);

                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);

                        File file = createFile(Objects.requireNonNull(inputStream));

                        imageFiles.add(file);

                        Log.d("imageFiles", imageFiles.toString());

                        inputStream.close();
                    } catch (IOException e) {
                        Log.e("ShareFragment", "Error reading image: " + e.getMessage());
                    }

                }
            } else if (data.getData() != null){
                // 只有一个图片被选择
                Uri singleImageUri = data.getData();
                imageUrisList.add(singleImageUri);

                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(singleImageUri);

                    File file = createFile(Objects.requireNonNull(inputStream));

                    imageFiles.add(file);
                    Log.d("imageFiles", imageFiles.toString());

                    inputStream.close();
                } catch (IOException e) {
                    Log.e("ShareFragment", "Error reading image: " + e.getMessage());
                }
            }

            // 将新选择的图片追加到现有图片列表中
            imageUris.addAll(imageUrisList);
            adapter.notifyDataSetChanged();

            sendImage();
        }
    }


    // 处理上传文件
    private void sendImage() {
        // 检查是否选择了图片
        if (imageUris.isEmpty()) {
            Toast.makeText(getContext(), "请选择至少一张图片", Toast.LENGTH_SHORT).show();
        } else {
            // 异步处理图片上传
            new Thread(() -> {
                // 创建 OkHttpClient 实例并设置超时时间
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS) // 连接超时时间
                        .readTimeout(60, TimeUnit.SECONDS)    // 读取超时时间
                        .writeTimeout(60, TimeUnit.SECONDS)   // 写入超时时间
                        .build();

                Headers headers = new Headers.Builder()
                        .add("appId", "0c43d325bd4c4077a2ef71afd51a2ac9")
                        .add("appSecret", "1796311d4cb76a76e4c309e0ca1c6ff5a13c0")
                        .add("Accept", "application/json, text/plain, */*")
                        .build();

                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

                for (File file : imageFiles) {
                    builder.addFormDataPart("fileList",
                            file.getName(),
                            RequestBody.create(MediaType.parse("image/*"), file)
                    );
                }

                // 构建完整的请求体
                RequestBody requestBody = builder.build();
                Request request = new Request.Builder()
                        .url("https://api-store.openguet.cn/api/member/photo/image/upload")
                        .headers(headers)
                        .post(requestBody)
                        .build();

                // 发送请求
                client.newCall(request).enqueue(callback);
            }).start();
        }
    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            // 处理失败情况
            Log.e("Upload", "Failed to upload images", e);
            // 确保在主线程中显示 Toast
            getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "上传失败", Toast.LENGTH_SHORT).show());
        }

        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            // 处理服务器返回的数据
            String responseBody = Objects.requireNonNull(response.body()).string();

            Log.d("Upload", "Server responded with: " + responseBody);

            try { // 解析服务器返回的JSON数据
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONObject data = jsonResponse.getJSONObject("data");
                int code = jsonResponse.getInt("code");

                if (code == 200) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "图片添加成功", Toast.LENGTH_SHORT).show());

                    imageCode = data.getLong("imageCode");

                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };


    /**
     * 转换从相册获取的图片的格式
     * @param inputStream 输入字节流
     * @return 图片文件 file
     * @throws IOException 处理异常
     */
    private File createFile(InputStream inputStream) throws IOException {
        File file = File.createTempFile("temp_image", ".jpg", getActivity().getCacheDir());

        OutputStream outputStream = Files.newOutputStream(file.toPath());

        byte[] buffer = new byte[1024];

        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.close();

        return file;
    }

    // 处理保存分享
    private void saveShare() {
        new Thread(() -> {
            String url = "http://10.70.142.223:8080/share/save";

            RequestQueue queue = Volley.newRequestQueue(context);

            // 构建请求参数
            JSONObject params = new JSONObject();
            try {
                params.put("content", etContent.getText().toString());
                params.put("imageCode", imageCode);
                params.put("pUserId", userId);
                params.put("title", etTitle.getText().toString());

            } catch (Exception e) {
                Log.d("ShareFragment", e.toString());
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                    url,
                    params,
                    this::parseSaveShareResponse,
                    error -> Log.d("ShareFragment", error.toString())
            );

            queue.add(jsonObjectRequest);

        }).start();
    }

    /**
     * 解析保存分享的JSON响应
     * @param response 相应体信息
     */
    private void parseSaveShareResponse(JSONObject response) {

        try {
            String msg = response.getString("msg");
            shareListCode = response.getInt("code");
            if (shareListCode == 200) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                    Log.d("ShareFragment", response.toString());
                });
            } else {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                Log.d("ShareFragment", response.toString());
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    // 处理获得保存分享列表
    private void getMyShareList() {
        new Thread(() -> {
            String url = "http://10.70.142.223:8080/share/save" + "?userId=" + userId;

            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET,
                    url,
                    null,
                    this::parseMyShareListResponse,
                    error -> Log.d("ShareFragment", error.toString())
            );

            queue.add(jsonObjectRequest);

        }).start();

    }

    /**
     * 解析获得保存分享列表的JSON响应
     * @param response 响应体信息
     */
    private void parseMyShareListResponse(JSONObject response) {
        try {
            int code = response.getInt("code");
            JSONObject data = response.getJSONObject("data");

            Log.d("getMyShareList", response.toString());

            JSONArray recordsArray = data.getJSONArray("records");

            if (code == 200) {
                for (int i = 0; i < recordsArray.length(); i++) {

                    JSONObject record = recordsArray.getJSONObject(i);

                    Long singleImageCode = record.getLong("imageCode");

                    if (singleImageCode.equals(imageCode)) {

                        Log.d("record", record.toString());

                        sendShare(record);
                    }
                }
            } else {
                Toast.makeText(context, "还未选中任何图片", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理发送分享
     * @param record 待分享的图文信息
     */
    private void sendShare(JSONObject record) {
        new Thread(() -> {
            String url = "http://10.70.142.223:8080/share/add";

            RequestQueue queue = Volley.newRequestQueue(context);

            // 构建请求参数
            JSONObject params = new JSONObject();
            try {
                params.put("content", record.getString("content"));
                params.put("id", record.getString("id"));
                params.put("imageCode", imageCode);
                params.put("pUserId", userId);
                params.put("title", record.getString("title"));

            } catch (Exception e) {
                Log.e("sendShare", Objects.requireNonNull(e.getMessage()));
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST,
                    url,
                    params,
                    response -> Log.d("ShareFragment", response.toString()),
                    error -> Log.d("ShareFragment", error.toString())
            );

            queue.add(jsonObjectRequest);

        }).start();
    }

}