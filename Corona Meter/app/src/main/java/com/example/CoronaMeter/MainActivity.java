package com.example.CoronaMeter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity  {

        SNavigationDrawer sNavigationDrawer;
        int color1=0;
        Class fragmentClass;
public static Fragment fragment;
        private static RemoteViews contentView;
        private static Notification notification;
        private static NotificationManager notificationManager;
        private static final int NotificationID = 1005;
        private static NotificationCompat.Builder mBuilder;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar()!=null) {
        getSupportActionBar().hide();
        }

        sNavigationDrawer = findViewById(R.id.navigationDrawer);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Home",R.drawable.home_back));
        menuItems.add(new MenuItem("Districts",R.drawable.district_back));
        menuItems.add(new MenuItem("Hospital",R.drawable.hospital_back));
        menuItems.add(new MenuItem("News",R.drawable.back_newsjpg));
        menuItems.add(new MenuItem("My Account",R.drawable.avatar1));

        sNavigationDrawer.setMenuItemList(menuItems);
        fragmentClass =  Home.class;
        try {
                fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                e.printStackTrace();
                }
                if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
                }

        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
                @Override
                public void onMenuItemClicked(int position) {
                        System.out.println("Position "+position);

                        switch (position){
                                case 0:{
                                        color1 = R.color.red;
                                        fragmentClass = Home.class;
                                        break;
                                }
                                case 1:{
                                        color1 = R.color.orange;
                                        fragmentClass = myAns.class;
                                        break;
                                }
                                case 2:{
                                        color1 = R.color.white;
                                        fragmentClass = myQuestions.class;
                                        break;
                                }
                                case 3:{
                                        color1 = R.color.teal_200;
                                        fragmentClass = news.class;
                                        break;
                                }
                                case 4:{
                                        color1 = R.color.blue;
                                        fragmentClass = myAccountfrag.class;
                                        break;
                                }


                        }
                        sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                                @Override
                                public void onDrawerOpened() {

                                }

                                @Override
                                public void onDrawerOpening(){

                                }

                                @Override
                                public void onDrawerClosing(){
                                        System.out.println("Drawer closed");

                                        try {
                                                fragment = (Fragment) fragmentClass.newInstance();
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }

                                        if (fragment != null) {
                                                FragmentManager fragmentManager = getSupportFragmentManager();
                                                fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                                        }
                                }

                                @Override
                                public void onDrawerClosed() {

                                }

                                @Override
                                public void onDrawerStateChanged(int newState) {
                                        System.out.println("State "+newState);
                                }
                        });
                }
        });
                RunNotification();
      }
        private void RunNotification() {

                notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
                mBuilder = new NotificationCompat.Builder(getApplicationContext(), "notify_001");

                contentView = new RemoteViews(getPackageName(), R.layout.notify);
                contentView.setImageViewResource(R.id.image, R.drawable.speedometer);
                contentView.setString(R.id.charging,getPackageName(),"hlw");
                Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
                        notificationIntent, 0);
                mBuilder.setSmallIcon(R.drawable.tabicon1);
                mBuilder.setAutoCancel(false);
                mBuilder.setOngoing(false);
                mBuilder.setPriority(Notification.PRIORITY_HIGH);
                mBuilder.setContentTitle("Corona Meter");
           mBuilder.setWhen(6*60*60);
           mBuilder.setContentIntent(intent);
                mBuilder.build().flags = Notification.FLAG_NO_CLEAR | Notification.PRIORITY_HIGH;
                mBuilder.setContent(contentView);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        String channelId = "channel_id";
                        NotificationChannel channel = new NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH);
                        channel.enableVibration(true);
                        channel.setVibrationPattern(new long[]{100,200, 400});
                        notificationManager.createNotificationChannel(channel);
                        mBuilder.setChannelId(channelId);
                }

                notification = mBuilder.build();
                notificationManager.notify(NotificationID, notification);
        }
}
