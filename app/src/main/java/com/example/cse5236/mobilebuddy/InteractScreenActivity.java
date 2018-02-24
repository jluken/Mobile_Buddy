package com.example.cse5236.mobilebuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;

/**
 * Created by Amir on 2/23/2018.
 */

public class InteractScreenActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("Checkpoint3", "InteractScreenActivity: On Create Triggered");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.interact_screen_activity);

        final Intent gameIntent = new Intent(this, PlayGame.class);
        final Button playGameButton = findViewById(R.id.playGameButton);
        playGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(gameIntent);
            }
        });

        final Intent musicIntent = new Intent(this, Music.class);
        final Button playMusicButton = findViewById(R.id.playMusicButton);
        playMusicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(musicIntent);
            }
        });

        final Intent foodIntent = new Intent(this, CollectFood.class);
        final Button collectFoodButton = findViewById(R.id.collectFoodButton);
        collectFoodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(foodIntent);
            }
        });

        final Intent drawIntent = new Intent(this, Draw.class);
        final Button drawButton = findViewById(R.id.drawButton);
        drawButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(drawIntent);
            }
        });

        final Intent walkIntent = new Intent(this, GoForWalk.class);
        final Button walkButton = findViewById(R.id.walkButton);
        walkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(walkIntent);
            }
        });

        final Intent sleepIntent = new Intent(this, Sleep.class);
        final Button sleepButton = findViewById(R.id.sleepButton);
        sleepButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(sleepIntent);
            }
        });
    }

    @Override
    protected void onStop()
    {
        Log.d("Checkpoint3", "InteractScreenActivity: On Stop Triggered");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.d("Checkpoint3", "InteractScreenActivity: On Start Triggered");
        super.onStart();

    }
}
