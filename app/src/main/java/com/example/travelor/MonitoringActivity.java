package com.example.travelor;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.travelor.adapter.ImagePagerAdapter;
import com.example.travelor.bean.Canteen;
import com.example.travelor.datebase.CanteenDbOpenHelper;
import com.example.travelor.fragment.AnnouncementEditDialogFragment;
import com.example.travelor.service.CanteenService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MonitoringActivity extends AppCompatActivity implements AnnouncementEditDialogFragment.AnnouncementEditDialogListener {

    private static final int UPDATE_DELAY_MILLI = 3000; // 1000 milliseconds == 1 second

    private Canteen canteen;
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
    private CanteenService.GetService canteenGetService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoring_in);
        initServices();
        initCanteenItem();
        initCanteenNameView();
        initSwichImage();
        initBackButton();
        initLocationIcon();
        initVideoPlayer();
        cyclicallyUpdateContentViaRemote();
    }

    private void cyclicallyUpdateContentViaRemote() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                readCanteenFromRemote();
                handler.postDelayed(this, UPDATE_DELAY_MILLI);
            }
        }, UPDATE_DELAY_MILLI);
    }

    private void initServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CanteenService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        canteenGetService = retrofit.create(CanteenService.GetService.class);
    }

    private void initCurrentState() {
        currentState = findViewById(R.id.current_state);
        currentState.setText(canteen.getState());
    }

    private void initLocationIcon() {
        locationIcon = findViewById(R.id.location_icon);
        addListener2LocationIcon();
    }

    private void addListener2LocationIcon() {
        locationIcon.setOnClickListener(locationIcon -> {
            Intent intent = new Intent(this, GaodeMapActivity.class);
            intent.putExtra("canteenItem", canteen);
            this.startActivity(intent);
        });
    }

    private void initAnnouncement() {
        announcement = findViewById(R.id.announcement);
        announcement.setText(canteen.getAnnouncement());
    }

    private void initFlowState() {
        flowState = findViewById(R.id.flow_state);
        flowState.setText("流量状况: " + canteen.getState());
    }

    private void initCanteenItem() {
        canteen = (Canteen) getIntent().getSerializableExtra("canteenItem");
        readCanteenFromRemote();
    }

    private void readCanteenFromRemote() {
        canteenGetService.getCanteen(canteen.getName()).enqueue(new Callback<Canteen>() {
            @Override
            public void onResponse(Call<Canteen> call, Response<Canteen> response) {
                canteen = response.body();
                initFlowState();
                initCurrentState();
                initAnnouncement();
            }

            @Override
            public void onFailure(Call<Canteen> call, Throwable throwable) {
                Log.e("MonitoringActivity", throwable.toString());
            }
        });
    }

    private void initCanteenNameView() {
        canteenName = findViewById(R.id.canteen_name);
        canteenName.setText(canteen.getName());
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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String newAnnouncement) {
        CanteenDbOpenHelper.updateAnnouncement(this, canteen.getName(), newAnnouncement);
        announcement.setText(newAnnouncement);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

}
