package com.sliit.easylearner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sliit.easylearner.util.DrawingView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DrawImage extends AppCompatActivity {

    ImageView mImageView;
    public Canvas canvas;
    public Paint paint;
    public Path path;
    public Bitmap bitmap;
    public Float  x1;
    public Float  y1;
    public Integer w;
    public Integer h;
    LinearLayout main;
    DrawingView mDrawingView;
    LinearLayout mDrawingPad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawingView=new DrawingView(this);
        setContentView(R.layout.activity_draw_image);
        mDrawingPad=(LinearLayout)findViewById(R.id.view_drawing_pad);
        mDrawingPad.addView(mDrawingView);

        main = (LinearLayout) findViewById(R.id.layoutMain);
        new LoadBackground("https://easylearntamil.000webhostapp.com/upload/const/const1.png",
                "androidfigure").execute();
//        mImageView = (ImageView) findViewById(R.id.choosenImageView);
//        Object obj = this.getSystemService(Context.WINDOW_SERVICE);
//        WindowManager wm = (WindowManager)obj;
//        Display disp = wm.getDefaultDisplay();

//        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.drawable.tenor);
//        Glide.with(this)
//                .load("https://easylearntamil.000webhostapp.com/upload/const/const1.png")
//                .apply(requestOptions)
//                .into(mImageView);


    }

    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cle(View view) {
        mDrawingView.clear();
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
            main.setBackgroundDrawable(result);
        }
    }
}
