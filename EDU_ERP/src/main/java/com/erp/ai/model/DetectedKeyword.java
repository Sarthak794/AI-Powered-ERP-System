package com.erp.ai.model;

public class DetectedKeyword {

    private String category;

    private String keyword;

    public DetectedKeyword() {
    }

    public DetectedKeyword(String category,
                           String keyword) {

        this.category = category;
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}