package com.sliit.easylearner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sliit.easylearner.LearnWordsByCategoryActivity;
import com.sliit.easylearner.R;
import com.sliit.easylearner.model.WordCategory;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<WordCategory> wordCategoryArrayList = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<WordCategory> wordCategories){
        this.mContext = context;
        this.wordCategoryArrayList = wordCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolderCalled");

        Glide.with(mContext)
                .asBitmap()
                .load(wordCategoryArrayList.get(position).getCategoryImage())
                .into(holder.image);

        holder.imageName.setText(wordCategoryArrayList.get(position).getCategoryName());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "OnClick : ClickOn "+wordCategoryArrayList.get(position).getCategoryName());
                Intent intent = new Intent(mContext, LearnWordsByCategoryActivity.class);
                intent.putExtra("categoryName", wordCategoryArrayList.get(position).getCategoryName());
                intent.putExtra("categoryId", wordCategoryArrayList.get(position).getCategoryId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wordCategoryArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parentView);
        }
    }
}
