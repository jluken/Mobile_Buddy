package com.example.cse5236.mobilebuddy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by krishnaganesan on 2/22/18.
 */

public class HomeScreenActivity extends AppCompatActivity{

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.wtf("buddy", "onCreate");
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

        final BuddyDisplayFragment buddyFragment = new BuddyDisplayFragment();
        fragmentTransaction.add(R.id.pet_container, buddyFragment);
        //fragmentTransaction.commit();
        final GraphFragment graphFragment = new GraphFragment();
        fragmentTransaction.add(R.id.graph_container, graphFragment);
        fragmentTransaction.commit();

        int minute = 30;
        //increase stats every minute minutes
        final Handler handler = new Handler();
        final int delay = 1000*60*minute; //milliseconds
        final Activity active = this;
        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                int hunger = getStat(active,"hunger");
                int sleepiness = getStat(active,"sleepiness");
                int boredom = getStat(active,"boredom");
                int playfulness = getStat(active,"playfulness");
                int sadness = getStat(active,"sadness");
                int loneliness = getStat(active,"loneliness");

                setStat(active,"hunger", hunger +1);
                setStat(active,"sleepiness", sleepiness + 1);
                setStat(active,"boredom", boredom + 1);
                setStat(active,"playfulness", playfulness + 1);
                setStat(active,"sadness", sadness + 1);
                setStat(active,"loneliness", loneliness + 1);

                graphFragment.updateGraph();
                buddyFragment.updateBuddy();

                handler.postDelayed(this, delay);
            }
        }, delay);
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
    public static int getStat(Activity active, String stat){
        String filename = stat + ".dat";
        int statVal = 0;
        try{
            Log.e("file: ","mobilebuddy "+filename);
            FileInputStream in = active.openFileInput(filename);
            Scanner scanner = new Scanner(in);
            String data = scanner.nextLine();
            Log.e("data: ","mobilebuddy " + data);
            statVal = Integer.parseInt(data);
            in.close();
            scanner.close();
        }
        catch(java.io.FileNotFoundException	e){
            statVal = 0;
            FileOutputStream outputStream;
            try {
                outputStream = active.openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(Integer.toString(statVal).getBytes());
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }

//        Map<String, Integer> idMap = new HashMap<String, Integer>()
//        {
//            {
//                put("hunger", R.id.textHunger);
//                put("sleepiness", R.id.textSleepy);
//                put("boredom", R.id.textBored);
//                put("playfulness", R.id.textPlayful);
//                put("sadness", R.id.textSad);
//                put("loneliness", R.id.textLonely);
//            }
//        };
//        Log.e("stat: ","mobilebuddy "+stat);
//        Log.e("id: ","mobilebuddy "+idMap.get(stat));
//        Log.e("view", "mobilebuddy "+ getView());
//        TextView statDisplay = (TextView)getView().findViewById(idMap.get(stat));
//        Log.e("textview: ","mobilebuddy "+statDisplay);
//        statDisplay.setText(stat+": "+statVal);

        return statVal;
    }

    public static void setStat(Activity active, String stat, int val){
        String filename = stat + ".dat";
        if (val > 100){
            val = 100;
        }
        if (val < 0){
            val = 0;
        }


        FileOutputStream outputStream;
        try {
            outputStream = active.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(Integer.toString(val).getBytes());
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
