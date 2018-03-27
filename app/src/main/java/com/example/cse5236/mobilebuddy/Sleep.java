package com.example.cse5236.mobilebuddy;

import android.content.DialogInterface;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isSleeping = false;
        currentCurfewDisplay = findViewById(R.id.currentCurfewDisplay);


        curfewSetter = findViewById(R.id.curfewSetter);
        curfewSetter.setEnabled(false);

        startSleeping = findViewById(R.id.startSleeping);
        startSleeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (CheckCurrentTimeBeforeCurfew())
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
                    startSleeping.setEnabled(false);
                    curfewSetter.setEnabled(true);
                    setNewCurfew.setText("Confirm Curfew");
                    v.setTag(0); //pause
                } else {
                    startSleeping.setEnabled(true);
                    curfew = new Time(curfewSetter.getCurrentHour(), curfewSetter.getCurrentMinute(), 0);
                    setNewCurfew.setText("Set new curfew");
                    curfewSetter.setEnabled(false);

                    currentCurfewDisplay.setText("Current Curfew:" + curfew.toString());
                    v.setTag(1); //pause
                }
            }
        });






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
        builder1.setMessage("Welcome back! Your pet slept for " + hours +  " hours and "+ minutes + " minutes!");
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

    private boolean CheckCurrentTimeBeforeCurfew()
    {

        boolean timeIsBeforeCurfew = false;
        Date currentTime = Calendar.getInstance().getTime();
        int minutesCurrentTime = currentTime.getHours() * 60 + currentTime.getMinutes();
        int minutesForCurfew = curfew.getHours() * 60 + curfew.getMinutes();

        if (minutesCurrentTime < minutesForCurfew)
        {
            timeIsBeforeCurfew = true;
        }

        return timeIsBeforeCurfew;

    }

    private boolean IsScreenOn()
    {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        boolean result= VERSION.SDK_INT>=VERSION_CODES.KITKAT_WATCH&&powerManager.isInteractive()||VERSION.SDK_INT<VERSION_CODES.KITKAT_WATCH&&powerManager.isScreenOn();
        return result;
    }

    private int[] CalculateTimeDifference(Date startTime)
    {

        Date endTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");

        long difference = endTime.getTime() - startTime.getTime();
        if(difference<0)
        {
            try
            {
                Date dateMax = simpleDateFormat.parse("24:00");
                Date dateMin = simpleDateFormat.parse("00:00");
                difference=(dateMax.getTime() -startTime.getTime() )+(endTime.getTime()-dateMin.getTime());

            }
            catch (Exception e)
            {
                Log.d("sleeptimecalculation", "CalculateTimeDifference: Exception occurred");
            }

        }
        int hours = (int) ((difference / (1000*60*60)));
        int min = (int) (difference - (1000*60*60*hours)) / (1000*60);

        int[] retVal = new int[2];
        retVal[0] = hours;
        retVal[1] = min;

        return retVal;
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
            int[] hoursAndMinutes = CalculateTimeDifference(sleepStarted);
            MakeAlertRestartingFromSleep(hoursAndMinutes[0], hoursAndMinutes[1]);

            //Add this count to the stats
        }

     }


}
