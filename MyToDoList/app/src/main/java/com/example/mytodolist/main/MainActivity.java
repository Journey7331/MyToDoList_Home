package com.example.mytodolist.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mytodolist.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    AddFragment addFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}