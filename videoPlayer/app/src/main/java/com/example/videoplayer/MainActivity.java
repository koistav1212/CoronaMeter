package com.example.videoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    RecyclerView myRecyclerView;
    MyAdapter obj_adapter;
    public static int REQUEST_PERMISSION = 1;
    File directory;
    boolean bolean_permission;
    public static ArrayList<File> fileArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRecyclerView = (RecyclerView)findViewById(R.id.listViewRecycler);

        //Phone Memory And SD Card

        //SD Card
        directory = new File("/mnt/");

        GridLayoutManager manager = new GridLayoutManager(MainActivity.this,2);
        myRecyclerView.setLayoutManager(manager);

        permissionForVideo();
    }

    private void permissionForVideo() {

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){


            if((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))){
            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION);
            }

        }
        else{
            bolean_permission = true;
            getFile(directory);
            obj_adapter = new MyAdapter(getApplicationContext(),fileArrayList);
            myRecyclerView.setAdapter(obj_adapter);

        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_PERMISSION){

            if(grantResults.length>0 && grantResults[0]  == PackageManager.PERMISSION_GRANTED){

                bolean_permission = true;
                getFile(directory);
                obj_adapter = new MyAdapter(getApplicationContext(),fileArrayList);
                myRecyclerView.setAdapter(obj_adapter);

            }
            else{
                Toast.makeText(this, "Please Allow the Permission", Toast.LENGTH_SHORT).show();
            }



        }
    }

    public ArrayList<File> getFile(File directory){

        File[] files;
        files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".mp4") ||
                        file.getName().endsWith(".gif")) {
                    if (!fileArrayList.contains(file)) fileArrayList.add(file);

                }
            }
        }


        return fileArrayList;
    }
}