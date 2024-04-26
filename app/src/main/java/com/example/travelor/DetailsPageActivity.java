package com.example.travelor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelor.adapter.DishCardAdapter;
import com.example.travelor.adapter.ImagePagerAdapter;

import com.example.travelor.bean.Dish;

import com.example.travelor.datebase.DishCltDbOpenHelper;
import com.example.travelor.datebase.DishDbOpenHelper;

import com.example.travelor.util.SpfUtil;
import com.example.travelor.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class DetailsPageActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<Integer> imageList = new ArrayList<>();
    private ArrayList<View> dots = new ArrayList<>();
    private Dish mDish;
    private TextView tvName;
    private TextView tvPrice1;
    private TextView tvWindow;
    private TextView tvLocation;
    private List<ImageView> imageViews = new ArrayList<>();
    private TextView tvPrice;
    private TextView tvIntroduce;
    private TextView back;


    private Context mContext;
    private View likeItIcon;

    public static final String KEY_COLLECTED = "collected";

    private RecyclerView sameRecycler;
    private List<Dish> mSame;
    private DishCardAdapter mDishCardAdapter;
    private DishDbOpenHelper mDishDbOpenHelper;

    private Button btCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_page_layout);
        mContext = this;

        tvName = findViewById(R.id.name);
        tvWindow = findViewById(R.id.rank);
        tvPrice = findViewById(R.id.price);
        tvPrice1 = findViewById(R.id.price1);
        tvIntroduce = findViewById(R.id.introduce);
        tvLocation = findViewById(R.id.location);
        btCollect = findViewById(R.id.buy_now);

        imageViews.add((ImageView)findViewById(R.id.photo1));
        imageViews.add((ImageView)findViewById(R.id.photo2));
        imageViews.add((ImageView)findViewById(R.id.photo3));

        sameRecycler = findViewById(R.id.hotel_rl);
        mSame = new ArrayList<>();
        mDishDbOpenHelper = new DishDbOpenHelper(this);

        initData();
        initEvent();
    }

    private void initEvent() {
        imageSwitch();
        setLikeIt();
        backEvent();
        collect();
    }

    private void collect() {
        btCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DishCltDbOpenHelper dishCltDbOpenHelper = new DishCltDbOpenHelper(mContext);
                long row = dishCltDbOpenHelper.insertData(mDish);
                if (row != -1) {
                    ToastUtil.toastShort(mContext, "收藏成功！");
                } else {
                    ToastUtil.toastShort(mContext, "已收藏！");
                }
            }
        });
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



    // 设置收藏点亮
    private void setLikeIt() {
        likeItIcon = findViewById(R.id.likeIt_icon);

        likeItIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isCollected = SpfUtil.getBoolean(DetailsPageActivity.this, KEY_COLLECTED);
                if(isCollected) {
                    likeItIcon.setSelected(false);
                    SpfUtil.saveBoolean(DetailsPageActivity.this, KEY_COLLECTED, false);
                }
                else {
                    likeItIcon.setSelected(true);
                    SpfUtil.saveBoolean(DetailsPageActivity.this, KEY_COLLECTED, true);
                }
            }
        });
    }

    // 图设置
    private void setImages(String imagesString) {
        String[] imageNames = imagesString.split("#");

        for (int i = 0; i < imageNames.length; i++) {
            int id = getResources().getIdentifier(imageNames[i], "drawable", getPackageName());
            imageViews.get(i).setImageResource(id);
            imageList.add(id);

        }
    }

    // 接收数据并初始化
    private void initData() {
        Intent intent = getIntent();
        mDish = (Dish) intent.getSerializableExtra("dish");
        if (mDish != null) {
            tvName.setText(mDish.getName());
            tvWindow.setText(mDish.getWindow());
            tvIntroduce.setText(mDish.getIntroduce());
            tvLocation.setText(mDish.getLocation());
            tvPrice.setText(mDish.getPrice());
            tvPrice1.setText(mDish.getPrice());
            setImages(mDish.getImages());
        }

          initSame(mDish.getCategory());
    }

    // 处理推荐视图
    private void initSame(String category) {
        mSame = mDishDbOpenHelper.queryFromDbByCategory(category); // todo 动态推荐名称
        mDishCardAdapter = new DishCardAdapter(this, mSame);
        sameRecycler.setAdapter(mDishCardAdapter);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        sameRecycler.setLayoutManager(linearLayoutManager); // 创建布局管理器
        mDishCardAdapter.notifyDataSetChanged();
    }





    private void imageSwitch() {
        viewPager = findViewById(R.id.viewPager);
        final int[] position = {0};  // 当前显示的图片索引

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

                handler.postDelayed(this, 1000);  // 设置延时时间（单位：毫秒）
            }
        };

        // 启动自动切换
        handler.postDelayed(runnable, 1000);  // 设置初始延时时间（单位：毫秒）
    }


}