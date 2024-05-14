package com.example.travelor;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

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
    private TextView currentState;
    private ViewPager viewPager;
    private View locationIcon;
    private List<Integer> imageList;
    private ArrayList<View> dots = new ArrayList<>();
    private VideoView videoView;
    private Button btnToggle;
    private int currentPosition = 1;
    private boolean isPlaying = false;
    private TextView backButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoring_in);
        initCanteenItem();
        initCanteenNameView();
        initSwichImage();
        initBackButton();
        initFlowState();
        initCurrentState();
        initAnnouncement();
        initLocationIcon();
        initVideoPlayer();

    }


    private void initCurrentState() {
        currentState = findViewById(R.id.current_state);
        currentState.setText(canteenItem.getFlowState());
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

    //视频播放
    private void initVideoPlayer(){
        videoView = findViewById(R.id.videoView);
        btnToggle = findViewById(R.id.btnToggle);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.in1;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.seekTo(currentPosition);

        btnToggle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!isPlaying) {
                    videoView.seekTo(currentPosition); // 设置播放位置
                    videoView.start();
                    isPlaying = true;
                    btnToggle.setSelected(true);
                    btnToggle.setVisibility(View.INVISIBLE);
                    setBtnVisible();
                }
                else { // 如果正在播放，则停止播放
                    videoView.pause();
                    currentPosition = videoView.getCurrentPosition(); // 记录当前播放位置;
                    isPlaying = false;
                    btnToggle.setSelected(false);
                }
            }
        });
    }


    // 开始停止播放逻辑
    private void setBtnVisible() {
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnToggle.getVisibility() == View.INVISIBLE)
                    btnToggle.setVisibility(View.VISIBLE);
                else btnToggle.setVisibility(View.INVISIBLE);
            }
        });
    };
}
