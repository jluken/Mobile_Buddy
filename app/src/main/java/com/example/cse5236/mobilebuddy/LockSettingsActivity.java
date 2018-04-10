package com.example.cse5236.mobilebuddy;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.orangegangsters.lollipin.lib.PinActivity;
import com.github.orangegangsters.lollipin.lib.managers.AppLock;
import com.github.orangegangsters.lollipin.lib.managers.LockManager;

public class LockSettingsActivity extends Activity implements View.OnClickListener {


    private static final int REQUEST_CODE_ENABLE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_settings);

        findViewById(R.id.enableLock).setOnClickListener(this);
        findViewById(R.id.editLock).setOnClickListener(this);
        findViewById(R.id.disableLock).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(LockSettingsActivity.this, CustomPinActivity.class);
        switch (v.getId()) {
            case R.id.enableLock:
                intent.putExtra(AppLock.EXTRA_TYPE, AppLock.ENABLE_PINLOCK);
                LockManager<CustomPinActivity> lockManager = LockManager.getInstance();
                lockManager.enableAppLock(this, CustomPinActivity.class);
                lockManager.getAppLock().setTimeout(1000);
                startActivityForResult(intent, REQUEST_CODE_ENABLE);
                break;
            case R.id.editLock:
                intent.putExtra(AppLock.EXTRA_TYPE, AppLock.CHANGE_PIN);
                startActivity(intent);
                break;
            case R.id.disableLock:
                intent.putExtra(AppLock.EXTRA_TYPE, AppLock.DISABLE_PINLOCK);
                LockManager<CustomPinActivity> lockManager1 = LockManager.getInstance();
                lockManager1.disableAppLock();
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_ENABLE:
                Toast.makeText(this, "PinCode enabled", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
