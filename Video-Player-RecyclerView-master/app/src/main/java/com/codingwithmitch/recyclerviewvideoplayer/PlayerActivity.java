/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codingwithmitch.recyclerviewvideoplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.codingwithmitch.recyclerviewvideoplayer.models.MediaObject;
import com.github.stefanodp91.android.circularseekbar.CircularSeekBar;
import com.github.stefanodp91.android.circularseekbar.OnCircularSeekBarChangeListener;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * A fullscreen activity to play audio or video streams.
 */
public class PlayerActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,NavigationView.OnNavigationItemSelectedListener {
    int position;
    Context context;
    private ArrayList<MediaObject> mediaObjects = new ArrayList<>();
  private PlayerView playerView;
  private SimpleExoPlayer player;


    DataSource.Factory dataSourceFactory;
    int repeat_count = 0,aspect_count=0;

    int brightness;
    int count=0;
  private boolean playWhenReady = true;
  private int currentWindow = 0;
  private long playbackPosition = 0;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    ActionBarDrawerToggle togglebar;
  ImageView next,prev,more,insertSub,aspect_button;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_player);
      context = getApplicationContext();
    playerView = findViewById(R.id.video_view);

      drawerLayout = findViewById(R.id.drawer_layout);
      navigationView = findViewById(R.id.nav_view);
      togglebar = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
      drawerLayout.addDrawerListener(togglebar);
      navigationView.setNavigationItemSelectedListener(this);
      next=findViewById(R.id.next);
      prev=findViewById(R.id.prev);
      more=findViewById(R.id.more);
      more.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                  drawerLayout.closeDrawer(Gravity.RIGHT);
              } else {
                  drawerLayout.openDrawer(Gravity.RIGHT);
              }
          }
      });


      brightness =
              Settings.System.getInt(context.getContentResolver(),
                      Settings.System.SCREEN_BRIGHTNESS, 0);
    position =getIntent().getExtras().getInt("position");
      Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
    mediaObjects= (ArrayList<MediaObject>) getIntent().getSerializableExtra("mediaobject");


      // Check whether has the write settings permission or not.
if(player==null) {
    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    TrackSelection.Factory videoTrackSelectionFactory =
            new AdaptiveTrackSelection.Factory(bandwidthMeter);
    TrackSelector trackSelector =
            new DefaultTrackSelector(videoTrackSelectionFactory);

    // 2. Create the player
    player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
}
      // Bind the player to the view.
      playerView.setUseController(true);
       dataSourceFactory = new DefaultDataSourceFactory(
              context, Util.getUserAgent(context, "RecyclerView VideoPlayer"));

      initializePlayer(position);
      boolean settingsCanWrite = false;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          settingsCanWrite = Settings.System.canWrite(context);
      }
      if(!settingsCanWrite) {
          // If do not have write settings permission then open the Can modify system settings panel.
          Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
          startActivity(intent);
      }
      insertSub=findViewById(R.id.insertSub);
      insertSub.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              showFileChooser();
          }
      });
      CircularSeekBar seekBar = findViewById(R.id.bright);
seekBar.setStep(1);
     seekBar.setProgress(brightness);
      seekBar.setOnRoundedSeekChangeListener(new OnCircularSeekBarChangeListener() {

          @Override
          public void onProgressChange(CircularSeekBar CircularSeekBar, float progress) {
              Settings.System.putInt(context.getContentResolver(),
                      Settings.System.SCREEN_BRIGHTNESS, (int) (progress));
          }

          @Override
          public void onStartTrackingTouch(CircularSeekBar CircularSeekBar) {

          }

          @Override
          public void onStopTrackingTouch(CircularSeekBar CircularSeekBar) {

          }
      });
      aspect_button=findViewById(R.id.aspect_button);
      aspect_button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(aspect_count==0) {
                  playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                  aspect_count=1;
              }
              else if(aspect_count==1)
              {
                  playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                  aspect_count=2;
              }
              else if(aspect_count==2)
              {
                  playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                  aspect_count=3;
              }
              else if(aspect_count==3)
              {
                  playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                  aspect_count=0;
              }
          }
      });
      next.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(position<mediaObjects.size()-1) {
                  initializePlayer(position++);
              }
              else
              {
                  position=0;
                  initializePlayer(position);
              }
          }
      });
      prev.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(position>0) {
                  initializePlayer(position--);
              }
              else
              {
                  position=mediaObjects.size()-1;
                  initializePlayer(position);
              }
          }
      });
  }
  public String timeConversion(long value) {
    String songTime;
    int dur = (int) value;
    int hrs = (dur / 3600000);
    int mns = (dur / 60000) % 60000;
    int scs = dur % 60000 / 1000;

    if (hrs > 0) {
      songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
    } else {
      songTime = String.format("%02d:%02d", mns, scs);
    }
    return songTime;
  }
  @Override
  public void onStart() {
    super.onStart();
    if (Util.SDK_INT > 23) {
      initializePlayer(position);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    hideSystemUi();
    if ((Util.SDK_INT <= 23 || player == null)) {
      initializePlayer(position);
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    if (Util.SDK_INT <= 23) {
      releasePlayer();
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    if (Util.SDK_INT > 23) {
      releasePlayer();
    }
  }

  private void initializePlayer(int pos) {


          MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                  .createMediaSource(Uri.parse(mediaObjects.get(pos).getMedia_url()));
          player.prepare(videoSource);
          player.setPlayWhenReady(true);
      playerView.setPlayer(player);

  }

  private void releasePlayer() {
    if (player != null) {
      playbackPosition = player.getCurrentPosition();
      currentWindow = player.getCurrentWindowIndex();
      playWhenReady = player.getPlayWhenReady();
      player.release();
      player = null;
    }
  }

  @SuppressLint("InlinedApi")
  private void hideSystemUi() {
    playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
  }


  @Override
  public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

  }

  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {

  }

  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {

  }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.playBack_speed) {
            // Handle the camera action


            if(count==0)
            {
                PlaybackParams param = new PlaybackParams();
                param.setSpeed(1f);// 1f is 1x, 2f is 2x
                player.setPlaybackParams(param);
                count=1;
                Toast.makeText(this, "1X", Toast.LENGTH_SHORT).show();
            }
           else if(count==1)
            {
                PlaybackParams param = new PlaybackParams();
                param.setSpeed(2f);// 1f is 1x, 2f is 2x
                player.setPlaybackParams(param);
                Toast.makeText(this, "2X", Toast.LENGTH_SHORT).show();
                count=0;
            }
        } else if (id == R.id.info) {

        } else if (id == R.id.repeat) {
if(repeat_count==0)
{
    player.setRepeatMode(Player.REPEAT_MODE_ONE);
    Toast.makeText(context, "Repeat Once", Toast.LENGTH_SHORT).show();
    repeat_count=1;
}
           else if(repeat_count==1)
            {
                player.setRepeatMode(Player.REPEAT_MODE_ALL);
                Toast.makeText(context, "Repeat All", Toast.LENGTH_SHORT).show();
                repeat_count=2;
            }
            else if(repeat_count==2)
            {
                player.setRepeatMode(Player.REPEAT_MODE_OFF);
                Toast.makeText(context, "Repeat Off", Toast.LENGTH_SHORT).show();
                repeat_count=0;
            }
        }
        return true;
    }
    private void showFileChooser() {
        try {
            // Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // startActivityForResult(i, RESULT_LOAD_IMAGE);
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, 1);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

        }
    }
}
