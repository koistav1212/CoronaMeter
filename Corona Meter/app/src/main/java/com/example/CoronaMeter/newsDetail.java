package com.example.CoronaMeter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class newsDetail extends Fragment {
Integer positionCountry;
ImageView newsIcon;
TextView headline,source,content;
Button letsgo;
    public newsDetail() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View view= inflater.inflate(R.layout.fragment_news_detail, container, false);
        positionCountry=Integer.valueOf(getArguments().getString("positionCountry"));
        headline=view.findViewById(R.id.headline);
        source=view.findViewById(R.id.source);
        newsIcon=view.findViewById(R.id.newsIcon);
        content=view.findViewById(R.id.content);
        letsgo=view.findViewById(R.id.letsgo);
        headline.setText(news.newsModelist.get(positionCountry).getHeadline());
        Glide.with(getActivity()).load(news.newsModelist.get(positionCountry).getImgurl()).into(newsIcon);
        String content1=news.newsModelist.get(positionCountry).getContent().replaceAll("\r","");
        content1=news.newsModelist.get(positionCountry).getContent().replaceAll("\r\n","");
        String content2=content1;
        content2=content1.replaceAll("<ul><li>","");
        String content3;
        content3=content2.replaceAll("</li><li>","");
       String content4=content3.replaceAll("</li>","");
        String content5=content4.replaceAll("<li>","");
        content.setText(content5);
        source.setText("Source : "+news.newsModelist.get(positionCountry).getSource());
        letsgo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent Getintent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.newsModelist.get(positionCountry).getDescription()));
               getActivity().startActivity(Getintent);

            }
        });
        return view;
    }
}