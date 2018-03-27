package com.example.cse5236.mobilebuddy;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

import android.content.res.Resources;

public class PlayGame extends AppCompatActivity implements View.OnTouchListener {

    public int score = 0;
    private ImageView buddy, battery1, battery2, battery3;
    private TextView scoreText;
    private ArrayList<ImageView> batteryList = new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        FrameLayout layout = findViewById(R.id.frameMain);

        buddy = new ImageView(this);
        buddy.setImageResource(R.drawable.buddysprite);
        buddy.setMaxHeight(4);
        buddy.setMaxWidth(4);
        buddy.setX(getScreenWidth() / 2);
        buddy.setY(10);
        layout.addView(buddy);

        battery1 = new ImageView(this);
        battery1.setImageResource(R.drawable.battery);
        battery1.setMaxWidth(4);
        battery1.setMaxHeight(4);
        layout.addView(battery1);

        battery2 = new ImageView(this);
        battery2.setImageResource(R.drawable.battery);
        battery2.setMaxWidth(4);
        battery2.setMaxHeight(4);
        layout.addView(battery2);

        battery3 = new ImageView(this);
        battery3.setImageResource(R.drawable.battery);
        battery3.setMaxWidth(4);
        battery3.setMaxHeight(4);
        layout.addView(battery3);

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
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Update();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
    }

    @Override
    public boolean onTouch(View view, MotionEvent event){
        final int x = (int) event.getRawX();

        if (x < getScreenWidth() / 2){
            buddy.setX(buddy.getX() - 1);
        } else if (x > getScreenWidth() / 2){
            buddy.setX(buddy.getX() + 1);
        }

        return true;
    }

    public void Update(){
        CheckCollisions();

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
            }
        }
    }

    public void ApplyGravity(){
        for (int i = 0; i < batteryList.size(); i++){
            batteryList.get(i).setY(batteryList.get(i).getY() - 1);
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public void Respawn(ImageView battery){
        battery.setY(10);
        battery.setX(new Random().nextInt(getScreenWidth()));
    }

}