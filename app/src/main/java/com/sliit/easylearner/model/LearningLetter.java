package com.sliit.easylearner.model;

public class LearningLetter {
    private int letterId;
    private int categoryId;
    private String letterTamil;
    private String letterSinhala;
    private String letterImage;

    public LearningLetter(int id, int catId, String tamil, String sinhala, String img){
        this.letterId = id;
        this.categoryId = catId;
        this.letterTamil = tamil;
        this.letterSinhala = sinhala;
        this.letterImage = img;
    }

    public int getLetterId() {
        return letterId;
    }

    public void setLetterId(int letterId) {
        this.letterId = letterId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getLetterTamil() {
        return letterTamil;
    }

    public void setLetterTamil(String letterTamil) {
        this.letterTamil = letterTamil;
    }

    public String getLetterSinhala() {
        return letterSinhala;
    }

    public void setLetterSinhala(String letterSinhala) {
        this.letterSinhala = letterSinhala;
    }

    public String getLetterImage() {
        return letterImage;
    }

    public void setLetterImage(String letterImage) {
        this.letterImage = letterImage;
    }
}
