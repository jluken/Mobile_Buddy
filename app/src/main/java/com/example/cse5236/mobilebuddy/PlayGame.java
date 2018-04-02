package com.example.cse5236.mobilebuddy;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class PlayGame extends AppCompatActivity {

    private FragmentTransaction transaction;
    private GameFragment gfFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        gfFragment = new GameFragment();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.game_container, gfFragment).commit();
    }
}