package com.example.travelor.bean;


import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class CanteenItem implements Serializable {

    private String canteenName;
    private String flowState;
    private String announcement;
    private transient Drawable canteenImage;

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public CanteenItem(String canteenName, String flowState, Drawable canteenImage) {
        this.canteenName = canteenName;
        this.flowState = flowState;
        this.canteenImage = canteenImage;
    }

    public CanteenItem(String canteenName, String flowState, Drawable canteenImage, String announcement) {
        this.canteenName = canteenName;
        this.flowState = flowState;
        this.canteenImage = canteenImage;
        this.announcement = announcement;
    }

    public String getCanteenName() {
        return canteenName;
    }

    public void setCanteenName(String canteenName) {
        this.canteenName = canteenName;
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
