package com.sliit.easylearner.model;

public class WordCategory {
    private String categoryId;
    private String categoryName;
    private String categoryImage;

    public WordCategory(String id, String name, String imgUrl){
        this.categoryId = id;
        this.categoryName  = name;
        this.categoryImage = imgUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
}
