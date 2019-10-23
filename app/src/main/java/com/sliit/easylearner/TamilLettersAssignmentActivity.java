package com.sliit.easylearner;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sliit.easylearner.model.McqQuestion;

import java.util.ArrayList;
import java.util.List;

public class TamilLettersAssignmentActivity extends AppCompatActivity {
    private List<McqQuestion> questions = new ArrayList<McqQuestion>();
    private McqQuestion currentQuestion;


    private int progress;
    String Uid;
    private final String preference_file_key = "APP_PROGRESS";
    ProgressDialog progressDialog;
    private int totalMarks;
    private int totalQuestionsCount;

    SharedPreferences sharedPref;


    Button btnOption1;
    Button btnOption2;
    Button btnOption3;
    Button btnOption4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamil_letters_assignment);

        //Adding MCQ questions
        questions.add(new McqQuestion("அ", 3, "ඌ", "ඓ", "අ", "ඒ"));
        questions.add(new McqQuestion("உ", 2, "එ", "උ", "ඉ", "ඕ"));
        questions.add(new McqQuestion("ஔ", 1, "ඖ", "ආ", "ඔ", "උ"));
        questions.add(new McqQuestion("ஐ", 4, "එ", "ඒ", "ඕ", "ඓ"));
        questions.add(new McqQuestion("இ", 4, "ඖ", "ඕ", "උ", "ඉ"));
        totalQuestionsCount = questions.size();

        final Context context = TamilLettersAssignmentActivity.this;
        sharedPref = context.getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();


        loadProgress();
        currentQuestion = questions.get(progress);
        if (sharedPref.contains("LETTERS_MCQ_SCORE")){
            totalMarks = sharedPref.getInt("LETTERS_MCQ_SCORE", 0);
        }else {
            editor.putInt("LETTERS_MCQ_SCORE", 0);
        }
        TextView textViewQuestionNumber = findViewById(R.id.assignmentNo);
        textViewQuestionNumber.setText(String.valueOf(progress+1));
        TextView textViewQuestion = findViewById(R.id.question);
        textViewQuestion.setText(questions.get(progress).getQuestion());

        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);

        btnOption1.setText(questions.get(progress).getOption1());
        btnOption2.setText(questions.get(progress).getOption2());
        btnOption3.setText(questions.get(progress).getOption3());
        btnOption4.setText(questions.get(progress).getOption4());

        btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentQuestion.validateAnswer(1)){
                    increaseMarks();
                    new AlertDialog.Builder(context)
                            .setTitle("Correct Answer")
                            .setMessage("Nice work. Your answer is correct and your current score is "+totalMarks)
                            .setIcon(R.drawable.ic_done_black_24dp)
                            .show();
                }else {
                    String correctAnswer = "";
                    if (questions.get(progress).getAnswer() == 1){
                        correctAnswer = questions.get(progress).getOption1();
                    }else if (questions.get(progress).getAnswer() == 2){
                        correctAnswer = questions.get(progress).getOption2();
                    }else if (questions.get(progress).getAnswer() == 3){
                        correctAnswer = questions.get(progress).getOption3();
                    }else if (questions.get(progress).getAnswer() == 4){
                        correctAnswer = questions.get(progress).getOption4();
                    }
                    new AlertDialog.Builder(context)
                            .setTitle("Wrong Answer")
                            .setMessage("Oops! Looks like you need to study more. Correct answer is "+correctAnswer)
                            .setIcon(R.drawable.ic_close_black_24dp)
                            .show();
                }
            }
        });

        btnOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentQuestion.validateAnswer(2)){
                    increaseMarks();
                    new AlertDialog.Builder(context)
                            .setTitle("Correct Answer")
                            .setMessage("Nice work. Your answer is correct and your current score is "+totalMarks)
                            .setIcon(R.drawable.ic_done_black_24dp)
                            .show();
                }else {
                    new AlertDialog.Builder(context)
                            .setTitle("Wrong Answer")
                            .setMessage("Oops! Looks like you need to study more.")
                            .setIcon(R.drawable.ic_close_black_24dp)
                            .show();
                }
            }
        });

        btnOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentQuestion.validateAnswer(3)){
                    increaseMarks();
                    new AlertDialog.Builder(context)
                            .setTitle("Correct Answer")
                            .setMessage("Nice work. Your answer is correct and your current score is "+totalMarks)
                            .setIcon(R.drawable.ic_done_black_24dp)
                            .show();
                }else {
                    new AlertDialog.Builder(context)
                            .setTitle("Wrong Answer")
                            .setMessage("Oops! Looks like you need to study more.")
                            .setIcon(R.drawable.ic_close_black_24dp)
                            .show();
                }
            }
        });

        btnOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentQuestion.validateAnswer(4)){
                    increaseMarks();
                    new AlertDialog.Builder(context)
                            .setTitle("Correct Answer")
                            .setMessage("Nice work. Your answer is correct and your current score is "+totalMarks)
                            .setIcon(R.drawable.ic_done_black_24dp)
                            .show();
                }else {
                    String correctAnswer = "";
                    if (questions.get(progress).getAnswer() == 1){
                        correctAnswer = questions.get(progress).getOption1();
                    }else if (questions.get(progress).getAnswer() == 2){
                        correctAnswer = questions.get(progress).getOption2();
                    }else if (questions.get(progress).getAnswer() == 3){
                        correctAnswer = questions.get(progress).getOption3();
                    }else if (questions.get(progress).getAnswer() == 4){
                        correctAnswer = questions.get(progress).getOption4();
                    }
                    new AlertDialog.Builder(context)
                            .setTitle("Wrong Answer")
                            .setMessage("Oops! Looks like you need to study more. The correct answer is "+correctAnswer)
                            .setIcon(R.drawable.ic_close_black_24dp)
                            .show();
                }
            }
        });

        Button btnPreviouseAssignment = findViewById(R.id.btnPreAssignment);
        btnPreviouseAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress--;
                saveProgress(progress);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        Button btnNextAssignment = findViewById(R.id.btnNextAssignment);
        btnNextAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress++;
                saveProgress(progress);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    private void saveProgress(int value){
        if (value < questions.size() && value >= 0){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("LETTERS_MCQ_PROGRESS", value);
            editor.commit();
        }else {
            makeToast("That's the end of assignments");
        }
    }

    private boolean enableMarksUpdate = true;
    private void increaseMarks(){
        if (enableMarksUpdate && totalMarks < questions.size()){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("LETTERS_MCQ_SCORE", ++totalMarks);
            editor.commit();
            enableMarksUpdate = false;
            TextView textViewScore = findViewById(R.id.scoreText);
            textViewScore.setText("Yout current score is "+ totalMarks+" out of "+questions.size());
        }
    }

    private void loadProgress(){
        if (sharedPref.getInt("LETTERS_MCQ_PROGRESS", 1) <= questions.size()){
            makeToast("exceeded"+ questions.size() + " "+ sharedPref.getInt("LETTERS_MCQ_PROGRESS", 0));
            progress = sharedPref.getInt("LETTERS_MCQ_PROGRESS", 0);
        }else {
            progress = 0;
            makeToast("success"+ questions.size() + " "+ sharedPref.getInt("LETTERS_MCQ_PROGRESS", 0));
        }
        totalMarks = sharedPref.getInt("LETTERS_MCQ_SCORE", 0);
        TextView textViewScore = findViewById(R.id.scoreText);
        textViewScore.setText("Yout current score is "+ totalMarks+" out of "+questions.size());
    }

    private void makeToast(String msg){
        Toast.makeText(TamilLettersAssignmentActivity.this,msg, Toast.LENGTH_SHORT).show();
    }
}
