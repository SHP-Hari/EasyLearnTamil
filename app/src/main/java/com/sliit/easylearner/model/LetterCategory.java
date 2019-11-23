package com.sliit.easylearner.model;

public class LetterCategory {
    private int id;
    private String categoryName;
    private String categoryImage;
    private int status;

    public LetterCategory(int id, String catName, String catImage, int sts){
        this.id = id;
        this.categoryName = catName;
        this.categoryImage = catImage;
        this.status = sts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
