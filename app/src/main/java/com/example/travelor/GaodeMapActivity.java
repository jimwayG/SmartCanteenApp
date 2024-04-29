package com.example.travelor;

import android.app.Activity;
import android.content.Context;
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

import java.util.HashMap;
import java.util.Map;


public class GaodeMapActivity extends Activity {
    private LinearLayout containerLayout;

    private MapView mapView;
    private AMap map;

    private Button button;

    private static Map<String, LatLng> CANTEEN_LOCATION_MAP;
    static
    {
        CANTEEN_LOCATION_MAP = new HashMap<>();
        CANTEEN_LOCATION_MAP.put("荷园二餐厅", new LatLng(34.815124, 113.530656));
    }

    public  static  final  String CITI_KEY="city";
    public  static  final int SHANGHAI=0;
    public  static  final int BEIJING=1;
    public  static  final int GUANGZHOU=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaode_map_layout);
        initContainerLayout();
        initAndAddButton();
        initAndAddMapComponents(savedInstanceState);
    }

    private void initAndAddButton() {
        button = new Button(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        button.setBackgroundColor(Color.parseColor("#A06565")); // Setting background color programmatically
        button.setGravity(Gravity.CENTER); // Centering the text
        button.setText("返回");
        button.setTextSize(30); // Setting text size in dp
        button.setTextColor(Color.WHITE);
        button.setOnClickListener(button -> {
            finish(); // Finish the activity
        });
        containerLayout.addView(button);
    }

    private void initAndAddMapComponents(Bundle savedInstanceState) {
        AMapOptions mapOptions = generateMapOptions(savedInstanceState);
        initMapView(savedInstanceState, mapOptions);
        addMapView2Container();
        initMapFromMapView();
    }

    private void initContainerLayout() {
        containerLayout = (LinearLayout) findViewById(R.id.gaode_map_layout);
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
    private AMapOptions generateMapOptions(Bundle savedInstanceState) {
        AMapOptions aOptions = new AMapOptions();
        aOptions.camera(new CameraPosition(CANTEEN_LOCATION_MAP.get(getIntent().getStringExtra("canteenName")), 19f, 0, 0));
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