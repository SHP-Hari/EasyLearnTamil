package com.sliit.easylearner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TamilLettersLearnActivity extends AppCompatActivity {

    private String[] vowelsSinhala = {"අ (ආනා)", "ආ (ආවන්නා)", "ඉ (ඉනා)", "ඊ (ඊවන්නා)", "උ (උනා)", "ඌ (ඌවන්නා)", "එ (එනා)", "ඒ (ඒයන්නා)", "ඓ (ඓයන්නා)", "ඔ (ඔනා)", "ඕ (ඕවන්නා)", "ඖ (ඖවන්නා)"};
    private String[] vowelsTamil = {"அ", "ஆ", "இ", "ஈ", "உ", "ஊ", "எ", "ஏ", "ஐ", "ஒ", "ஓ", "ஔ"};
    private int[] vowelsImages = {R.drawable.vowel1, R.drawable.vowel2, R.drawable.vowel3, R.drawable.vowel4, R.drawable.vowel5, R.drawable.vowel6, R.drawable.vowel7, R.drawable.vowel8, R.drawable.vowel9, R.drawable.vowel10, R.drawable.vowel11, R.drawable.vowel12};

    private String[] constSinhala = {"ක ග හ (කානා)", "ං (නානා)", "ස ච ඡ (සානා)", "ඤ (ඤානා)", "ඩ ට (ඩානා)", "ත ද (තානා)", "ප බ ෆ (පානා)",
            "ර ට (රානා)", "ය (යානා)", "ර (රානා)", "ල (ලානා)", "ව (වානා)", "ළ (ළානා)", "ළ (ළානා)", "න (නානා)", "න (නානා)", "ම (මානා)", "ණ (ණානා)"};
    private String[] constTamil = {"க", "ங", "ச", "ஞ", "ட", "த", "ப", "ற", "ய", "ர", "ல", "வ", "ழ", "ள", "ன", "ந", "ம", "ண"};
    private int[] constImages = {R.drawable.const1, R.drawable.const2, R.drawable.const3, R.drawable.const4, R.drawable.const5, R.drawable.const6, R.drawable.const7, R.drawable.const8, R.drawable.const9, R.drawable.const10, R.drawable.const11, R.drawable.const12, R.drawable.const13, R.drawable.const14, R.drawable.const15, R.drawable.const16, R.drawable.const17, R.drawable.const18};

    private String[] granthaSinhala = {"ජ", "ශ", "ෂ", "හ", "ක්ෂ"};
    private String[] granthaTamil = {"ஜ", "ஸ", "ஷ", "ஹ", "சஷ"};
    private int[] granthaImages = {R.drawable.grantha1, R.drawable.grantha2, R.drawable.grantha3, R.drawable.grantha4, R.drawable.grantha5};

    private TextView textViewTitle;
    private TextView textViewSinhalaLetter;
    private TextView textViewTamilLetter;
    private ImageView imageView;
    private Button buttonPre;
    private Button buttonNext;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private int vowelProgress;
    private int constProgress;
    private int granthaProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamil_letters_learn);
        getSupportActionBar().hide();

        Context context = TamilLettersLearnActivity.this;
        sharedPreferences = context.getSharedPreferences("progress", context.MODE_PRIVATE);
        Bundle extras = getIntent().getExtras();
        editor =sharedPreferences.edit();

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewSinhalaLetter = findViewById(R.id.textViewSinhalaLetter);
        textViewTamilLetter = findViewById(R.id.question);
        imageView = findViewById(R.id.imageViewLetter);
        buttonPre = findViewById(R.id.buttonPre);
        buttonNext = findViewById(R.id.buttonNext);

        textViewSinhalaLetter.setText(vowelsSinhala[0]);
        textViewTamilLetter.setText(vowelsTamil[0]);
        imageView.setImageDrawable(getResources().getDrawable(vowelsImages[0]));

        vowelProgress = Integer.parseInt(sharedPreferences.getString("LETTER_VOWEL_PROGRESS", "0"));
        constProgress = Integer.parseInt(sharedPreferences.getString("LETTER_CONST_PROGRESS", "0"));
        granthaProgress = Integer.parseInt(sharedPreferences.getString("LETTER_GRANTHA_PROGRESS", "0"));

        String type =extras.getString("lesson");
        if ("vowel".equals(type)){
            textViewTitle.setText("Vowels");
            textViewSinhalaLetter.setText(vowelsSinhala[vowelProgress]);
            textViewTamilLetter.setText(vowelsTamil[vowelProgress]);
            imageView.setImageDrawable(getResources().getDrawable(vowelsImages[vowelProgress]));
            if (vowelProgress + 1 >= vowelsSinhala.length){
                makeToast("Lesson is complete");
                buttonNext.setText("Home");
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TamilLettersLearnActivity.this, TamilLettersCategoryActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }else {
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveProgressLocal("LETTER_VOWEL_PROGRESS", String.valueOf(vowelProgress + 1));
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }
            if (vowelProgress != 0){
                buttonPre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveProgressLocal("LETTER_VOWEL_PROGRESS", String.valueOf(vowelProgress - 1));
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }
        }else if ("constant".equals(type)){
            textViewTitle.setText("Constants");
            textViewSinhalaLetter.setText(constSinhala[constProgress]);
            textViewTamilLetter.setText(constTamil[constProgress]);
            imageView.setImageDrawable(getResources().getDrawable(constImages[constProgress]));
            if (constProgress + 1 >= constSinhala.length){
                makeToast("Lesson is complete");
                buttonNext.setText("Home");
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TamilLettersLearnActivity.this, TamilLettersCategoryActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }else {
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveProgressLocal("LETTER_CONST_PROGRESS", String.valueOf(constProgress + 1));
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }
            if (constProgress != 0){
                buttonPre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveProgressLocal("LETTER_CONST_PROGRESS", String.valueOf(constProgress - 1));
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }
        }else if ("grantha".equals(type)){
            textViewTitle.setText("Grantha");
            textViewSinhalaLetter.setText(granthaSinhala[granthaProgress]);
            textViewTamilLetter.setText(granthaTamil[granthaProgress]);
            imageView.setImageDrawable(getResources().getDrawable(granthaImages[granthaProgress]));
            if (granthaProgress + 1 >= granthaSinhala.length){
                makeToast("Lesson is complete");
                buttonNext.setText("Home");
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TamilLettersLearnActivity.this, TamilLettersCategoryActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }else {
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveProgressLocal("LETTER_GRANTHA_PROGRESS", String.valueOf(granthaProgress + 1));
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }
            if (granthaProgress != 0){
                buttonPre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveProgressLocal("LETTER_GRANTHA_PROGRESS", String.valueOf(granthaProgress - 1));
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }
        }
    }

    private void saveProgressLocal(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    private void makeToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
