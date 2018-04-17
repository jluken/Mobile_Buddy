package com.example.cse5236.mobilebuddy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;

/**
 * Created by Amir on 2/23/2018.
 */

public class InteractScreenHorizontalFragment extends Fragment {

    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.interact_horizontal_layout, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        bundle = this.getArguments();
        final String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

        final ImageView gameIcon = getView().findViewById(R.id.gameIcon);
        gameIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity((Intent)bundle.getParcelable("game"));
            }
        });

        final ImageView musicIcon = getView().findViewById(R.id.musicIcon);
        musicIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getContext().checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_DENIED)
                    ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, 1);
                else
                    startActivity((Intent) bundle.getParcelable("music"));

            }
        });

        final ImageView foodIcon = getView().findViewById(R.id.foodIcon);
        foodIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity((Intent)bundle.getParcelable("food"));
            }
        });

        final ImageView drawIcon = getView().findViewById(R.id.drawIcon);
        drawIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity((Intent)bundle.getParcelable("draw"));
            }
        });

        final ImageView walkIcon = getView().findViewById(R.id.walkIcon);
        walkIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity((Intent)bundle.getParcelable("walk"));
            }
        });

        final ImageView sleepIcon = getView().findViewById(R.id.sleepIcon);
        sleepIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity((Intent)bundle.getParcelable("sleep"));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    startActivity((Intent) bundle.getParcelable("music"));
            }
        }
    }
}
