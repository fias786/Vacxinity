package com.sharnoxz.vacxinity.ui.home;

public class HorizontalData {

    private int image;
    private String text,url;

    public HorizontalData(int image, String text, String url) {
        this.image = image;
        this.text = text;
        this.url = url;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
