package com.sliit.easylearner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sliit.easylearner.adapter.LetterAdapter;
import com.sliit.easylearner.adapter.RecyclerViewAdapter;
import com.sliit.easylearner.model.LetterCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TamilLettersCategoryActivity extends AppCompatActivity {

    private ArrayList<LetterCategory> letterCategories = new ArrayList<>();
    ProgressDialog progressDialog;
    private static final String URL_LETTER_CATEGORY = "https://easylearntamil.000webhostapp.com/letter-categories";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_tamil_letters_category);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait While Loading...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        loadLetterCategories();

    }

    private void loadLetterCategories() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_LETTER_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.cancel();
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("success");
                            if (error){
                                JSONArray jsonArray = jsonObject.getJSONArray("categories");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject category = jsonArray.getJSONObject(i);
                                    letterCategories.add(new LetterCategory(category.getInt("id"),
                                            category.getString("categoryName"),
                                            category.getString("image"),
                                            category.getInt("status"))
                                    );
                                }
                                initializeRecycler();
                            }else {
                                Toast.makeText(TamilLettersCategoryActivity.this, "Error Data", Toast.LENGTH_SHORT).show();
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

    private void initializeRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        LetterAdapter adapter = new LetterAdapter(this, letterCategories);
        recyclerView.setAdapter(adapter);
    }
}
