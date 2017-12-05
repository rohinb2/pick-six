package com.example.rohinbhasin.nflapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.rohinbhasin.nflapp.JsonClasses.Score;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView nflScoresListView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.menu_res);

        nflScoresListView = (RecyclerView) findViewById(R.id.score_viewer);
        nflScoresListView.setLayoutManager(new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        new SportsFeedsAsyncTask().execute(0);
    }

    

    /**
     * Asynchronous task for accessing all of the Game data from the SportsFeeds API.
     */
    public class SportsFeedsAsyncTask extends AsyncTask<Integer, Integer, ArrayList<Score>> {

        @Override
        protected ArrayList<Score> doInBackground(Integer... footballID) {
            return SportsDataUtility.getScoresForCurrentWeek();
        }

        @Override
        protected void onPostExecute(ArrayList<Score> scores) {
            ScoreDisplayAdapter adapter = new ScoreDisplayAdapter(scores);
            nflScoresListView.setAdapter(adapter);
        }
    }
}
