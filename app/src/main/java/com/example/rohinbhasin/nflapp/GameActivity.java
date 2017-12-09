package com.example.rohinbhasin.nflapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rohinbhasin.nflapp.JsonClasses.Game;
import com.example.rohinbhasin.nflapp.JsonClasses.GameVotes;
import com.example.rohinbhasin.nflapp.JsonClasses.Score;
import com.example.rohinbhasin.nflapp.JsonClasses.Team;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity {

    private static final String IN_PROGRESS = "In Progress";
    private static final String TRUE = "true";
    private static final String FINAL_SCORE = "Final";
    private static final String ROBOTO_BOLD_FILE_PATH = "fonts/Roboto_Condensed/RobotoCondensed-Bold.ttf";
    public static final String ROBOTO_LIGHT_FILE_PATH = "fonts/Roboto/Roboto-Light.ttf";
    public static final String ROBOTO_CONDENSED_FILE_PATH = "fonts/Roboto_Condensed/RobotoCondensed-Light.ttf";

    private LinearLayout gameLayout;
    private TextView awayTeamView;
    private TextView awayScoreView;
    private ImageView awayImageView;
    private TextView homeTeamView;
    private TextView homeScoreView;
    private ImageView homeImageView;
    private TextView gameStatusView;
    private Button awayVoteButton;
    private Button homeVoteButton;
    private TextView awayTeamVotePercentage;
    private TextView homeTeamVotePercentage;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    private Score gameScore;
    private Game game;
    private Team awayTeam;
    private Team homeTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameLayout = (LinearLayout) findViewById(R.id.game_layout);
        awayTeamView = (TextView) findViewById(R.id.away_team_name_detail);
        awayScoreView = (TextView) findViewById(R.id.away_score);
        awayImageView = (ImageView) findViewById(R.id.away_team_logo_detail);
        homeTeamView = (TextView) findViewById(R.id.home_team_name_detail);
        homeScoreView = (TextView) findViewById(R.id.home_score);
        homeImageView = (ImageView) findViewById(R.id.home_team_logo_detail);
        gameStatusView = (TextView) findViewById(R.id.game_status);
        awayVoteButton = (Button) findViewById(R.id.vote_for_away_button);
        homeVoteButton = (Button) findViewById(R.id.vote_for_home_button);
        awayTeamVotePercentage = (TextView) findViewById(R.id.away_team_percentage);
        homeTeamVotePercentage = (TextView) findViewById(R.id.home_team_percentage);

        database = FirebaseDatabase.getInstance();

        final Intent intent = getIntent();
        gameScore = intent.getParcelableExtra("score");
        game = gameScore.getGame();
        awayTeam = game.getAwayTeam();
        homeTeam = game.getHomeTeam();

        setFonts();
        setTeamProperties();
        setViewBasedOnGameStatus();
        setColorsOfGameView();
    }

    /**
     * Set the fonts/typefaces for the views.
     */
    private void setFonts() {
        final Typeface ROBOTO_THIN = Typeface.createFromAsset(getAssets(),
                ROBOTO_LIGHT_FILE_PATH);
        final Typeface ROBOTO_CONDENSED_LIGHT = Typeface.createFromAsset(getAssets(),
                ROBOTO_CONDENSED_FILE_PATH);

        awayTeamView.setTypeface(ROBOTO_THIN);
        awayScoreView.setTypeface(ROBOTO_CONDENSED_LIGHT);
        homeTeamView.setTypeface(ROBOTO_THIN);
        homeScoreView.setTypeface(ROBOTO_CONDENSED_LIGHT);
    }

    /**
     * Sets initial team properties with names, scores, and logos.
     */
    private void setTeamProperties() {
        final Game GAME = gameScore.getGame();
        final Team AWAY_TEAM = GAME.getAwayTeam();
        final Team HOME_TEAM = GAME.getHomeTeam();
        final Context context = getApplicationContext();

        awayTeamView.setText(AWAY_TEAM.getName());
        homeTeamView.setText(HOME_TEAM.getName());
        awayScoreView.setText(gameScore.getAwayScore());
        homeScoreView.setText(gameScore.getHomeScore());

        try {
            final String AWAY_TEAM_URL = TeamLogos.getLogoURLForTeam(awayTeam.getName());
            final String HOME_TEAM_URL = TeamLogos.getLogoURLForTeam(homeTeam.getName());
            Picasso.with(context).load(AWAY_TEAM_URL).into(awayImageView);
            Picasso.with(context).load(HOME_TEAM_URL).into(homeImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets characteristics of the view based on whether or not the game has occurred yet or based
     * on the results of the game.
     */
    private void setViewBasedOnGameStatus() {

        final Typeface ROBOTO_CONDENSED_BOLD = Typeface.createFromAsset(getAssets(),
                ROBOTO_BOLD_FILE_PATH);
        final String DATE_DISPLAY = FormattingUtilities.reformatDateForDisplay(game.getDate()) + "\n" + game.getTime();

        if (gameScore.getIsUnplayed().equals(TRUE)) {
            gameStatusView.setText(DATE_DISPLAY);
        } else if (gameScore.getIsInProgress().equals(TRUE)) {
            gameStatusView.setText(IN_PROGRESS);
        } else {
            gameStatusView.setText(FINAL_SCORE);
        }

        // Sets the winning team's score to Bold and hides the scores if the game hasn't occurred yet
        if (gameScore.getIsUnplayed().equals(TRUE)) {
            awayScoreView.setVisibility(View.INVISIBLE);
            homeScoreView.setVisibility(View.INVISIBLE);
        } else if (Integer.valueOf(gameScore.getAwayScore()) > Integer.valueOf(gameScore.getHomeScore())) {
            awayScoreView.setTypeface(ROBOTO_CONDENSED_BOLD);
        } else {
            homeScoreView.setTypeface(ROBOTO_CONDENSED_BOLD);
        }
    }

    /**
     * Sets the color of the view based on the color of the home team
     */
    private void setColorsOfGameView() {

        Bitmap homeLogoBitmap = FormattingUtilities.drawableToBitmap(homeImageView.getDrawable());
        Palette.generateAsync(homeLogoBitmap, new Palette.PaletteAsyncListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onGenerated(Palette palette) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                int backgroundColor = palette.getLightMutedColor(0);
                int secondColor = palette.getVibrantColor(0);

                gameLayout.setBackgroundColor(backgroundColor);
                window.setStatusBarColor(secondColor);
                window.setNavigationBarColor(secondColor);
            }
        });
    }

    private void voteForAwayTeam() {

    }
}
