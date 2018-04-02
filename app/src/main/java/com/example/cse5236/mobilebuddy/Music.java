package com.example.cse5236.mobilebuddy;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class Music extends AppCompatActivity {

    private FragmentTransaction transaction;
    private MusicPlayerFragment mpFragment;
    public MediaPlayer mediaPlayer;
    public MediaPlayerHolder holder;
    public Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mediaPlayer = new MediaPlayer();
        holder = new MediaPlayerHolder(mediaPlayer);
        bundle = new Bundle();
        bundle.putSerializable("player", holder);
        mpFragment = new MusicPlayerFragment();
        mpFragment.setArguments(bundle);

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.music_container, mpFragment).commit();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
