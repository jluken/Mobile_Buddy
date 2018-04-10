//package com.example.cse5236.mobilebuddy;
//
//import android.app.Application;
//
//import com.example.cse5236.mobilebuddy.CustomPinActivity;
//import com.github.orangegangsters.lollipin.lib.managers.LockManager;
//
//import com.github.orangegangsters.lollipin.lib.managers.AppLockActivity;
//
//
//public class CustomApplication extends Application {
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        LockManager<CustomPinActivity> lockManager = LockManager.getInstance();
//        lockManager.enableAppLock(this, CustomPinActivity.class);
//    }
//}