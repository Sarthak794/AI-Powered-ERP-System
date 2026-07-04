package com.erp.ai;

public class DetectedHeading {

    private String type;

    private int index;

    public DetectedHeading() {
    }

    public DetectedHeading(String type, int index) {
        this.type = type;
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}