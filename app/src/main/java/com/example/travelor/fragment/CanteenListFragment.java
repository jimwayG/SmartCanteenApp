package com.example.travelor.fragment;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelor.R;
import com.example.travelor.adapter.CanttenCardAdapter;
import com.example.travelor.bean.CanteenItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CanteenListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_canteen_list, container, false);
        initDateView(rootView);
        initCanteenListView(rootView);
        return rootView;
    }

    private void initCanteenListView(View rootView) {
        Resources res = getResources();
        List<CanteenItem> canteenList4Test = List.of(
                new CanteenItem("荷园一餐厅", "拥挤", ResourcesCompat.getDrawable(res, R.drawable.he1, null)),
                new CanteenItem("荷园二餐厅", "空闲", ResourcesCompat.getDrawable(res, R.drawable.he2, null), "荷园二食堂将于五月一日休息，届时将暂停营业，请各位同学提前安排好就餐时间。"),
                new CanteenItem("聚英园餐厅", "空闲", ResourcesCompat.getDrawable(res, R.drawable.jyj, null)),
                new CanteenItem("风华园餐厅", "空闲", ResourcesCompat.getDrawable(res, R.drawable.fhy, null))
        );
        RecyclerView canteenCardsRecycleView = rootView.findViewById(R.id.canteen_cards);
        canteenCardsRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        CanttenCardAdapter canttenCardAdapter = new CanttenCardAdapter(getContext(), canteenList4Test);
        canteenCardsRecycleView.setAdapter(canttenCardAdapter);
    }

    private void initDateView(View rootView) {
        TextView dateView = rootView.findViewById(R.id.date);
        dateView.setText(getCurrentTimeFormat());
    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

}