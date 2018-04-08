package com.example.cse5236.mobilebuddy;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Draw extends AppCompatActivity {

    private DrawCanvas customCanvas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("checkpoint", "mobilebuddy draw create");
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_draw_horiz);
        else
            setContentView(R.layout.activity_draw);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button drawButton = findViewById(R.id.DrawButton);
        customCanvas = (DrawCanvas) findViewById(R.id.signature_canvas);
        final Activity active = this;

        drawButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int lonely = HomeScreenActivity.getStat(active, "loneliness");
                HomeScreenActivity.setStat(active, "loneliness", (lonely - 20));
                ImageView mImageView;
                mImageView = (ImageView) (ImageView)findViewById(R.id.drawBuddy);
                mImageView.setImageResource(R.drawable.buddythanks);
                customCanvas.clearCanvas();
                drawButton.setEnabled(false);

            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        final Button drawButton = findViewById(R.id.DrawButton);
        ImageView mImageView;
        mImageView = (ImageView) (ImageView)findViewById(R.id.drawBuddy);
        mImageView.setImageResource(R.drawable.buddydraw);
        drawButton.setEnabled(true);


    }





}
