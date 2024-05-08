package com.example.travelor;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.travelor.adapter.ImagePagerAdapter;
import com.example.travelor.bean.CanteenItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import java.util.ArrayList;
import java.util.List;


public class MonitoringActivity extends AppCompatActivity {

    private CanteenItem canteenItem;
    private TextView canteenName;
    private TextView flowState;
    private TextView announcement;
    private ViewPager viewPager;
    private View locationIcon;
    private List<Integer> imageList;
    private ArrayList<View> dots = new ArrayList<>();

    private TextView backButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoring_in);
        initCanteenItem();
        initCanteenNameView();
        initSwichImage();
        initBackButton();
        initFlowState();
        initAnnouncement();
        initLocationIcon();
    }

    private void initLocationIcon() {
        locationIcon = findViewById(R.id.location_icon);
        addListener2LocationIcon();
    }

    private void addListener2LocationIcon() {
        locationIcon.setOnClickListener(locationIcon -> {
            Intent intent = new Intent(this, GaodeMapActivity.class);
            intent.putExtra("canteenItem", canteenItem);
            this.startActivity(intent);
        });
    }

    private void initAnnouncement() {
        announcement = findViewById(R.id.announcement);
        announcement.setText(canteenItem.getAnnouncement());
    }

    private void initFlowState() {
        flowState = findViewById(R.id.flow_state);
        flowState.setText("流量状况: " + canteenItem.getFlowState());
    }

    private void initCanteenItem() {
        canteenItem = (CanteenItem) getIntent().getSerializableExtra("canteenItem");
    }

    private void initCanteenNameView() {
        canteenName = findViewById(R.id.canteen_name);
        canteenName.setText(canteenItem.getCanteenName());
    }


    private void initBackButton() {
        backButton = findViewById(R.id.turn_back);
        backButton.setOnClickListener(view -> finish());
    }

    private void initSwichImage() {
        viewPager = findViewById(R.id.viewPager);
        imageList = new ArrayList<>();  // 存储图片资源的列表
        final int[] position = {0};  // 当前显示的图片索引

        // 添加图片资源到列表
        imageList.add(R.drawable.he11);
        imageList.add(R.drawable.he12);
        imageList.add(R.drawable.he13);

        dots.add(findViewById(R.id.p1));
        dots.add(findViewById(R.id.p2));
        dots.add(findViewById(R.id.p3));

        // 创建适配器并设置给ViewPager
        ImagePagerAdapter adapter = new ImagePagerAdapter(imageList);
        viewPager.setAdapter(adapter);

        // 设置自动切换
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int oldPosition = position[0];
                position[0] = (position[0] + 1) % imageList.size();

                viewPager.setCurrentItem(position[0], true);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                dots.get(position[0]).setBackgroundResource(R.drawable.dot_focus);

                handler.postDelayed(this, 1500);  // 设置延时时间（单位：毫秒）
            }
        };

        // 启动自动切换
        handler.postDelayed(runnable, 1500);  // 设置初始延时时间（单位：毫秒）
    }
}
