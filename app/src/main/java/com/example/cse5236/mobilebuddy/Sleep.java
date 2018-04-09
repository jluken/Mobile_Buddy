package com.example.cse5236.mobilebuddy;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.AlertDialog;

import android.hardware.display.DisplayManager;
import android.os.PowerManager;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Timer;

public class Sleep extends AppCompatActivity {

    boolean isSleeping;

    TextView currentCurfewDisplay;
    Button setNewCurfew;
    Button startSleeping;
    TimePicker curfewSetter;
    Time curfew;
    Date sleepStarted;
    SleepScheduleHelper helper = new SleepScheduleHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_sleep_horiz);
        else
            setContentView(R.layout.activity_sleep);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        isSleeping = false;
        currentCurfewDisplay = findViewById(R.id.currentCurfewDisplay);
        curfewSetter = findViewById(R.id.curfewSetter);


        startSleeping = findViewById(R.id.startSleeping);
        startSleeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        Date currentTime = Calendar.getInstance().getTime();

                if (helper.CheckCurrentTimeBeforeCurfew(curfew, currentTime  ))
                {
                    isSleeping = true;
                    MakeAlertSuccessfulSleepAttempt();
                }
                else
                {
                    MakeAlertFailedSleepAttempt();

                }
            }
        });
        startSleeping.setEnabled(false);

        setNewCurfew = findViewById(R.id.setNewCurfew);
        setNewCurfew.setTag(1);
        setNewCurfew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int status =(Integer) v.getTag();
                if(status == 1) {
                    curfewSetter.setEnabled(true);
                    startSleeping.setEnabled(false);
                    setNewCurfew.setText("Confirm Curfew");
                    v.setTag(0); //pause
                } else {
                    startSleeping.setEnabled(true);


                    curfew = new Time(curfewSetter.getCurrentHour(), curfewSetter.getCurrentMinute(), 0);
                    setNewCurfew.setText("Set new curfew");
                    curfewSetter.setEnabled(false);
                    setNewCurfew.setEnabled(false);

                    currentCurfewDisplay.setText("Current Curfew:" + curfew.toString());
                    v.setTag(1); //pause
                }
            }
        });


        if (savedInstanceState != null) {
            curfew = new Time(savedInstanceState.getLong("CURFEW"));
            startSleeping.setEnabled(true);


            curfew = new Time(curfewSetter.getCurrentHour(), curfewSetter.getCurrentMinute(), 0);
            setNewCurfew.setText("Set new curfew after you sleep!");
            curfewSetter.setEnabled(false);
            setNewCurfew.setEnabled(false);

            currentCurfewDisplay.setText("Current Curfew:" + curfew.toString());
            setNewCurfew.setTag(1); //pause        }
        }
        else
        {
            curfewSetter.setEnabled(false);
            currentCurfewDisplay.setText("No current curfew set!");
        }



    }

    private void  MakeAlertSuccessfulSleepAttempt()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("You're on time, sleep your phone now to put your pet to sleep!");
        builder1.setCancelable(true);

        builder1.setNeutralButton(
                "Awesome, I'll sleep my phone now!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert = builder1.create();
        alert.show();
    }


    private void  MakeAlertFailedSleepAttempt()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("You missed your curfew tonight! Try again tomorrow :)");
        builder1.setCancelable(true);

        builder1.setNeutralButton(
                "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert = builder1.create();
        alert.show();
    }

    private void  MakeAlertRestartingFromSleep(int hours, int minutes)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Welcome back! Your pet slept for " + hours +  " hours and "+ minutes + " minutes! You can change your curfew for tomorrow if you want!");
        builder1.setCancelable(true);

        builder1.setNeutralButton(
                "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert = builder1.create();
        alert.show();
    }


    private boolean IsScreenOn()
    {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        boolean result= VERSION.SDK_INT>=VERSION_CODES.KITKAT_WATCH&&powerManager.isInteractive()||VERSION.SDK_INT<VERSION_CODES.KITKAT_WATCH&&powerManager.isScreenOn();
        return result;
    }



     @Override
    protected void onStop()
     {
         super.onStop();


         if (isSleeping && IsScreenOn() == false)
         {
             sleepStarted = Calendar.getInstance().getTime();
         }
         else //User tried to sleep but they didn't shut their display off to start the sleep
         {
             isSleeping = false;
         }


     }

     @Override
    protected void onRestart()
     {
        super.onRestart();

        if (isSleeping)
        {
            //Allow user to change curfew now if they want
            setNewCurfew.setEnabled(true);
            Date endTime = Calendar.getInstance().getTime();

            int[] hoursAndMinutes = helper.CalculateTimeDifference(sleepStarted, endTime );
            MakeAlertRestartingFromSleep(hoursAndMinutes[0], hoursAndMinutes[1]);

            //Add this count to the stats
            int currentSleepStat = HomeScreenActivity.getStat(this, "sleepiness" );
            HomeScreenActivity.setStat(this, "sleepiness", currentSleepStat - hoursAndMinutes[0]);

            isSleeping = false;
        }

     }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
        // Save the user's current game state
        if(curfew != null) {
            savedInstanceState.putLong("CURFEW", curfew.getTime());
            savedInstanceState.putString("KEY", "Test");
        }
    }


}
