package com.example.rohinbhasin.nflapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rohinbhasin.nflapp.JsonClasses.Score;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final TextView test = findViewById(R.id.game_view);

        final Intent intent = getIntent();
        Score gameScore = intent.getParcelableExtra("score");

    }
}
