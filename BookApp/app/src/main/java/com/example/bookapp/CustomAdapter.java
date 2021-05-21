package com.example.bookapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
private  Context context;
Activity activity;
Animation animation;
private ArrayList book_id,book_author,book_title,book_pages;
CustomAdapter(Activity activity, Context context,ArrayList book_id,ArrayList book_title,ArrayList book_author,ArrayList book_pages)
{
    this.context=context;
    this.activity=activity;
    this.book_id=book_id;
    this.book_title=book_title;
    this.book_author=book_author;
    this.book_pages=book_pages;

}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.my_row,parent,false);
    return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

holder.book_id_txt.setText(String.valueOf(book_id.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));

        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_pages_txt.setText(String.valueOf(book_pages.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(context, UpdateActivity.class);
                   intent.putExtra("id", String.valueOf(book_id.get(position)));
                   intent.putExtra("title", String.valueOf(book_title.get(position)));
                   intent.putExtra("author", String.valueOf(book_author.get(position)));
                   intent.putExtra("pages", String.valueOf(book_pages.get(position)));
                   activity.startActivityForResult(intent, 1);
               }
           });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView book_id_txt,book_author_txt,book_title_txt,book_pages_txt;
    LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt=itemView.findViewById(R.id.book_id_txt);
            book_author_txt=itemView.findViewById(R.id.book_author_txt);
            book_title_txt=itemView.findViewById(R.id.book_title_txt);

            book_pages_txt=itemView.findViewById(R.id.book_pages_txt);
           mainLayout=itemView.findViewById(R.id.mainLayout);
            animation= AnimationUtils.loadAnimation(context,R.anim.translate_anim);
            mainLayout.setAnimation(animation);

        }
    }
}
