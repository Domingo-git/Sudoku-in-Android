package com.domingo.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StartMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
    }
    
    public static StartMenu newInstance() {
        
        Bundle args = new Bundle();
        
        StartMenu fragment = new StartMenu();
        //fragment.setArguments(args);
        return fragment;
    }
}