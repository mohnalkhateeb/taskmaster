package com.example.taskmaster.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.taskmaster.R;
import com.example.taskmaster.auth.AuthActivity;

public class SplashActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            //This method will be executed once the timer is over
            // Start your app main activity
            startActivity(new Intent(SplashActivity2.this, AuthActivity.class));
            // close this activity
            finish();
        }, 3000); // pause the launch of the dashboard for 3 seconds
    }
}