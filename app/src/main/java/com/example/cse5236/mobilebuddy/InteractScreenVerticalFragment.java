package com.example.cse5236.mobilebuddy;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Amir on 2/23/2018.
 */

public class InteractScreenVerticalFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.interact_vertical_layout, container, false);

        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        final Button playGameButton = getView().findViewById(R.id.playGameButton);
        playGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        final Button playMusicButton = getView().findViewById(R.id.playMusicButton);
        playMusicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        final Button collectFoodButton = getView().findViewById(R.id.collectFoodButton);
        collectFoodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        final Button drawButton = getView().findViewById(R.id.drawButton);
        drawButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        final Button walkButton = getView().findViewById(R.id.walkButton);
        walkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        final Button sleepButton = getView().findViewById(R.id.sleepButton);
        sleepButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });
    }
}
