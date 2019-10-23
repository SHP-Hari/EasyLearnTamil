package com.sliit.easylearner.model;

public class LearningWord {
    private String id;
    private String catId;
    private String tamilWord;
    private String sinhalaWord;

    public LearningWord(String id, String cat, String tamil, String sinhala){
        this.id = id;
        this.catId = cat;
        this.tamilWord = tamil;
        this.sinhalaWord = sinhala;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getTamilWord() {
        return tamilWord;
    }

    public void setTamilWord(String tamilWord) {
        this.tamilWord = tamilWord;
    }

    public String getSinhalaWord() {
        return sinhalaWord;
    }

    public void setSinhalaWord(String sinhalaWord) {
        this.sinhalaWord = sinhalaWord;
    }
}
