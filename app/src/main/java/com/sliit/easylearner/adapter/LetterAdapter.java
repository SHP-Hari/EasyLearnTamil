package com.sliit.easylearner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sliit.easylearner.LearnWordsByCategoryActivity;
import com.sliit.easylearner.R;
import com.sliit.easylearner.TamilLettersLearnActivity;
import com.sliit.easylearner.model.LetterCategory;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.ViewHolder> {

    private ArrayList<LetterCategory> letterCategoryArrayList = new ArrayList<>();
    private Context mContext;

    public LetterAdapter(Context context, ArrayList<LetterCategory> mLetterArrayList){
        this.mContext = context;
        letterCategoryArrayList = mLetterArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_letter_listitem, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(mContext)
                .load(letterCategoryArrayList.get(position).getCategoryImage())
                .apply(requestOptions)
                .into(holder.image);

        holder.name.setText(letterCategoryArrayList.get(position).getCategoryName());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick: clicked on an image: " + letterCategoryArrayList.get(position).getId());
                Intent intent = new Intent(mContext, TamilLettersLearnActivity.class);
                intent.putExtra("categoryName", letterCategoryArrayList.get(position).getCategoryName());
                intent.putExtra("categoryId", String.valueOf(letterCategoryArrayList.get(position).getId()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return letterCategoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
        }
    }
}
