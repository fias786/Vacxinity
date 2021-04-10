package com.sharnoxz.vacxinity.ui.home;


import java.util.ArrayList;

public class VerticalData {
    private ArrayList<HorizontalData> horizontalData;
    private String title;

    public VerticalData(ArrayList<HorizontalData> horizontalData, String title) {
        this.horizontalData = horizontalData;
        this.title = title;
    }


    public ArrayList<HorizontalData> getHorizontalData() {
        return horizontalData;
    }

    public void setHorizontalData(ArrayList<HorizontalData> horizontalData) {
        this.horizontalData = horizontalData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
