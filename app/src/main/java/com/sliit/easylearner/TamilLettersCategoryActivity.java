package com.sliit.easylearner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TamilLettersCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamil_letters_category);

        final Intent intentLearn = new Intent(TamilLettersCategoryActivity.this, TamilLettersLearnActivity.class);

        Button buttonVowels = findViewById(R.id.btnVowels);
        buttonVowels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentLearn.putExtra("lesson", "vowel");
                startActivity(intentLearn);
            }
        });

        Button buttonConstants = findViewById(R.id.btnConstants);
        buttonConstants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentLearn.putExtra("lesson", "constant");
                startActivity(intentLearn);
            }
        });

        Button buttonGrantha = findViewById(R.id.btnGrantha);
        buttonGrantha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentLearn.putExtra("lesson", "grantha");
                startActivity(intentLearn);
            }
        });
    }
}
