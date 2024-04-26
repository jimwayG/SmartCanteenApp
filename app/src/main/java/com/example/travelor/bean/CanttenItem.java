package com.example.travelor.bean;


public class CanttenItem {
    private String mainCantten;
    private String flowState;

    private int canttenImage;

    public CanttenItem(String mainCantten, String flowState, int canttenImage) {
        this.mainCantten = mainCantten;
        this.flowState = flowState;

        this.canttenImage = canttenImage;
    }

    public String getMainCantten() {
        return mainCantten;
    }

    public void setMainCantten(String mainCantten) {
        this.mainCantten = mainCantten;
    }

    public String getFlowState() {
        return flowState;
    }

    public void setFlowState(String flowState) {
        this.flowState = flowState;
    }



    public int getCanttenImage() {
        return canttenImage;
    }

    public void setCanttenImage(int canttenImage) {
        this.canttenImage = canttenImage;
    }
}
