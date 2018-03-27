package com.example.cse5236.mobilebuddy;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * Created by Amir on 2/23/2018.
 */

public class InteractScreenActivity extends FragmentActivity{

    private FragmentTransaction transaction;
    private InteractScreenVerticalFragment vertFrag;
    private InteractScreenHorizontalFragment horiFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("Checkpoint3", "InteractScreenActivity: On Create Triggered");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.interact_screen_activity);

        vertFrag = new InteractScreenVerticalFragment();
        horiFrag = new InteractScreenHorizontalFragment();

        Intent gameIntent = new Intent(this, PlayGame.class);
        Intent musicIntent = new Intent(this, Music.class);
        Intent foodIntent = new Intent(this, CollectFood.class);
        Intent drawIntent = new Intent(this, Draw.class);
        Intent walkIntent = new Intent(this, GoForWalk.class);
        Intent sleepIntent = new Intent(this, Sleep.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable("game", gameIntent);
        bundle.putParcelable("music", musicIntent);
        bundle.putParcelable("food", foodIntent);
        bundle.putParcelable("draw", drawIntent);
        bundle.putParcelable("walk", walkIntent);
        bundle.putParcelable("sleep", sleepIntent);
        vertFrag.setArguments(bundle);
        horiFrag.setArguments(bundle);

        transaction = getSupportFragmentManager().beginTransaction();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            transaction.add(R.id.interact_container, horiFrag).commit();
        else
            transaction.add(R.id.interact_container, vertFrag).commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        transaction = getSupportFragmentManager().beginTransaction();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            transaction.replace(R.id.interact_container, horiFrag).commit();
        else
            transaction.replace(R.id.interact_container, vertFrag).commit();
    }

    @Override
    protected void onStop()
    {
        Log.d("Checkpoint3", "InteractScreenActivity: On Stop Triggered");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.d("Checkpoint3", "InteractScreenActivity: On Start Triggered");
        super.onStart();

    }
}
