package com.imagesharing.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.imagesharing.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername;
    private TextInputEditText etPassword;
    private CheckBox cbRemember;

    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "sp_login";
    private static final String KEY_USERNAME = "login_username";
    private static final String KEY_PASSWORD = "login_password";
    private static final String KEY_REMEMBER = "login_remember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        Button btnLogin = findViewById(R.id.bt_login);

        btnLogin.setOnClickListener(v -> { // 处理登录按钮点击事件
            // 获取用户名和密码
            String username = Objects.requireNonNull(etUsername.getText()).toString();
            String password = Objects.requireNonNull(etPassword.getText()).toString();

            if (username.isEmpty()) { // 判断用户名是否为空
                // lambda表达式的线程
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show());

            } else if (password.isEmpty()) { // 判断密码是否为空

                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show());

            } else { // 正常输入后,进行登录请求,并返回登录信息
                login(password, username);
            }

        });

        cbRemember = findViewById(R.id.cb_remember);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        reload();

        TextView tvRegister = findViewById(R.id.tv_register);

        tvRegister.setOnClickListener(v -> { // 处理注册标签点击事件
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }

    private void reload() {
        boolean isRemember = sharedPreferences.getBoolean(KEY_REMEMBER, false);

        if (isRemember) { // 如果上次点击了记住密码，则加载用户名和密码
            String username = sharedPreferences.getString(KEY_USERNAME, "");
            String password = sharedPreferences.getString(KEY_PASSWORD, "");
            etUsername.setText(username);
            etPassword.setText(password);
            cbRemember.setChecked(true);
        }
    }

    /**
     * 登录请求
     * @param password 密码
     * @param username 用户名
     */
    private void login(String password, String username) {

        runOnUiThread(() -> {
            RequestQueue queue = Volley.newRequestQueue(this);

            String URL = "http://10.70.142.223:8080/user/login"; // ip换成自己的电脑ip，端口默认8080

            StringRequest request = new StringRequest(Request.Method.POST, URL, response -> {// 处理请求成功
                parseJsonResponse(response);

                if (cbRemember.isChecked()) { // 如果勾选了记住密码，则保存用户名和密码
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_USERNAME, username);
                    editor.putString(KEY_PASSWORD, password);
                    editor.putBoolean(KEY_REMEMBER, cbRemember.isChecked());
                    editor.apply();
                }

            }, error -> { // 处理请求失败
                Log.d("LoginActivity", "Error: " + error.getMessage());
            }){
                @NonNull
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> map = new HashMap<>();
                    map.put("password", password);
                    map.put("username", username);
                    return map;
                }
            };
            queue.add(request);
        });
    }

    /**
     * 解析JSON响应
     * @param response JSON响应
     */
    private void parseJsonResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);

            String msg = jsonResponse.getString("msg");

            JSONObject data = jsonResponse.getJSONObject("data");
            Long id = data.getLong("id");
            String username = data.getString("username");

            // 启动 NavigationActivity 并传递 userId
            Intent intent = new Intent(this, NavigationActivity.class);
            intent.putExtra("userId", id);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();

            // 另起一个线程在界面显示信息
            runOnUiThread(() -> {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                Log.d("LoginActivity", response);
            });
        } catch (Exception e) {
            Log.e("LoginActivity", "Error parsing JSON response: " + e.getMessage());
        }
    }


}