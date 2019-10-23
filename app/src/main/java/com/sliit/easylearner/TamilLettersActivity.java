package com.sliit.easylearner;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sliit.easylearner.util.DatabaseHelper;

public class TamilLettersActivity extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamil_letters);
        getSupportActionBar().hide();
        db = new DatabaseHelper(this);

        Button buttonLearn = findViewById(R.id.btnLearn);
        buttonLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TamilLettersActivity.this, TamilLettersCategoryActivity.class);
                startActivity(intent);
            }
        });

        Button buttonAssignment = findViewById(R.id.btnAssignments);
        buttonAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.checkPaperAttemptStatus(1)){
                    Intent intent = new Intent(TamilLettersActivity.this, LettersAssignmentActivity.class);
                    startActivity(intent);
                }else {
                    Snackbar.make(v, "You Have already Attempted this Assignment", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        Button buttonWrite = findViewById(R.id.btnWrite);
        buttonWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TamilLettersActivity.this, WriteLettersActivity.class);
                startActivity(intent);
            }
        });
    }
}
