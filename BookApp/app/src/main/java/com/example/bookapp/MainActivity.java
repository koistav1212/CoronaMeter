package com.example.bookapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  RecyclerView recyclerView;
  FloatingActionButton add_Button;
  MyDatabaseHelper myDB;
  ArrayList<String> book_id,book_title,book_author,book_pages;
  ImageView empty_imageview;
  TextView no_data;
  CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_Button=findViewById(R.id.add_Button);
        recyclerView=findViewById(R.id.recyclerView);
        empty_imageview=findViewById(R.id.empty_imageView);
        no_data=findViewById(R.id.no_data);
        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,addActivity.class);
                startActivity(intent);
            }
        });
        myDB=new MyDatabaseHelper(MainActivity.this);
        book_author=new ArrayList<>();
        book_id=new ArrayList<>();
        book_pages=new ArrayList<>();
        book_title=new ArrayList<>();
     storeDatainarrays();

     customAdapter=new CustomAdapter(MainActivity.this,this,book_id,book_title,book_author,book_pages);
     recyclerView.setAdapter(customAdapter);
     recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            recreate();
        }
    }

    void storeDatainarrays()
     {
         Cursor cursor=myDB.readAlldata();
         if(cursor.getCount()==0)
         {
             empty_imageview.setVisibility(View.VISIBLE);
             no_data.setVisibility(View.VISIBLE);
         }
         else
         {
             while(cursor.moveToNext())
             {
                 book_id.add(cursor.getString(0));
                 book_title.add(cursor.getString(1));
                 book_author.add(cursor.getString(2));
                 book_pages.add(cursor.getString(3));

             }
             empty_imageview.setVisibility(View.GONE);
             no_data.setVisibility(View.GONE);
         }
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.delete_all)
        {
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            MyDatabaseHelper myDB=new MyDatabaseHelper(this);
            myDB.deleteAllData();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}