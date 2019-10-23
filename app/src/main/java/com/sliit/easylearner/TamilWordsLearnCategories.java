package com.sliit.easylearner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TamilWordsLearnCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamil_words_learn_categories);
        getSupportActionBar().hide();

        Button btnNouns = findViewById(R.id.btnNouns);
        Button btnVerbs = findViewById(R.id.btnVerbs);
        Button btnNumbers = findViewById(R.id.btnNumbers);
        Button btnDateTime =findViewById(R.id.btnDateTime);
        Button btnClothes = findViewById(R.id.btnClothes);
        Button btnAnimals = findViewById(R.id.btnAnimals);
        Button btnVegetables = findViewById(R.id.btnVegitables);
        Button btnFruits = findViewById(R.id.btnFruits);
        Button btnColors = findViewById(R.id.btnColors);

        btnNouns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLesson("noun");
            }
        });

        btnVerbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLesson("verb");
            }
        });

        btnNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLesson("number");
            }
        });

        btnDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLesson("datetime");
            }
        });

        btnClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLesson("clothes");
            }
        });

        btnAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLesson("animals");
            }
        });

        btnVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLesson("vegetables");
            }
        });

        btnFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLesson("fruits");
            }
        });

        btnColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLesson("colors");
            }
        });
    }

    private void loadLesson(String type){
        Intent intent = new Intent(TamilWordsLearnCategories.this, TamilWordsLearnActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
