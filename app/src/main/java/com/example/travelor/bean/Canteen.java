package com.example.travelor.bean;


import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Canteen implements Serializable {

    private String name;
    private String flowState;
    private String announcement;
    private String imageUrl;
    private transient Drawable canteenImage;

    public Canteen(String name, String flowState, String announcement, String imageUrl) {
        this.name = name;
        this.flowState = flowState;
        this.announcement = announcement;
        this.imageUrl = imageUrl;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Canteen(String name, String flowState, Drawable canteenImage) {
        this.name = name;
        this.flowState = flowState;
        this.canteenImage = canteenImage;
    }

    public Canteen(String name, String flowState, Drawable canteenImage, String announcement) {
        this.name = name;
        this.flowState = flowState;
        this.canteenImage = canteenImage;
        this.announcement = announcement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlowState() {
        return flowState;
    }

    public void setFlowState(String flowState) {
        this.flowState = flowState;
    }

    public Drawable getCanteenImage() {
        return canteenImage;
    }

    public void setCanteenImage(Drawable canteenImage) {
        this.canteenImage = canteenImage;
    }

}
