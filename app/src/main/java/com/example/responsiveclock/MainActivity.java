package com.example.responsiveclock;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        Clock clock = new Clock(getApplicationContext());
        clock.setBackgroundColor(Color.GRAY);
        setContentView(clock);
    }
}