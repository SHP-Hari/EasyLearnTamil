package com.sliit.easylearner;

import android.webkit.WebView;

public class WordListItem {
    private String sinhalaWord;
    private String tamilWord;

    public WordListItem(String sinhalaWord, String tamilWord) {
        this.sinhalaWord = sinhalaWord;
        this.tamilWord = tamilWord;
    }

    public String getSinhalaWord() {
        return sinhalaWord;
    }

    public void setSinhalaWord(String sinhalaWord) {
        this.sinhalaWord = sinhalaWord;
    }

    public String getTamilWord() {
        return tamilWord;
    }

    public void setTamilWord(String tamilWord) {
        this.tamilWord = tamilWord;
    }
}
