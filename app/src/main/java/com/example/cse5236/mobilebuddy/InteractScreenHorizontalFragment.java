package com.example.cse5236.mobilebuddy;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;

/**
 * Created by Amir on 2/23/2018.
 */

public class InteractScreenHorizontalFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.interact_horizontal_layout, container, false);

        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        final Bundle bundle = this.getArguments();

        final Button playGameButton = getView().findViewById(R.id.playGameButton);
        playGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity((Intent)bundle.getParcelable("game"));
            }
        });

        final Button playMusicButton = getView().findViewById(R.id.playMusicButton);
        playMusicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity((Intent)bundle.getParcelable("music"));
            }
        });

        final Button collectFoodButton = getView().findViewById(R.id.collectFoodButton);
        collectFoodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity((Intent)bundle.getParcelable("food"));
            }
        });

        final Button drawButton = getView().findViewById(R.id.drawButton);
        drawButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity((Intent)bundle.getParcelable("draw"));
            }
        });

        final Button walkButton = getView().findViewById(R.id.walkButton);
        walkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity((Intent)bundle.getParcelable("walk"));
            }
        });

        final Button sleepButton = getView().findViewById(R.id.sleepButton);
        sleepButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity((Intent)bundle.getParcelable("sleep"));
            }
        });
    }
}
