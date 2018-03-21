package com.example.cse5236.mobilebuddy;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Draw extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button lonelyUpButton = findViewById(R.id.lonelyUpButton);
        lonelyUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateStat(1);
            }
        });
        final Button lonelyDownButton = findViewById(R.id.lonelyDownButton);
        lonelyDownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateStat(-1);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void updateStat(int upOrDown){
        String filename = "loneliness.dat";
        int statVal = 0;
        try{
            Log.e("file: ","mobilebuddy "+filename);
            FileInputStream in = openFileInput(filename);
            Scanner scanner = new Scanner(in);
            String data = scanner.nextLine();
            Log.e("data: ","mobilebuddy " + data);
            statVal = Integer.parseInt(data);
            statVal+=upOrDown;
            FileOutputStream outputStream;
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(Integer.toString(statVal).getBytes());
            outputStream.close();
            in.close();
            scanner.close();
        }
        catch(java.io.FileNotFoundException	e){
            Log.e("error: ","mobilebuddy file not found");
            statVal = 0;
            FileOutputStream outputStream;
            try {
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(Integer.toString(statVal).getBytes());
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }
    }



}
