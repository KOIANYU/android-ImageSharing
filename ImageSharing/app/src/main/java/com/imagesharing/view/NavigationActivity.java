package com.imagesharing.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.imagesharing.R;
import com.imagesharing.fragment.HomeFragment;
import com.imagesharing.fragment.MeFragment;
import com.imagesharing.fragment.ShareFragment;

public class NavigationActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private ShareFragment shareFragment;
    private MeFragment meFragment;
    private BottomNavigationView bottomNavigationView;

    private Long userId;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_navigation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        userId = intent.getLongExtra("userId", -1);
        username = intent.getStringExtra("username");

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 监听底部导航栏的点击事件
        bottomClick();

        // 默认选中第一个页面
        selectedFragment(0);

    }

    // 监听底部导航栏的点击事件
    private void bottomClick() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                selectedFragment(0);
            } else if (item.getItemId() == R.id.nav_share) {
                selectedFragment(1);
            } else {
                selectedFragment(2);
            }
            return true;
        });
    }

    /**
     * 根据position显示相应的Fragment
     * @param position 0表示首页，1表示分享，2表示我的
     */
    private void selectedFragment(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        // 根据position显示相应的Fragment
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment(userId);
                    fragmentTransaction.add(R.id.fragment_container, homeFragment);
                } else {
                    fragmentTransaction.show(homeFragment);
                }
                break;
            case 1:
                if (shareFragment == null) {
                    shareFragment = new ShareFragment(userId);
                    fragmentTransaction.add(R.id.fragment_container, shareFragment);
                } else {
                    fragmentTransaction.show(shareFragment);
                }
                break;
            case 2:
                if (meFragment == null) {
                    meFragment = new MeFragment(userId, username);
                    fragmentTransaction.add(R.id.fragment_container, meFragment);
                } else {
                    fragmentTransaction.show(meFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    // 先隐藏所有的Fragment
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) fragmentTransaction.hide(homeFragment);
        if (shareFragment != null) fragmentTransaction.hide(shareFragment);
        if (meFragment != null) fragmentTransaction.hide(meFragment);
    }

}