package com.example.rohinbhasin.nflapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.rohinbhasin.nflapp.JsonClasses.Score;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity with the Recycler Views of all the games.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView nflScoresListView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        nflScoresListView = (RecyclerView) findViewById(R.id.score_viewer);
        nflScoresListView.setLayoutManager(new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        new SportsFeedsAsyncTask().execute(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_res, menu);
        return true;
    }

    /**
     * On-click method that is called on the Sign Out button in the toolbar, switches activities and
     * signs out.
     *
     * @param item Item from the toolbar.
     */
    public void signOut(MenuItem item) {
        final Context context = getApplicationContext();
        Intent backToSignInActivity = new Intent(context, SignInActivity.class);
        backToSignInActivity.putExtra("launchedFromMain", 1);

        // start the sign in activity again and finish this activity so the user cannot return unauthenticated
        startActivity(backToSignInActivity);
        finish();
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
