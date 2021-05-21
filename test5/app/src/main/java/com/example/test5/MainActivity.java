package com.example.test5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.graphics.drawable.ColorDrawable;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
public class MainActivity extends AppCompatActivity{
    AppBarConfiguration appBarConfiguration;
    NavController navController;
    DrawerLayout drawerLayout;
    SipSession.Listener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     navController = Navigation.findNavController(this, R.id.fragment);

 drawerLayout=findViewById(R.id.drawer_layout);
     appBarConfiguration  =
               new AppBarConfiguration.Builder(navController.getGraph())
                       .setOpenableLayout(drawerLayout)
                       .build();

       NavigationView navView = findViewById(R.id.nav_view);
       NavigationUI.setupWithNavController(navView, navController);
  NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        navController.addOnDestinationChangedListener((controller, destination, arguments) ->
              {
                  if(destination.getId()==R.id.firstFragment)
              {
                  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.purple_200)));
              }
              else if(destination.getId()==R.id.secondFragment)
              {
                  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.teal_200)));
              }
                  });
   }


    @Override
    public boolean onSupportNavigateUp()
    {
        return NavigationUI.navigateUp(navController, drawerLayout);
   }
}