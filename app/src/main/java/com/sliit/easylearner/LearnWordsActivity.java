package com.sliit.easylearner;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sliit.easylearner.adapter.RecyclerViewAdapter;
import com.sliit.easylearner.model.McqQuestion;
import com.sliit.easylearner.model.WordCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LearnWordsActivity extends AppCompatActivity {

    private ArrayList<WordCategory> wordCategories = new ArrayList<>();
    ProgressDialog progressDialog;
    private static final String URL_CATEGORY = "https://easylearntamil.000webhostapp.com/categories";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_learn_words);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait While Loading...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        loadCategories();
    }

    private void loadCategories() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CATEGORY,
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
                                    wordCategories.add(new WordCategory(category.getString("id"),
                                            category.getString("categoryName"),
                                            category.getString("image"))
                                    );
                                }
                                initializeRecycler();
                            }else {
                                Toast.makeText(LearnWordsActivity.this, "Error Data", Toast.LENGTH_SHORT).show();
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
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, wordCategories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
