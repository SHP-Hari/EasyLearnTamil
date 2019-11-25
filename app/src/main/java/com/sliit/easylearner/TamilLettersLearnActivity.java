package com.sliit.easylearner;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.sliit.easylearner.util.DrawingView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TamilLettersLearnActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewSinhalaLetter;
    private TextView textViewTamilLetter;
    private ImageView imageView;
    private Button buttonPre;
    private Button buttonNext;
    private ImageButton btnClear;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String cat_id;
    private String cat_name;
    int index;
    ProgressDialog progressDialog;
    ArrayList<LearningLetter> mLearningLetters = new ArrayList<>();

    LinearLayout main;
//    LinearLayout mDrawingPad;
//    DrawingView mDrawingView;
    private View view;
    private signature mSignature;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_tamil_letters_learn);

        main = (LinearLayout) findViewById(R.id.layoutMain);
        mSignature = new signature(getApplicationContext(), null);
        main.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        Context context = TamilLettersLearnActivity.this;
        sharedPreferences = context.getSharedPreferences("progress", context.MODE_PRIVATE);
        Bundle extras = getIntent().getExtras();
        editor =sharedPreferences.edit();

        getData();
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewSinhalaLetter = findViewById(R.id.textViewSinhalaLetter);
        textViewTamilLetter = findViewById(R.id.question);
//        imageView = findViewById(R.id.imageViewLetter);
        buttonPre = findViewById(R.id.buttonPre);
        buttonNext = findViewById(R.id.buttonNext);
        btnClear = (ImageButton) findViewById(R.id.buttonClear);

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

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignature.clear();
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
//        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.drawable.tenor);
//
//        Glide.with(this)
//                .load(mLearningLetters.get(index).getLetterImage())
//                .apply(requestOptions)
//                .into(imageView);
        new LoadBackground(mLearningLetters.get(index).getLetterImage(),"androidfigure").execute();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("indexKey", index);
    }

    private void nextButton(){
        mSignature.clear();
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
//        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.drawable.tenor);
//
//        Glide.with(this)
//                .load(mLearningLetters.get(index).getLetterImage())
//                .apply(requestOptions)
//                .into(imageView);
        new LoadBackground(mLearningLetters.get(index).getLetterImage(),"androidfigure").execute();
    }


    private void previestButton(){
        mSignature.clear();
        if (index != 0){
            index = (index-1);
        }

        if (index == 0){
            finish();
        }
        textViewSinhalaLetter.setText(mLearningLetters.get(index).getLetterSinhala());
        textViewTamilLetter.setText(mLearningLetters.get(index).getLetterTamil());
//        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.drawable.tenor);
//
//        Glide.with(this)
//                .load(mLearningLetters.get(index).getLetterImage())
//                .apply(requestOptions)
//                .into(imageView);
        new LoadBackground(mLearningLetters.get(index).getLetterImage(),"androidfigure").execute();
    }

    public class signature extends View {

        private static final float STROKE_WIDTH = 20;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();
        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(0xFFFF0000);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);

        }

        @SuppressLint("WrongThread")
        public void save(View v, String StoredPath) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(main.getWidth(), main.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmap);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();

        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    private class LoadBackground extends AsyncTask<String, Void, Drawable> {

        private String imageUrl , imageName;

        public LoadBackground(String url, String file_name) {
            this.imageUrl = url;
            this.imageName = file_name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSignature.setBackgroundDrawable( getResources().getDrawable(R.drawable.tenor) );
        }

        @Override
        protected Drawable doInBackground(String... urls) {

            try {
                InputStream is = (InputStream) this.fetch(this.imageUrl);
                Drawable d = Drawable.createFromStream(is, this.imageName);
                return d;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        private Object fetch(String address) throws MalformedURLException,IOException {
            URL url = new URL(address);
            Object content = url.getContent();
            return content;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            super.onPostExecute(result);
            mSignature.setBackgroundDrawable(result);
        }
    }
}
