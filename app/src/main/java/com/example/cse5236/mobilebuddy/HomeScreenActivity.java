package com.example.cse5236.mobilebuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
/**
 * Created by krishnaganesan on 2/22/18.
 */

public class HomeScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
}
