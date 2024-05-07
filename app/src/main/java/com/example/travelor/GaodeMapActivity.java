package com.example.travelor;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.example.travelor.bean.CanteenItem;

import java.util.HashMap;
import java.util.Map;


public class GaodeMapActivity extends Activity {

    private LinearLayout containerLayout;
    private Button backButton;
    private MapView mapView;
    private AMap map;
    private CanteenItem canteenItem;
    private static Map<String, LatLng> CANTEEN_LOCATION_MAP;
    static
    {
        CANTEEN_LOCATION_MAP = new HashMap<>();
        CANTEEN_LOCATION_MAP.put("荷园一餐厅", new LatLng(34.814042, 113.53044));
        CANTEEN_LOCATION_MAP.put("荷园二餐厅", new LatLng(34.815124, 113.530656));
        CANTEEN_LOCATION_MAP.put("风华园餐厅", new LatLng(34.812607, 113.530411));
        CANTEEN_LOCATION_MAP.put("聚英园餐厅", new LatLng(34.811744, 113.530406));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaode_map_layout);
        initCanteenItem();
        initContainerLayout();
        addBackButton();
        addMapComponents(savedInstanceState);
    }

    private void initCanteenItem() {
        canteenItem = (CanteenItem) getIntent().getSerializableExtra("canteenItem");
    }

    private void addBackButton() {
        backButton = new Button(this);
        backButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        backButton.setBackgroundColor(Color.parseColor("#A06565")); // Setting background color programmatically
        backButton.setGravity(Gravity.CENTER); // Centering the text
        backButton.setText("返回");
        backButton.setTextSize(30); // Setting text size in dp
        backButton.setTextColor(Color.WHITE);
        backButton.setOnClickListener(button -> {
            finish(); // Finish the activity
        });
        containerLayout.addView(backButton);
    }

    private void addMapComponents(Bundle savedInstanceState) {
        AMapOptions mapOptions = generateMapOptions();
        initMapView(savedInstanceState, mapOptions);
        addMapView2Container();
        initMapFromMapView();
    }

    private void initContainerLayout() {
        containerLayout = findViewById(R.id.gaode_map_layout);
    }

    private void addMapView2Container() {
        containerLayout.addView(mapView,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
    }

    private void initMapView(Bundle savedInstanceState, AMapOptions aOptions) {
        mapView = new MapView(this, aOptions);
        mapView.onCreate(savedInstanceState);
    }

    @NonNull
    private AMapOptions generateMapOptions() {
        AMapOptions aOptions = new AMapOptions();
        aOptions.camera(new CameraPosition(CANTEEN_LOCATION_MAP.get(canteenItem.getCanteenName()), 19f, 0, 0));
        return aOptions;
    }

    private void initMapFromMapView() {
        if (map == null) {
            map = mapView.getMap();
        }
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}