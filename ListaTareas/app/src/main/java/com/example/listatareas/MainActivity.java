package com.example.listatareas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Solo carga el layout con el NavHostFragment
        setContentView(R.layout.activity_main);
    }
}
