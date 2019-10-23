package com.sliit.easylearner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sliit.easylearner.model.McqQuestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdvanceAssignmentActivity extends AppCompatActivity {

    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;
    TextView questionText;
    int mIndex;
    String question;
    TextView scoreTextView;
    ProgressBar mProgressBar;
    ProgressDialog progressDialog;
    int mScore;
    LinearLayout scoreLayout;
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    ArrayList<Integer> timeTaken = new ArrayList<>();

    private static final String URL_ADVANCED_ASSIGNMENT = "https://easylearntamil.000webhostapp.com/advanced-assignment";

    List<McqQuestion> questionList;

    int PROGRESS_BAR_INCREMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_advance_assignment);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait While Loading...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        questionList = new ArrayList<>();
        loadQuestions();
        if (savedInstanceState != null){
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("indexKey");
        }else {
            mScore = 0;
            mIndex = 0;
        }

        scoreLayout = (LinearLayout) findViewById(R.id.answerLayout);
        answer1 = (Button) findViewById(R.id.opt1);
        answer2 = (Button) findViewById(R.id.opt2);
        answer3 = (Button) findViewById(R.id.opt3);
        answer4 = (Button) findViewById(R.id.opt4);
        questionText = (TextView) findViewById(R.id.question_text_view);
        scoreTextView = (TextView) findViewById(R.id.score);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        chronometer = findViewById(R.id.time);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 100000000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(AdvanceAssignmentActivity.this, "Bing!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(1);
                updateQuestion();
            }
        });
        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(2);
                updateQuestion();
            }
        });
        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(3);
                updateQuestion();
            }
        });
        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(4);
                updateQuestion();
            }
        });
    }

    private void loadQuestions() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ADVANCED_ASSIGNMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.cancel();
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("success");
                            if (error){
                                JSONArray jsonArray = jsonObject.getJSONArray("questions");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject question = jsonArray.getJSONObject(i);
                                    questionList.add(new McqQuestion(question.getString("question"),
                                            question.getInt("correctAnswer"),
                                            question.getString("answer1"),
                                            question.getString("answer2"),
                                            question.getString("answer3"),
                                            question.getString("answer4"))
                                    );
                                }
                                initializeAssignment();
                            }else {
                                Toast.makeText(AdvanceAssignmentActivity.this, "Error Data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void initializeAssignment() {
        PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0/questionList.size());
        question = questionList.get(mIndex).getQuestion();
        questionText.setText(question);
        answer1.setText(questionList.get(mIndex).getOption1());
        answer2.setText(questionList.get(mIndex).getOption2());
        answer3.setText(questionList.get(mIndex).getOption3());
        answer4.setText(questionList.get(mIndex).getOption4());

        scoreTextView.setText("Score "+mScore+"/"+questionList.size());
        startChronometer();

    }

    private void updateQuestion(){
        resetChronometer();
        mIndex = (mIndex+1) % questionList.size();
        if (mIndex == 0){
            android.support.v7.app.AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Finished");
            alert.setCancelable(false);
            alert.setMessage("You Have Scored "+mScore+" Points");
            alert.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.show();
        }
        question = questionList.get(mIndex).getQuestion();
        questionText.setText(question);
        answer1.setText(questionList.get(mIndex).getOption1());
        answer2.setText(questionList.get(mIndex).getOption2());
        answer3.setText(questionList.get(mIndex).getOption3());
        answer4.setText(questionList.get(mIndex).getOption4());
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        scoreTextView.setText("Score "+mScore+"/"+questionList.size());
        startChronometer();
    }

    private void checkAnswer(int userAnswer){
        pauseChronometer();
        int elapsedMillis = (int) (SystemClock.elapsedRealtime() - chronometer.getBase());
        timeTaken.add(elapsedMillis);
        int correctAnswer = questionList.get(mIndex).getAnswer();
        if (userAnswer == correctAnswer){
            Snackbar.make(scoreLayout, "Correct Answer", Snackbar.LENGTH_SHORT).show();
            mScore = mScore + 1;
        }else {
            Snackbar.make(scoreLayout, "Wrong Answer", Snackbar.LENGTH_SHORT ).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey", mScore);
        outState.putInt("indexKey", mIndex);
    }

    public void startChronometer() {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer() {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Toast.makeText(this, "Please Complete the Assignment", Toast.LENGTH_SHORT).show();
    }
}
