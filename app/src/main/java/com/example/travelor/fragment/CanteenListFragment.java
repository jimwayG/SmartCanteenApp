package com.example.travelor.fragment;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import com.example.travelor.bean.Canteen;
import com.example.travelor.datebase.CanteenDbOpenHelper;
import com.example.travelor.service.Monitoring;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CanteenListFragment extends Fragment {

    private Monitoring.GetService monitoringGetService;
    private CanttenCardAdapter canttenCardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_canteen_list, container, false);
        initDateView(rootView);
        initServices(); // Init service before using
        initCanteenListView(rootView);
        return rootView;
    }

    private void initServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Monitoring.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        monitoringGetService = retrofit.create(Monitoring.GetService.class);
    }

    private void initCanteenListView(View rootView) {
        Cursor cursor = getCursor4CanteenDb();
        List<Canteen> canteenList4Test = new ArrayList<>();
        insertCanteens(cursor, canteenList4Test);
//        canteenList4Test.add(new Canteen("荷园一餐厅", "空闲", ResourcesCompat.getDrawable(res, R.drawable.he1, null)));
//        canteenList4Test.add(new Canteen("荷园二餐厅", "空闲", ResourcesCompat.getDrawable(res, R.drawable.he2, null), "荷园二食堂将于五月一日休息，届时将暂停营业，请各位同学提前安排好就餐时间。"));
//        canteenList4Test.add(new Canteen("聚英园餐厅", "空闲", ResourcesCompat.getDrawable(res, R.drawable.jyj, null)));
//        canteenList4Test.add(new Canteen("风华园餐厅", "空闲", ResourcesCompat.getDrawable(res, R.drawable.fhy, null)));
        updateCanteenListInfoViaRemote(canteenList4Test);
        setViewAndAdapter(rootView, canteenList4Test);
    }

    private void setViewAndAdapter(View rootView, List<Canteen> canteenList4Test) {
        RecyclerView canteenCardsRecycleView = rootView.findViewById(R.id.canteen_cards);
        canteenCardsRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        canttenCardAdapter = new CanttenCardAdapter(getContext(), canteenList4Test);
        canteenCardsRecycleView.setAdapter(canttenCardAdapter);
    }

    private static void insertCanteens(Cursor cursor, List<Canteen> canteenList4Test) {
        while(cursor.moveToNext()) {
            canteenList4Test.add(
                    new Canteen(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                    )
            );
        }
        cursor.close();
    }

    private Cursor getCursor4CanteenDb() {
        CanteenDbOpenHelper canteenDbOpenHelper = new CanteenDbOpenHelper(getContext());
        SQLiteDatabase canteenDb = canteenDbOpenHelper.getReadableDatabase();
        Cursor cursor = canteenDb.query(
                CanteenDbOpenHelper.CanteenEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        return cursor;
    }

    private void updateCanteenListInfoViaRemote(List<Canteen> canteenList4Test) {
        for (int i = 0; i < canteenList4Test.size(); i++) {
            Canteen canteen = canteenList4Test.get(i);
            try2UpdateFlowStateViaRemote(canteen, i);
        }
    }

    private void try2UpdateFlowStateViaRemote(Canteen canteen, int position) {
        monitoringGetService.getMonitoring(canteen.getName()).enqueue(new Callback<Monitoring.CanteenMonitoring>() {
            @Override
            public void onResponse(Call<Monitoring.CanteenMonitoring> call, Response<Monitoring.CanteenMonitoring> response) {
                try2UpdateFlowState(response);
            }

            private void try2UpdateFlowState(Response<Monitoring.CanteenMonitoring> response) {
                Monitoring.CanteenMonitoring canteenMonitoring = response.body();
                if (Objects.isNull(canteenMonitoring)) {
                    Log.w("CanteenListFragment", String.format("Canteen (%s) monitoring info is null", canteen.getName()));
                    return;
                }
                canteen.setFlowState(canteenMonitoring.getState());
                canttenCardAdapter.notifyItemChanged(position);
            }

            @Override
            public void onFailure(Call<Monitoring.CanteenMonitoring> call, Throwable t) {
                Log.e("CanteenListFragment", String.format("Can not retrieve canteen (%s) monitoring info from service: %s", canteen.getName(), t));
            }
        });
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