package com.example.travelor.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelor.R;
import com.example.travelor.adapter.CanttenCardAdapter;
import com.example.travelor.bean.Canteen;
import com.example.travelor.service.CanteenService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CanteenListFragment extends Fragment {

    private static final int UPDATE_DELAY_MILLI = 3000; // 1000 milliseconds == 1 second

    private CanteenService.GetService canteenGetService;
    private CanteenService.GetAllService canteenGetAllService;
    private CanttenCardAdapter canttenCardAdapter;
    private List<Canteen> canteenList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_canteen_list, container, false);
        initDateView(rootView);
        initServices(); // Init service before using
        initCanteenListView(rootView);
        cyclicallyUpdateCanteenListView();
        return rootView;
    }

    private void cyclicallyUpdateCanteenListView() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                initCanteenListViaRemote(canteenList);
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
        canteenGetAllService = retrofit.create(CanteenService.GetAllService.class);
    }

    private void initCanteenListView(View rootView) {
        canteenList = new ArrayList<>();
        initCanteenListViaRemote(canteenList);
        setViewAndAdapter(rootView, canteenList);
    }

    private void initCanteenListViaRemote(List<Canteen> canteenList) {
        canteenGetAllService.getAllCanteen().enqueue(new Callback<List<Canteen>>() {
            @Override
            public void onResponse(Call<List<Canteen>> call, Response<List<Canteen>> response) {
                Log.d("CanteenListFragment", String.format("Retrieve canteens info from remote:\n%s", response.body().toString()));
                canteenList.clear();
                canteenList.addAll(response.body());
                canttenCardAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<Canteen>> call, Throwable t) {
                Log.e("CanteenListFragment", t.toString());
            }
        });
    }

    private void setViewAndAdapter(View rootView, List<Canteen> canteenList) {
        RecyclerView canteenCardsRecycleView = rootView.findViewById(R.id.canteen_cards);
        canteenCardsRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        canttenCardAdapter = new CanttenCardAdapter(getContext(), canteenList);
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