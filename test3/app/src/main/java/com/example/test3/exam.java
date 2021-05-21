package com.example.test3;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class exam extends AppCompatActivity{
    RecyclerView recyclerView;
    String s1[],s2[];
    int images[]={R.drawable.c_plus,R.drawable.c_hash,R.drawable.java1,R.drawable.java1,R.drawable.c_hash,R.drawable.ruby,
            R.drawable.ruby,R.drawable.ruby,R.drawable.java1,
    R.drawable.vc};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_exam);
recyclerView =findViewById(R.id.recyclerView);
s1=getResources().getStringArray(R.array.programming_name);
s2=getResources().getStringArray(R.array.description);
MyAdapter myAdapter= new MyAdapter(this,s1,s2,images);
recyclerView.setAdapter(myAdapter);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
