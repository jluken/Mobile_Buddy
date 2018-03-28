package com.example.cse5236.mobilebuddy;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import android.content.res.Resources;

public class PlayGame extends AppCompatActivity {

    public int score = 0;
    private ImageView buddy, battery1, battery2, battery3, leftButton, rightButton;
    private TextView scoreText;
    private int leftEvent, rightEvent;
    private ArrayList<ImageView> batteryList = new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        leftEvent = MotionEvent.ACTION_UP;
        rightEvent = MotionEvent.ACTION_UP;
        leftButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event){
                leftEvent = event.getAction();
                return true;
            }
        });

        rightButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event){
                rightEvent = event.getAction();
                return true;
            }
        });

        buddy = findViewById(R.id.buddy);
        buddy.setX(getScreenWidth() / 2);
        buddy.setY(getScreenHeight() - getScreenHeight() / 3.5f);

        battery1 = findViewById(R.id.battery1);
        battery2 = findViewById(R.id.battery2);
        battery3 = findViewById(R.id.battery3);

        batteryList.add(battery1);
        batteryList.add(battery2);
        batteryList.add(battery3);
        for (int i = 0; i < batteryList.size(); i++){
            Respawn(batteryList.get(i));
        }

        scoreText = findViewById(R.id.scoreText);

        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(10);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Update();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
    }

    public void Update(){
        ApplyGravity();
        CheckCollisions();

        if (buddy.getX() > 0 & (leftEvent == MotionEvent.ACTION_DOWN || leftEvent == MotionEvent.ACTION_MOVE))
            buddy.setX(buddy.getX() - 6);

        if (buddy.getX() < getScreenWidth() & (rightEvent == MotionEvent.ACTION_DOWN || rightEvent == MotionEvent.ACTION_MOVE))
            buddy.setX(buddy.getX() + 6);

        scoreText.setText("Score: " + score);
    }

    public void CheckCollisions(){
        int[] l = new int[2];
        buddy.getLocationOnScreen(l);
        Rect buddyRect = new Rect(l[0], l[1], l[0] + buddy.getWidth(), l[1] + buddy.getHeight());
        for (int i = 0; i < batteryList.size(); i++){
            int[] k = new int[2];
            batteryList.get(i).getLocationOnScreen(k);
            Rect batRect = new Rect(k[0], k[1], k[0] + batteryList.get(i).getWidth(), k[1] + batteryList.get(i).getHeight());
            if (buddyRect.intersect(batRect)){
                score += 100;
                Respawn(batteryList.get(i));
            }
            if (batteryList.get(i).getY() > getScreenHeight())
                Respawn(batteryList.get(i));
        }
    }

    public void ApplyGravity(){
        for (int i = 0; i < batteryList.size(); i++){
            batteryList.get(i).setY(batteryList.get(i).getY() + 3);
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public void Respawn(ImageView battery){
        battery.setY(0);
        battery.setX(new Random().nextInt(getScreenWidth()));
    }
}