package com.example.cse5236.mobilebuddy;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Music extends AppCompatActivity {

    private FragmentTransaction transaction;
    private MusicPlayerFragment mpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mpFragment = new MusicPlayerFragment();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.music_container, mpFragment).commit();
    }
}
