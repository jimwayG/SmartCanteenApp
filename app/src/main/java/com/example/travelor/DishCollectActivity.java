package com.example.travelor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.travelor.adapter.DishCltAdapter;
import com.example.travelor.bean.Dish;
import com.example.travelor.datebase.DishCltDbOpenHelper;
import com.example.travelor.util.ToastUtil;

import java.util.List;

public class DishCollectActivity extends AppCompatActivity {
    private TextView vtManage;
    private TextView vtBack;
    private ViewGroup llDelete;
    private TextView chooseAll;
    private Button btDelete;
    private Boolean isChooseAll = false;
    private Boolean managed = false;

    private List<Dish> mDish;
    private DishCltDbOpenHelper mDishCltDbOpenHelper;
    private DishCltAdapter mDishCltAdapter;
    private RecyclerView collectRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_collect_layout);

        vtManage = findViewById(R.id.vt_manage);
        vtBack = findViewById(R.id.back);
        llDelete = findViewById(R.id.ll_delete);
        btDelete = findViewById(R.id.bt_delete);
        chooseAll = findViewById(R.id.choose_all);

        collectRecycler = findViewById(R.id.collect_rlv);
        mDishCltDbOpenHelper = new DishCltDbOpenHelper(this);

        initData();
        initEvent();
    }

    private void initEvent() {
        vtManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(managed == false) {
                    vtManage.setText("完成");
                    managed = true;
                    llDelete.setVisibility(View.VISIBLE);
                }
                else{
                    vtManage.setText("管理");
                    managed = false;
                    llDelete.setVisibility(View.INVISIBLE);
                }

                mDishCltAdapter.setCheckBoxStatus(managed);

                deleteManage();
            }
        });

        vtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void deleteManage() {
        chooseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChooseAll == false) {
                    isChooseAll = true;
                    mDishCltAdapter.chooseAll();
                    chooseAll.setText("取消");
                }
                else {
                    isChooseAll = false;
                    mDishCltAdapter.cancelAll();
                    chooseAll.setText("全选");
                }

            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDishCltAdapter.deleteChosen();
                ToastUtil.toastShort(DishCollectActivity.this, "删除成功！");
            }
        });
    }

    private void initData() {
        llDelete.setVisibility(View.INVISIBLE); // 初始是看不见的

        mDish = mDishCltDbOpenHelper.queryAllFromDb();
        mDishCltAdapter = new DishCltAdapter(this, mDish);
        collectRecycler.setAdapter(mDishCltAdapter);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        collectRecycler.setLayoutManager(linearLayoutManager); // 创建布局管理器
        mDishCltAdapter.notifyDataSetChanged();
    }
}