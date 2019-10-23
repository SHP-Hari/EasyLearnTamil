package com.sliit.easylearner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sliit.easylearner.model.LearningWord;
import com.sliit.easylearner.model.WordCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LearnWordsByCategoryActivity extends AppCompatActivity {

    private String cat_id;
    private String cat_name;
    TextView subtitle;
    TextView tamilWord;
    TextView sinhalaWord;
    ImageButton mic;
    Button priviest;
    Button next;
    int index;
    ProgressDialog progressDialog;
    ArrayList<LearningWord> learningWordArrayList = new ArrayList<>();

    private final int NR_OF_SIMULTANEOUS_SOUND = 7;
    private final float LEFT_VOLUME = 1.0f;
    private final float RIGHT_VOLUME = 1.0f;
    private final int NO_LOOP = 0;
    private final int PRIORITY = 0;
    private final float NORMAL_PLAY_RATE = 1.0f;

    private SoundPool soundPool;
    private int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_learn_words_by_category);

        getIncomingData();
        subtitle = findViewById(R.id.subtitle);
        tamilWord = findViewById(R.id.tamilWord);
        sinhalaWord = findViewById(R.id.sinhalaWord);
        mic = findViewById(R.id.micbtn);
        priviest = findViewById(R.id.buttonPreWord);
        next = findViewById(R.id.buttonNextWord);

        soundPool = new SoundPool(NR_OF_SIMULTANEOUS_SOUND, AudioManager.STREAM_MUSIC, 0 );
        if (savedInstanceState != null){
            index = savedInstanceState.getInt("indexKey");
        }else {
            index = 0;
        }
    }

    private void getIncomingData() {
        if (getIntent().hasExtra("categoryName") && getIntent().hasExtra("categoryId")){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait While Loading...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            cat_id = getIntent().getStringExtra("categoryId");
            cat_name = getIntent().getStringExtra("categoryName");
//            subtitle.setText(cat_name);
            loadWords();
        }
    }

    private void loadWords() {
        String URL_WORDS_BY_CATEGORY = "https://easylearntamil.000webhostapp.com/categories/"+Integer.parseInt(cat_id);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_WORDS_BY_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.cancel();
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("success");
                            if (error){
                                JSONArray jsonArray = jsonObject.getJSONArray("words");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject word = jsonArray.getJSONObject(i);
                                    learningWordArrayList.add(new LearningWord(word.getString("id"),
                                            word.getString("categoryId"),
                                            word.getString("wordTamil"),
                                            word.getString("wordSinhala"))
                                    );
                                }
                                initializeView();
                            }else {
                                Toast.makeText(LearnWordsByCategoryActivity.this, "Error Data", Toast.LENGTH_SHORT).show();
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
        subtitle.setText(cat_name);
        tamilWord.setText(learningWordArrayList.get(index).getTamilWord());
        sinhalaWord.setText(learningWordArrayList.get(index).getSinhalaWord());
        String soundName = "tamil_"+learningWordArrayList.get(index).getId();
        int resID=getResources().getIdentifier(soundName, "raw", getPackageName());
        soundId = soundPool.load(getApplicationContext(), resID, 1);
    }

    public void playSound(View view) {
        if (soundId != 999){
            soundPool.play(soundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
        }else {
            Toast.makeText(this, "Cannot play the Sound!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("indexKey", index);
    }

    private void nextQuestion(){
        index = (index+1) % learningWordArrayList.size();
        if (index == 0){
            android.support.v7.app.AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Finished");
            alert.setCancelable(false);
            alert.setMessage("You Have Completed Learning all the words!");
            alert.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.show();
        }
        tamilWord.setText(learningWordArrayList.get(index).getTamilWord());
        sinhalaWord.setText(learningWordArrayList.get(index).getSinhalaWord());
        String soundName = "tamil_"+learningWordArrayList.get(index).getId();
        try {
            int resID=getResources().getIdentifier(soundName, "raw", getPackageName());
            soundId = soundPool.load(getApplicationContext(), resID, 1);
        }catch (Resources.NotFoundException e){
            soundId = 999;
        }

    }

    public void previestQuestion(){
        if (index != 0){
            index = (index-1);
        }

        if (index == 0){
            finish();
        }
        tamilWord.setText(learningWordArrayList.get(index).getTamilWord());
        sinhalaWord.setText(learningWordArrayList.get(index).getSinhalaWord());
        String soundName = "tamil_"+learningWordArrayList.get(index).getId();
        try {
            int resID=getResources().getIdentifier(soundName, "raw", getPackageName());
            soundId = soundPool.load(getApplicationContext(), resID, 1);
        }catch (Resources.NotFoundException e){
            soundId = 999;
        }
    }

    public void goForward(View view) {
        nextQuestion();
    }

    public void goBack(View view) {
        previestQuestion();
    }
}
