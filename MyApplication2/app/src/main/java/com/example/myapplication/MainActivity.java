package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.captaindroid.tvg.Tvg;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle togglebar;

    private MainActivity adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
 togglebar = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
 drawerLayout.addDrawerListener(togglebar);
 togglebar.syncState();
 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
 //ading icon and name tp action bar
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher_foreground);
navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        ImageSlider imageSlider=findViewById(R.id.slider);

        Uri uri1 = Uri.parse("android.resource://com.example.myapplication/drawable/services12");
        Uri uri2 = Uri.parse("android.resource://com.example.myapplication/drawable/services1");
        Uri uri3 = Uri.parse("android.resource://com.example.myapplication/drawable/services12");
        Uri uri4 = Uri.parse("android.resource://com.example.myapplication/drawable/services11");
        Uri uri5 = Uri.parse("android.resource://com.example.myapplication/drawable/services13");

        List<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(uri1.toString(),"Image 1"));
        slideModels.add(new SlideModel(uri2.toString(),"Image 2"));
        slideModels.add(new SlideModel(uri3.toString(),"Image 3"));
        slideModels.add(new SlideModel(uri4.toString(),"Image 4"));
        slideModels.add(new SlideModel(uri5.toString(),"Image 5"));
        imageSlider.setImageList(slideModels,true);
        //text color
        TextView companyheading1 = findViewById(R.id.company_heading1);
        Tvg.change(companyheading1, Color.parseColor("#ffe838"),  Color.parseColor("#fd57bf"));
        TextView companyheading2 = findViewById(R.id.company_heading2);
        Tvg.change(companyheading2, Color.parseColor("#ffe838"),  Color.parseColor("#fd57bf"));
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.home) {
            // Handle the camera action

        } else if (id == R.id.services) {
            Intent intent=new Intent(getApplication(),Services.class);
            startActivity(intent);}
        else if (id == R.id.branches) {
            Intent intent=new Intent(getApplication(),branches.class);
            startActivity(intent);}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (togglebar.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}