package com.sliit.easylearner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sliit.easylearner.model.LearningLetter;
import com.sliit.easylearner.model.LearningWord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TamilLettersLearnActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewSinhalaLetter;
    private TextView textViewTamilLetter;
    private ImageView imageView;
    private Button buttonPre;
    private Button buttonNext;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String cat_id;
    private String cat_name;
    int index;
    ProgressDialog progressDialog;
    ArrayList<LearningLetter> mLearningLetters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_tamil_letters_learn);


        Context context = TamilLettersLearnActivity.this;
        sharedPreferences = context.getSharedPreferences("progress", context.MODE_PRIVATE);
        Bundle extras = getIntent().getExtras();
        editor =sharedPreferences.edit();

        getData();
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewSinhalaLetter = findViewById(R.id.textViewSinhalaLetter);
        textViewTamilLetter = findViewById(R.id.question);
        imageView = findViewById(R.id.imageViewLetter);
        buttonPre = findViewById(R.id.buttonPre);
        buttonNext = findViewById(R.id.buttonNext);

        if (savedInstanceState != null){
            index = savedInstanceState.getInt("indexKey");
        }else {
            index = 0;
        }

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButton();
            }
        });

        buttonPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previestButton();
            }
        });
    }

    private void getData() {
        if (getIntent().hasExtra("categoryName") && getIntent().hasExtra("categoryId")){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait While Loading...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            cat_id = getIntent().getStringExtra("categoryId");
            cat_name = getIntent().getStringExtra("categoryName");

            loadLetters();
        }
    }

    private void loadLetters() {
        String URL_LETTERS_BY_CATEGORY = "https://easylearntamil.000webhostapp.com/letter-categories/"+Integer.parseInt(cat_id);
        Log.d("TAG", "is : "+URL_LETTERS_BY_CATEGORY);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_LETTERS_BY_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.cancel();
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("success");
                            if (error){
                                JSONArray jsonArray = jsonObject.getJSONArray("letters");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject letter = jsonArray.getJSONObject(i);
                                    mLearningLetters.add(new LearningLetter(letter.getInt("id"),
                                            letter.getInt("categoryId"),
                                            letter.getString("letterTamil"),
                                            letter.getString("letterSinhala"),
                                            letter.getString("letterImage"))
                                    );
                                }
                                initializeView();
                            }else {
                                Toast.makeText(TamilLettersLearnActivity.this, "Error Data", Toast.LENGTH_SHORT).show();
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

    private void initializeView() {
        textViewTitle.setText(cat_name);
        textViewSinhalaLetter.setText(mLearningLetters.get(index).getLetterSinhala());
        textViewTamilLetter.setText(mLearningLetters.get(index).getLetterTamil());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.tenor);

        Glide.with(this)
                .load(mLearningLetters.get(index).getLetterImage())
                .apply(requestOptions)
                .into(imageView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("indexKey", index);
    }

    private void nextButton(){
        index = (index+1) % mLearningLetters.size();
        if (index == 0){
            android.support.v7.app.AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Finished");
            alert.setCancelable(false);
            alert.setMessage("You Have Completed Learning "+ cat_name);
            alert.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.show();
        }
        textViewSinhalaLetter.setText(mLearningLetters.get(index).getLetterSinhala());
        textViewTamilLetter.setText(mLearningLetters.get(index).getLetterTamil());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.tenor);

        Glide.with(this)
                .load(mLearningLetters.get(index).getLetterImage())
                .apply(requestOptions)
                .into(imageView);
    }

    private void previestButton(){
        if (index != 0){
            index = (index-1);
        }

        if (index == 0){
            finish();
        }
        textViewSinhalaLetter.setText(mLearningLetters.get(index).getLetterSinhala());
        textViewTamilLetter.setText(mLearningLetters.get(index).getLetterTamil());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.tenor);

        Glide.with(this)
                .load(mLearningLetters.get(index).getLetterImage())
                .apply(requestOptions)
                .into(imageView);
    }
}
