package com.example.cse5236.mobilebuddy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

/**
 * Created by Amir on 2/23/2018.
 */

public class InteractScreenActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interact_screen_activity);

        final Button playGameButton = findViewById(R.id.playGameButton);
        playGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        final Button playMusicButton = findViewById(R.id.playMusicButton);
        playMusicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        final Button collectFoodButton = findViewById(R.id.collectFoodButton);
        collectFoodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        final Button drawButton = findViewById(R.id.drawButton);
        drawButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        final Button walkButton = findViewById(R.id.walkButton);
        walkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        final Button sleepButton = findViewById(R.id.sleepButton);
        sleepButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });
    }
}
