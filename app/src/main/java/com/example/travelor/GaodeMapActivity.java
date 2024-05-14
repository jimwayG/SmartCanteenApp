package com.example.travelor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import com.example.travelor.bean.Canteen;

import java.util.HashMap;
import java.util.Map;


public class GaodeMapActivity extends Activity {

    private LinearLayout containerLayout;
    private Button backButton;
    private MapView mapView;
    private AMap map;
    private Canteen canteen;
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
        canteen = (Canteen) getIntent().getSerializableExtra("canteenItem");
    }

    private void addBackButton() {
        backButton = new Button(this);
        backButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        backButton.setBackgroundColor(Color.parseColor("#A06565")); // Setting background color programmatically
        backButton.setGravity(Gravity.CENTER); // Centering the text
        backButton.setText("返回/跳转高德地图");
        backButton.setTextSize(30); // Setting text size in dp
        backButton.setTextColor(Color.WHITE);
        backButton.setOnClickListener(button -> {
            // 提供一个对话框让用户选择返回还是打开地图
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            // 用户选择打开地图
                            jumpToGaoDeMapApp(CANTEEN_LOCATION_MAP.get(canteen.getName()));
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            // 用户选择返回
                            finish();
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("是否打开高德地图查看位置？").setPositiveButton("打开地图", dialogClickListener)
                    .setNegativeButton("返回", dialogClickListener).show();
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
        aOptions.camera(new CameraPosition(CANTEEN_LOCATION_MAP.get(canteen.getName()), 19f, 0, 0));
        return aOptions;
    }

    private void jumpToGaoDeMapApp(LatLng location) {
        String longitude = String.valueOf(location.longitude);
        String latitude = String.valueOf(location.latitude);
        String uri = "androidamap://viewMap?sourceApplication=智慧食堂&lat=" + latitude + "&lon=" + longitude + "&dev=0";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.autonavi.minimap");
        startActivity(intent);
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