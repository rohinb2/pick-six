package com.example.rohinbhasin.nflapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rohinbhasin.nflapp.JsonClasses.Score;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView nflScoresListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nflScoresListView = (RecyclerView) findViewById(R.id.score_viewer);
        nflScoresListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    public class SportsFeedsAsyncTask extends AsyncTask<Integer, Integer, List<Score>> {

        @Override
        protected List<Score> doInBackground(Integer... cityID) {
            return SportsDataUtility.getScoresForCurrentWeek();
        }

        @Override
        protected void onPostExecute(List<Score> scores) {
            ScoreDisplayAdapter adapter = new ScoreDisplayAdapter(scores);
            nflScoresListView.setAdapter(adapter);
        }
    }
}
