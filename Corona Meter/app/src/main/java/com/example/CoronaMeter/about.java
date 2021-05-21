package com.example.CoronaMeter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class about extends Fragment {


    public about() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about, container, false);
        ImageView share=view.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap mBitmap = BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(),
                        R.drawable.speedometer);
                String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), mBitmap, "Image I want to share", null);
                Uri uri = Uri.parse(path);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT,"Corona Meter");
                sendIntent.putExtra(Intent.EXTRA_STREAM,uri);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Download Corona Meter and get precise data on covid and health news of your State and District.                "+"https://drive.google.com/file/d/1TddTPwZPvLKtOuT9tjUM-VQeFvk3oGTZ/view?usp=sharing");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, "Hlw");
                startActivity(shareIntent);
            }
        });
        return view;
    }
}