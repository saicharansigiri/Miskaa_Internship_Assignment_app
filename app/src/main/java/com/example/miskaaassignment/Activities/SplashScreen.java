package com.example.miskaaassignment.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.miskaaassignment.R;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();
        final Handler handler = new Handler();
        final Runnable runnable = () -> {
            Intent i = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(i);
            finish();
        };
        handler.postDelayed(runnable, 1000);
    }

}