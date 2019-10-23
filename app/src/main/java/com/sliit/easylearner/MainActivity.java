package com.sliit.easylearner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        WebView bg = findViewById(R.id.mainBgWV);
//        bg.getSettings().setJavaScriptEnabled(true);
//        bg.loadUrl("file:///android_asset/splash.html");
        getSupportActionBar().hide();

        Button btnWords = findViewById(R.id.buttonTamilWords);
        btnWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TamilWordsActivity.class);
                startActivity(intent);
            }
        });

        Button btnLetters = findViewById(R.id.buttonTamilLetters);
        btnLetters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TamilLettersActivity.class);
                startActivity(intent);
            }
        });
    }
}
