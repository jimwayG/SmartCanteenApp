package com.example.travelor.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.travelor.R;
import com.example.travelor.adapter.DishCardAdapter;
import com.example.travelor.bean.Dish;

import java.util.ArrayList;
import java.util.List;

import com.example.travelor.datebase.DishDbOpenHelper;

public class FrontPageFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private List<Dish> mDishes;
    private DishCardAdapter mDishAdapter;
    private SearchView searchView;
    private TextView showDish;
    private TextView showAll;
    private TextView showHumanity;
    private TextView showNature;
    private TextView showMfmg;
    private DishDbOpenHelper mDishDbOpenHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.front_page_layout, container, false);
        addListener2RootView(rootView);
        initView(rootView);
        initData();
        initEvent();

        // 搜索框
        searchView = rootView.findViewById(R.id.searchView);
        showDish = rootView.findViewById(R.id.show_attraction);
        showAll = rootView.findViewById(R.id.show_all);
        showNature = rootView.findViewById(R.id.show_nature);
        showHumanity = rootView.findViewById(R.id.show_humanity);
        showMfmg = rootView.findViewById(R.id.show_mfmg);

        search();
        categoryAct();

        return rootView;
    }

    private void addListener2RootView(View rootView) {
        // 在点击其他任何地方时，清除搜索框焦点
        rootView.setOnClickListener(view -> {
            searchView.clearFocus();
        });
    }

    // 按类别查询
    private void categoryAct() {
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDishes = mDishDbOpenHelper.queryAllFromDb();
                mDishAdapter.refreshData(mDishes);
                setLayout();
            }
        });

        showDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDishes = mDishDbOpenHelper.queryFromDbByCategory("饺子馄饨");
                mDishAdapter.refreshData(mDishes);
                setLayout();
            }
        });

        showNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDishes = mDishDbOpenHelper.queryFromDbByCategory("汉堡薯条");
                mDishAdapter.refreshData(mDishes);
                setLayout();
            }
        });

        showHumanity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDishes = mDishDbOpenHelper.queryFromDbByCategory("快餐便当");
                mDishAdapter.refreshData(mDishes);
                setLayout();
            }
        });
        showMfmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDishes = mDishDbOpenHelper.queryFromDbByCategory("米粉面馆");
                mDishAdapter.refreshData(mDishes);
                setLayout();
            }
        } );
    }

    // 菜肴名搜索
    private void search() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });
    }

    private void performSearch(String query) {
        mDishes = mDishDbOpenHelper.queryFromDbByName(query);
        mDishAdapter.refreshData(mDishes);
        setLayout();
    }

    // 回溯
    public void onResume() {
        super.onResume();
        refreshDataFromDb();
        setLayout();
        searchView.clearFocus();
        searchView.setQuery(searchView.getQuery(), true);
    }

    // 更新视图
    private void setLayout() {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager); // 创建布局管理器
        mDishAdapter.notifyDataSetChanged();
    }

    private void refreshDataFromDb() {
        mDishes = getDataFromDB();
        mDishAdapter.refreshData(mDishes);
    }

    private List<Dish> getDataFromDB() {
        return mDishDbOpenHelper.queryAllFromDb();
    }

    private void initEvent() {
        mDishAdapter = new DishCardAdapter(requireContext(), mDishes);
        mRecyclerView.setAdapter(mDishAdapter);
        // 点击列表的空白位置时，清除搜索框的焦点。空白位置一般在菜品列表不足时在，在菜品列表下方出现。
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null) {
                    // tapped on child
                    return false;
                } else {
                    // Tap occurred outside all child-views.
                    searchView.clearFocus();
                    return true;
                }
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private void initData() {
        mDishes = new ArrayList<>();
        mDishDbOpenHelper = new DishDbOpenHelper(requireContext());

    }

    private void initView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.rlv);
    }

}


