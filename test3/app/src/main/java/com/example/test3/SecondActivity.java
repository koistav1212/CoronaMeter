package com.example.test3;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
ImageView mainimageView;
TextView title,describtion;

int img;
String data1,data2;

    @Override
    protected void onCreate(Bundle SavedInstances)
    {
        super.onCreate(SavedInstances);
        setContentView(R.layout.activity_second);
       mainimageView=findViewById( R.id.mainimageView);
       title=findViewById(R.id.text1);
       describtion =findViewById(R.id.text2);
  getData();
  setData();
    }
    private void getData()
    {
        if(getIntent().hasExtra("myImage")&& getIntent().hasExtra("data2") && getIntent().hasExtra("data1"))
        {
              data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            img = getIntent().getIntExtra("myImage",1);

        }
        else
        {
            Toast.makeText(this,"no Data.",Toast.LENGTH_LONG).show();
        }
    }
    private void setData()
    {
        title.setText(data1);
        describtion.setText(data2);
        mainimageView.setImageResource(img);

    }
}

