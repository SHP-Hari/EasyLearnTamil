package com.sliit.easylearner;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sliit.easylearner.util.DatabaseHelper;

public class TamilWordsActivity extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamil_words);
        getSupportActionBar().hide();
        db = new DatabaseHelper(this);

        Button btnLearn = findViewById(R.id.btnLearn);
        btnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TamilWordsActivity.this, LearnWordsActivity.class);
                startActivity(intent);
            }
        });

        Button buttonTranslator = findViewById(R.id.btnWordAssignments);
        buttonTranslator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.checkPaperAttemptStatus(2)){
                    Intent intent = new Intent(TamilWordsActivity.this, WordsAssignmentActivity.class);
                    startActivity(intent);
                }else {
                    Snackbar.make(v, "You Have already Attempted this Assignment", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        Button btnAssignment = findViewById(R.id.btnTranslator);
        btnAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TamilWordsActivity.this, TamilTranslatorActivity.class);
                startActivity(intent);
            }
        });

        Button btnWriteWords = findViewById(R.id.btnWrite);
        btnWriteWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TamilWordsActivity.this, WriteWordsActivity.class);
                startActivity(intent);
            }
        });
    }

}
