package com.example.busroutebackend;

import android.app.Application;

import com.firebase.client.Firebase;

public class RouteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

}
