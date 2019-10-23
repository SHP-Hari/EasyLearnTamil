package com.sliit.easylearner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TamilWordsLearnActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextView textViewSubtitle;
    private TextView textViewTamilWord;
    private TextView textViewSinhalaWord;
    private TextView textViewEqual;
    private Button buttonNext;
    private Button buttonPre;
    private final String preference_file_key = "APP_PROGRESS";
    ProgressDialog progressDialog;
    private DatabaseReference userRef;
    String Uid;
    SharedPreferences sharedPref;

    private int nounsProgress = 1;
    private int verbsProgress = 1;
    private int numbersProgress = 1;
    private int datesProgress = 1;
    private int clothesProgress = 1;
    private int animalsProgress = 1;
    private int vegetablesProgress = 1;
    private int fruitsProgress = 1;
    private int colorsProgress = 1;

    private String[] sinhalaWordsNouns = new String[] {"", "අම්මා", "තාත්තා", "නංගී", "මල්ලි", "අයියා", "අක්කා", "සියා", "ආච්චි", "ලොකු  අම්මා",
            "ලොකු  තාත්තා", "බාප්පා", "පුංචි", "මාමා", "නැන්දා", "සොහොයුරා", "සොහොයුරිය ", "ගැහැණිය", "පිරිමියා", "මම", "කොල්ලා", "කෙල්ල", "අපි",
            "ඔහු", "ඇය", "ස්ත්\u200Dරී ලිංග", "පුරුෂ ලිංග"};
    private String[] tamilWordsNouns = new String[] {"", "அம்மா", "தந்தையின்", "சகோதரி", "சகோதரர்", "சகோதரர்", "சகோதரி", "ஜியா",
            "பாட்டி", "பெரிய அம்மா", "பெரிய அப்பா", "மாமா", "சிறிய", "மாமா", "அத்தை", "சகோதரர்", "சகோதரி", "பெண்", "ஆண்", "நான்",
            "பாய்", "பெண்", "நாம்", "அவர்", "அவள்", "பெண் பாலினம்", "ஆண் பாலினம்"};

    private String[] sinhalaWordsVerbs = new String[] {"", "එනවා", "බොනවා", "කනවා", "ගන්නවා", "නානවා", "දුවනවා", "පනිනවා", "කියවනවා",
            "හදනවා", "සෙල්ලම් කරනවා", "නැගිටිනවා", "නිදාගන්නවා", "අතුගානවා", "ඉඳ ගන්නවා", "අස් කරනවා", "නිදියනවා", "ලබා ගන්නවා", "ගේනවා",
            "හිතනවා", "හොරකම්  කරනවා", "කතා කරනවා", "චිත්\u200Dර අඳිනවා", "ඇඳුම් අදිනවා", "ලියනවා", "පීනනවා", "උයනවා", "වාහනය පදිනවා",
            "දකිනවා", "හංගනවා", "බලනවා", "නැගිටිනවා", "දැනගන්නවා", "ඉගෙන ගන්නවා", "ආදරේ කරනවා"};
    private String[] tamilWordsVerbs = new String[] {"", "வருகிறேன்", "குடிக்கிறேன்", "சாப்பிடுகிறேன்", "எடுக்கிறேன்", "குளிக்கிறேன்", "ஓடுகிறேன்",
            "பாய்கிறேன்", "வாசிக்கிறேன்", "தயாரிக்கிறேன்", "விளையாடுகிறேன்", "எழும்புகிறேன்", "தூங்குகின்றேன்", "சுத்தம் செய்கிறேன்",
            "அமர்கிறேன்", "சுத்தம் செய்கிறேன்", "தூங்க போகிறேன்", "கொள்வனவு செய்கிறேன்", "கொண்டு வருகிறேன்", "யோசிக்கிறேன்",
            "திருடுகிறேன்", "பேசுகிறார்", "வரைகிறேன்", "துணிகளை இழுப்பது", "எழுதுகின்றேன்", "நீச்சல்", "சமைகின்றேன்", "வாகனத்தை ஒட்டுகின்றேன்",
            "பார்க்கிறேன்", "மறைப்பது", "பார்க்கிறேன்", "எழும்புகின்றேன்", "அறிய", "படிக்கிறேன்", "நேசிக்கின்றேன்"};

    private String[] sinhalaWordsNumbers = new String[] {"", "එක", "දෙක", "තුන", "හතර", "පහ", "හය", "හත", "අට", "නවය", "දහය", "විස්ස",
            "විසි පහ", "පනහ ", "හැත්තෑ පහ", "සිය", "දෙසිය", "තුන්සිය", "හාරසිය", "පන්සියය", "දහස", "මිලියනය", "පළවෙනි", "දෙවෙනි", "තුන්වෙනි",
            "හතරවෙනි", "පස් වෙනි", "වරක්", "දෙවරක්", "තුන්වරක්"};
    private String[] tamilWordsNumbers = new String[] {"", "ஒன்று", "இரண்டு", "மூன்று", "நான்கு", "ஐந்து", "ஆறு", "ஏழு", "எட்டு", "ஒன்பது",
            "பத்து", "இருபது", "இருபத்தைந்து", "ஐம்பது", "எழுபத்தைந்து", "நூறு", "இருநூறு", "முன்னூறு", "நானூறு", "ஐந்நூறு", "ஆயிரம்",
            "மில்லியன்", "முதல்", "இரண்டாவது", "மூன்றாம்", "நான்காம்", "ஐந்தாவது", "ஒருமுறை", "இருமுறை", "மூன்று முறை"};

    private String[] sinhalaWordsDates = new String[] {"", "සඳුදා", "අඟහරුවාදා", "බදාදා", "බ්\u200Dරහස්පතින්දා", "සිකුරාදා", "සෙනසුරාදා", "ඉරිදා",
            "ජනවාරි", "පෙබරවාරි", "මාර්තු", "අප්\u200Dරේල්", "මැයි", "ජූනි", "ජූලි", "අගෝස්තු", "සැප්තැම්බර්", "ඔක්තෝම්බර්", "නොවැම්බර්", "දෙසැම්බර්",
            "මාසය", "දවස", "වේලාව", "තප්පරය", "විනාඩිය", "පැය", "සීත සෘතුව", "වසන්ත සෘතුව", "ගිම්හාන සෘතුව", "හෙට", "ඊයේ", "පෙරේදා", "අනිත් දා"};
    private String[] tamilWordsDates = new String[] {"", "திங்கள்", "செவ்வாய்க்கிழமை", "புதன்கிழமை", "வியாழக்கிழமை", "வெள்ளிக்கிழமை",
            "சனிக்கிழமை", "ஞாயிறு", "ஜனவரி", "பிப்ரவரி", "மார்ச்", "ஏப்ரல்", "மே", "ஜூன்", "ஜூலை", "ஆகஸ்ட்", "செப்டம்பர்", "அக்டோபர்",
            "நவம்பர்", "டிசம்பர்", "மாதம்", "தினம்", "நேரம்", "முற்றிலும்", "நிமிடம்", "மணி", "குளிர்காலம்", "வசந்த காலம்", "கோடைகால சங்கிராந்தி",
            "நாளை", "நேற்று", "சனிக்கிழமை", "மற்ற நாள்"};

    private String[] sinhalaWordsClothes = new String[] {"", "ඉන පටිය", "කබාය", "කන්නාඩිය", "තොප්පිය", "කමිසය", "මුද්ද ", "කලිසම", "සෙරෙප්පුව", "ඇඳුම්",
            "රෙදි"};

    private String[] tamilWordsClothes = new String[] {"", "இடுப்புப் பட்டை", "சின்னம்", "கண்ணாடி", "தொப்பி", "சட்டை", "ரிங்", "காட்சட்டை",
            "செருப்பு", "ஆடைகள்"};

    private String[] sinhalaWordsAnimals = new String[] {"", "සිංහයා", "මාළුවා", "කුකුලා", "පූසා", "අලියා", "මදුරුවා", "හාවා", "මකුළුවා", "කැරපොත්තා", "බූරුවා",
            "අශ්වයා"};

    private String[] tamilWordsAnimals = new String[] {"", "சிங்கம்", "மீன்", "இளஞ்சேவலின்", "பூனை", "யானை", "கொசு", "முயல்", "சிலந்தி",
            "கரப்பான் பூச்சி", "கழுதை", "குதிரை"};

    private String[] sinhalaWordsVegetables = new String[] {"", "අර්තාපල්", "ගෝවා", "කැරට්", "රතු අල", "බෝංචි"};

    private String[] tamilWordsVegetables = new String[] {"", "உருளைக்கிழங்குகள்", "முட்டைக்கோஸ்", "கேரட்", "சிவப்பு உருளைக்கிழங்கு", "பீன்ஸ்"};

    private String[] sinhalaWordsFruits = new String[] {"", "අඹ", "කොමඩු", "අන්නාසි", "මිදි"};

    private String[] tamilWordsFruits = new String[] {"", "மாம்பழ", "முலாம்பழம்", "அன்னாசி", "திராட்சை"};

    private String[] sinhalaWordsColors = new String[] {"", "රතු", "කොළ", "දම්", "කහ", "තැඹිලි"};

    private String[] tamilWordsColors = new String[] {"", "சிவப்பு", "பச்சை", "ஊதா", "மஞ்சள்", "ஆரஞ்சு"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamil_words_learn);
        getSupportActionBar().hide();

        Context context = TamilWordsLearnActivity.this;
        sharedPref = context.getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = sharedPref.edit();

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        Uid = mAuth.getCurrentUser().getUid();


        Bundle extras = getIntent().getExtras();
        textViewSubtitle = findViewById(R.id.subtitle);
        textViewTamilWord = findViewById(R.id.tamilWord);
        textViewSinhalaWord = findViewById(R.id.sinhalaWord);
        textViewEqual = findViewById(R.id.textViewEqual);
        buttonNext = findViewById(R.id.buttonNextWord);
        buttonPre = findViewById(R.id.buttonPreWord);

        String wordType = extras.getString("type");
        if ("noun".equals(wordType)){
            nounsProgress = Integer.parseInt(sharedPref.getString("WORD_NOUN_PROGRESS", "1"));
            if (nounsProgress >= sinhalaWordsNouns.length){
                textViewSubtitle.setText("Nouns");
                textViewTamilWord.setText("");
                textViewEqual.setText("");
                textViewSinhalaWord.setText("Nouns Lesson Finished");
                buttonNext.setText("Home");
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putString("WORD_NOUN_PROGRESS", "1");
                        editor.commit();
                        Intent intent = new Intent(TamilWordsLearnActivity.this, TamilWordsLearnCategories.class);
                        finish();
                        startActivity(intent);
                    }
                });
            }else {
                textViewSubtitle.setText("Nouns Lesson - " + nounsProgress);
                textViewTamilWord.setText(tamilWordsNouns[nounsProgress]);

                textViewSinhalaWord.setText(sinhalaWordsNouns[nounsProgress]);
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nounsProgress++;
                        editor.putString("WORD_NOUN_PROGRESS", String.valueOf(nounsProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }

            buttonPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (nounsProgress > 1){
                        nounsProgress--;
                        editor.putString("WORD_NOUN_PROGRESS", String.valueOf(nounsProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
            });
        }else if ("verb".equals(wordType)){
            verbsProgress = Integer.parseInt(sharedPref.getString("WORD_VERB_PROGRESS", "1"));
            textViewSubtitle.setText("Verbs Lesson - " + verbsProgress);
            if (verbsProgress >= sinhalaWordsVerbs.length){
                textViewTamilWord.setText("");
                textViewEqual.setText("");
                textViewSinhalaWord.setText("Verbs Lesson Finished");
                buttonNext.setText("Home");
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putString("WORD_VERB_PROGRESS", "1");
                        editor.commit();
                        Intent intent = new Intent(TamilWordsLearnActivity.this, TamilWordsLearnCategories.class);
                        finish();
                        startActivity(intent);
                    }
                });
            }else {
                textViewTamilWord.setText(tamilWordsVerbs[verbsProgress]);

                textViewSinhalaWord.setText(sinhalaWordsVerbs[verbsProgress]);
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        verbsProgress++;
                        editor.putString("WORD_VERB_PROGRESS", String.valueOf(verbsProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }

            buttonPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (verbsProgress > 1){
                        verbsProgress--;
                        editor.putString("WORD_VERB_PROGRESS", String.valueOf(verbsProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
            });
        }else if ("number".equals(wordType)){
            numbersProgress = Integer.parseInt(sharedPref.getString("WORD_NUMBER_PROGRESS", "1"));
            textViewSubtitle.setText("Numbers Lesson - " + numbersProgress);
            if (numbersProgress >= sinhalaWordsNumbers.length){
                textViewTamilWord.setText("");
                textViewEqual.setText("");
                textViewSinhalaWord.setText("Numbers Lesson Finished");
                buttonNext.setText("Home");
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putString("WORD_NUMBER_PROGRESS", "1");
                        editor.commit();
                        Intent intent = new Intent(TamilWordsLearnActivity.this, TamilWordsLearnCategories.class);
                        finish();
                        startActivity(intent);
                    }
                });
            }else {
                textViewTamilWord.setText(tamilWordsNumbers[numbersProgress]);

                textViewSinhalaWord.setText(sinhalaWordsNumbers[numbersProgress]);
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numbersProgress++;
                        editor.putString("WORD_NUMBER_PROGRESS", String.valueOf(numbersProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }

            buttonPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (numbersProgress > 1){
                        numbersProgress--;
                        editor.putString("WORD_NUMBER_PROGRESS", String.valueOf(numbersProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
            });
        }else if ("datetime".equals(wordType)){
            datesProgress = Integer.parseInt(sharedPref.getString("WORD_DATE_PROGRESS", "1"));
            textViewSubtitle.setText("Date and Time Lesson - " + datesProgress);
            if (datesProgress >= sinhalaWordsNumbers.length){
                textViewTamilWord.setText("");
                textViewEqual.setText("");
                textViewSinhalaWord.setText("Date and Time Lesson Finished");
                buttonNext.setText("Home");
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putString("WORD_DATE_PROGRESS", "1");
                        editor.commit();
                        Intent intent = new Intent(TamilWordsLearnActivity.this, TamilWordsLearnCategories.class);
                        finish();
                        startActivity(intent);
                    }
                });
            }else {
                textViewTamilWord.setText(tamilWordsDates[datesProgress]);

                textViewSinhalaWord.setText(sinhalaWordsDates[datesProgress]);
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datesProgress++;
                        editor.putString("WORD_DATE_PROGRESS", String.valueOf(datesProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }

            buttonPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (datesProgress > 1){
                        datesProgress--;
                        editor.putString("WORD_DATE_PROGRESS", String.valueOf(datesProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
            });
        }else if ("clothes".equals(wordType)){
            clothesProgress = Integer.parseInt(sharedPref.getString("WORD_CLOTHES_PROGRESS", "1"));
            textViewSubtitle.setText("Clothes Lesson - " + clothesProgress);
            if (clothesProgress >= sinhalaWordsClothes.length){
                textViewTamilWord.setText("");
                textViewEqual.setText("");
                textViewSinhalaWord.setText("Clothes Lesson Finished");
                buttonNext.setText("Home");
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putString("WORD_CLOTHES_PROGRESS", "1");
                        editor.commit();
                        Intent intent = new Intent(TamilWordsLearnActivity.this, TamilWordsLearnCategories.class);
                        finish();
                        startActivity(intent);
                    }
                });
            }else {
                textViewTamilWord.setText(tamilWordsClothes[clothesProgress]);

                textViewSinhalaWord.setText(sinhalaWordsClothes[clothesProgress]);
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clothesProgress++;
                        editor.putString("WORD_CLOTHES_PROGRESS", String.valueOf(clothesProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }

            buttonPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clothesProgress > 1){
                        clothesProgress--;
                        editor.putString("WORD_CLOTHES_PROGRESS", String.valueOf(clothesProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
            });
        }else if ("animals".equals(wordType)){
            animalsProgress = Integer.parseInt(sharedPref.getString("WORD_ANIMALS_PROGRESS", "1"));
            textViewSubtitle.setText("Animals Lesson - " + animalsProgress);
            if (animalsProgress >= sinhalaWordsAnimals.length){
                textViewTamilWord.setText("");
                textViewEqual.setText("");
                textViewSinhalaWord.setText("Animals Lesson Finished");
                buttonNext.setText("Home");
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putString("WORD_CLOTHES_PROGRESS", "1");
                        editor.commit();
                        Intent intent = new Intent(TamilWordsLearnActivity.this, TamilWordsLearnCategories.class);
                        finish();
                        startActivity(intent);
                    }
                });
            }else {
                textViewTamilWord.setText(tamilWordsAnimals[animalsProgress]);

                textViewSinhalaWord.setText(sinhalaWordsAnimals[animalsProgress]);
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        animalsProgress++;
                        editor.putString("WORD_ANIMALS_PROGRESS", String.valueOf(animalsProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }

            buttonPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (animalsProgress > 1){
                        animalsProgress--;
                        editor.putString("WORD_ANIMALS_PROGRESS", String.valueOf(animalsProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
            });
        }else if ("vegetables".equals(wordType)){
            vegetablesProgress = Integer.parseInt(sharedPref.getString("WORD_VEGETABLES_PROGRESS", "1"));
            textViewSubtitle.setText("Vegetables Lesson - " + vegetablesProgress);
            if (vegetablesProgress >= sinhalaWordsVegetables.length){
                textViewTamilWord.setText("");
                textViewEqual.setText("");
                textViewSinhalaWord.setText("Vegetables Lesson Finished");
                buttonNext.setText("Home");
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putString("WORD_VEGETABLES_PROGRESS", "1");
                        editor.commit();
                        Intent intent = new Intent(TamilWordsLearnActivity.this, TamilWordsLearnCategories.class);
                        finish();
                        startActivity(intent);
                    }
                });
            }else {
                textViewTamilWord.setText(tamilWordsVegetables[vegetablesProgress]);

                textViewSinhalaWord.setText(sinhalaWordsVegetables[vegetablesProgress]);
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vegetablesProgress++;
                        editor.putString("WORD_VEGETABLES_PROGRESS", String.valueOf(vegetablesProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }

            buttonPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (vegetablesProgress > 1){
                        vegetablesProgress--;
                        editor.putString("WORD_VEGETABLES_PROGRESS", String.valueOf(vegetablesProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
            });
        }else if ("fruits".equals(wordType)){
            fruitsProgress = Integer.parseInt(sharedPref.getString("WORD_FRUITS_PROGRESS", "1"));
            textViewSubtitle.setText("Fruits Lesson - " + fruitsProgress);
            if (fruitsProgress >= sinhalaWordsFruits.length){
                textViewTamilWord.setText("");
                textViewEqual.setText("");
                textViewSinhalaWord.setText("Fruits Lesson Finished");
                buttonNext.setText("Home");
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putString("WORD_FRUITS_PROGRESS", "1");
                        editor.commit();
                        Intent intent = new Intent(TamilWordsLearnActivity.this, TamilWordsLearnCategories.class);
                        finish();
                        startActivity(intent);
                    }
                });
            }else {
                textViewTamilWord.setText(tamilWordsFruits[fruitsProgress]);

                textViewSinhalaWord.setText(sinhalaWordsFruits[fruitsProgress]);
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fruitsProgress++;
                        editor.putString("WORD_FRUITS_PROGRESS", String.valueOf(fruitsProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }

            buttonPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fruitsProgress > 1){
                        fruitsProgress--;
                        editor.putString("WORD_FRUITS_PROGRESS", String.valueOf(fruitsProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
            });
        }else if ("colors".equals(wordType)){
            colorsProgress = Integer.parseInt(sharedPref.getString("WORD_COLORS_PROGRESS", "1"));
            textViewSubtitle.setText("Colors Lesson - " + colorsProgress);
            if (colorsProgress >= sinhalaWordsColors.length){
                textViewTamilWord.setText("");
                textViewEqual.setText("");
                textViewSinhalaWord.setText("Colors Lesson Finished");
                buttonNext.setText("Home");
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putString("WORD_COLORS_PROGRESS", "1");
                        editor.commit();
                        Intent intent = new Intent(TamilWordsLearnActivity.this, TamilWordsLearnCategories.class);
                        finish();
                        startActivity(intent);
                    }
                });
            }else {
                textViewTamilWord.setText(tamilWordsColors[colorsProgress]);

                textViewSinhalaWord.setText(sinhalaWordsColors[colorsProgress]);
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        colorsProgress++;
                        editor.putString("WORD_COLORS_PROGRESS", String.valueOf(colorsProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
            }

            buttonPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (colorsProgress > 1){
                        colorsProgress--;
                        editor.putString("WORD_COLORS_PROGRESS", String.valueOf(colorsProgress));
                        editor.commit();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
            });
        }

        saveProgress();



    }

    private void saveProgress(){
        userRef= FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);
        progressDialog.setMessage("Updating Profile...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        HashMap usermap=new HashMap();
        usermap.put("Nouns_lesson_progress", Integer.parseInt(sharedPref.getString("WORD_NOUN_PROGRESS", "1")));
        usermap.put("Verbs_lesson_progress", Integer.parseInt(sharedPref.getString("WORD_VERB_PROGRESS", "1")));
        usermap.put("Numbers_lesson_progress", Integer.parseInt(sharedPref.getString("WORD_NUMBER_PROGRESS", "1")));
        usermap.put("Clothes_lesson_progress", Integer.parseInt(sharedPref.getString("WORD_CLOTHES_PROGRESS", "1")));
        usermap.put("Animals_lesson_progress", Integer.parseInt(sharedPref.getString("WORD_ANIMALS_PROGRESS", "1")));
        usermap.put("Vegetables_lesson_progress", Integer.parseInt(sharedPref.getString("WORD_VEGETABLES_PROGRESS", "1")));
        usermap.put("Fruits_lesson_progress", Integer.parseInt(sharedPref.getString("WORD_FRUITS_PROGRESS", "1")));
        usermap.put("Colors_lesson_progress", Integer.parseInt(sharedPref.getString("WORD_COLORS_PROGRESS", "1")));

        userRef.updateChildren(usermap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if(task.isSuccessful())
                {

                    progressDialog.dismiss();

                }
                else
                {
                    progressDialog.dismiss();
                    makeToast("Check your internet connection");
                }


            }

        });
    }

    private void loadProgress(){
        Uid = currentUser.getUid();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    nounsProgress = Integer.parseInt(String.valueOf(dataSnapshot.child("Nouns_lesson_progress").getValue()));
                    verbsProgress = Integer.parseInt(String.valueOf(dataSnapshot.child("Verbs_lesson_progress").getValue()));
                    numbersProgress = Integer.parseInt(String.valueOf(dataSnapshot.child("Numbers_lesson_progress").getValue()));
                    datesProgress = Integer.parseInt(String.valueOf(dataSnapshot.child("Dates_lesson_progress").getValue()));
                    clothesProgress = Integer.parseInt(String.valueOf(dataSnapshot.child("Clothes_lesson_progress").getValue()));
                    animalsProgress = Integer.parseInt(String.valueOf(dataSnapshot.child("Animals_lesson_progress").getValue()));
                    vegetablesProgress = Integer.parseInt(String.valueOf(dataSnapshot.child("Vegetables_lesson_progress").getValue()));
                    fruitsProgress = Integer.parseInt(String.valueOf(dataSnapshot.child("Fruits_lesson_progress").getValue()));
                    colorsProgress = Integer.parseInt(String.valueOf(dataSnapshot.child("Colors_lesson_progress").getValue()));
                }else {
                    makeToast("error");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("TAG1234", "Failed to read value.", error.toException());
            }
        });
    }

    private void makeToast(String msg){
        Toast.makeText(TamilWordsLearnActivity.this,msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        currentUser = mAuth.getCurrentUser();
    }

}
