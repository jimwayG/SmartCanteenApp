package com.example.travelor.bean;


import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Canteen implements Serializable {

    private String name;
    private String state;
    private String announcement;
    private String imageUrl;
    private int capacity;
    private int peopleNum;
    private double saturation;
    private transient Drawable canteenImage;

    public Canteen(String name, String state, Drawable canteenImage) {
        this.name = name;
        this.state = state;
        this.canteenImage = canteenImage;
    }

    public Canteen(String name, String state, Drawable canteenImage, String announcement) {
        this.name = name;
        this.state = state;
        this.canteenImage = canteenImage;
        this.announcement = announcement;
    }

    public Canteen(String name, String state, String announcement, String imageUrl) {
        this.name = name;
        this.state = state;
        this.announcement = announcement;
        this.imageUrl = imageUrl;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Drawable getCanteenImage() {
        return canteenImage;
    }

    public void setCanteenImage(Drawable canteenImage) {
        this.canteenImage = canteenImage;
    }

    @Override
    public String toString() {
        return "Canteen{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", announcement='" + announcement + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", capacity=" + capacity +
                ", peopleNum=" + peopleNum +
                ", saturation=" + saturation +
                '}';
    }
}
