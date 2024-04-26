package com.example.travelor;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelor.adapter.ImagePagerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import java.util.ArrayList;
import java.util.List;


public class MonitoringActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<Integer> imageList;
    private ArrayList<View> dots = new ArrayList<>();

    private TextView back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoring_in); // 加载布局文件



        imageSwitch();
        backEvent();
    }


    private void backEvent() {
        back = findViewById(R.id.turn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void imageSwitch() {
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
