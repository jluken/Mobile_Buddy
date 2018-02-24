package com.example.cse5236.mobilebuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;

import java.util.logging.Logger;

/**
 * Created by krishnaganesan on 2/22/18.
 */

public class HomeScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("Checkpoint3", "HomeScreenActivity: On Create Triggered");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_activity);
        final Intent beginIntent = new Intent(this, InteractScreenActivity.class);

        final Button clickMeButton = findViewById(R.id.clickMeButton);
        clickMeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(beginIntent);
            }
        });
    }

    @Override
    protected void onStop()
    {
        Log.d("Checkpoint3", "HomeScreenActivity: On Stop Triggered");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.d("Checkpoint3", "HomeScreenActivity: On Start Triggered");
        super.onStart();

    }

}
