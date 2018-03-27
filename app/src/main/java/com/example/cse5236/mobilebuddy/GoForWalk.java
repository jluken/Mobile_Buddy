package com.example.cse5236.mobilebuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GoForWalk extends AppCompatActivity implements SensorEventListener, StepListener {


    //Credit to online github repo for helping me write this functionality
    //Source: https://github.com/bagilevi/android-pedometer and http://www.gadgetsaint.com/android/create-pedometer-step-counter-android/
    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private TextView TvSteps;
    private Button BtnStart;
    private Button BtnStop;
    Activity active = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_for_walk);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);


        TvSteps = (TextView) findViewById(R.id.tv_steps);
        BtnStart = (Button) findViewById(R.id.btn_start);
        BtnStop = (Button) findViewById(R.id.btn_stop);

        BtnStop.setEnabled(false);
        TvSteps.setText("Steps taken: " + numSteps);

        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(GoForWalk.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                BtnStart.setEnabled(false);
                BtnStop.setEnabled(true);
            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                BtnStop.setEnabled(false);
                BtnStart.setEnabled(true);
                MakeAlertRestartingFromSleep();
                sensorManager.unregisterListener(GoForWalk.this);

                //Add this count to the stats
                int currentBoredomStat = HomeScreenActivity.getStat(active, "boredom" );
                HomeScreenActivity.setStat(active, "boredom", currentBoredomStat + numSteps /2);

                //Reset steps for walk
                numSteps = 0;
            }
        });
    }



    private void  MakeAlertRestartingFromSleep()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Nice job! Your pet walked for " + numSteps + " steps with you!");
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
    }


}
