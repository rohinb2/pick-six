package com.example.rohinbhasin.nflapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.LinearGradient;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohinbhasin.nflapp.JsonClasses.Game;
import com.example.rohinbhasin.nflapp.JsonClasses.Score;
import com.example.rohinbhasin.nflapp.JsonClasses.Team;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity {

    public static final String IN_PROGRESS = "In Progress";
    private static final String TRUE = "true";
    public static final String FINAL_SCORE = "Final";

    private LinearLayout gameLayout;
    private TextView awayTeamView;
    private TextView awayScoreView;
    private ImageView awayImageView;
    private TextView homeTeamView;
    private TextView homeScoreView;
    private ImageView homeImageView;
    private TextView gameStatusView;

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

        Typeface roboto_thin = Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-Light.ttf");
        Typeface roboto_condensed_regular = Typeface.createFromAsset(getAssets(), "fonts/Roboto_Condensed/RobotoCondensed-Bold.ttf");
        Typeface roboto_condensed_light = Typeface.createFromAsset(getAssets(), "fonts/Roboto_Condensed/RobotoCondensed-Light.ttf");

        awayTeamView.setTypeface(roboto_thin);
        awayScoreView.setTypeface(roboto_condensed_light);
        homeTeamView.setTypeface(roboto_thin);
        homeScoreView.setTypeface(roboto_condensed_light);

        final Intent intent = getIntent();
        final Score GAME_SCORE = intent.getParcelableExtra("score");
        final Game GAME = GAME_SCORE.getGame();
        final Team AWAY_TEAM = GAME.getAwayTeam();
        final Team HOME_TEAM = GAME.getHomeTeam();
        final String DATE_DISPLAY = FormattingUtilities.reformatDateForDisplay(GAME.getDate()) + "\n" + GAME.getTime();

        awayTeamView.setText(AWAY_TEAM.getName());
        homeTeamView.setText(HOME_TEAM.getName());
        awayScoreView.setText(GAME_SCORE.getAwayScore());
        homeScoreView.setText(GAME_SCORE.getHomeScore());

        if (GAME_SCORE.getIsUnplayed().equals(TRUE)) {
            awayScoreView.setVisibility(View.INVISIBLE);
            homeScoreView.setVisibility(View.INVISIBLE);
        } else if (Integer.valueOf(GAME_SCORE.getAwayScore()) > Integer.valueOf(GAME_SCORE.getHomeScore())) {
            awayScoreView.setTypeface(roboto_condensed_regular);
        } else {
            homeScoreView.setTypeface(roboto_condensed_regular);
        }

        if (GAME_SCORE.getIsUnplayed().equals(TRUE)) {
            gameStatusView.setText(DATE_DISPLAY);
        } else if (GAME_SCORE.getIsInProgress().equals(TRUE)) {
            gameStatusView.setText(IN_PROGRESS);
        } else {
            gameStatusView.setText(FINAL_SCORE);
        }

        try {
            final Context context = getApplicationContext();
            final String AWAY_TEAM_URL = TeamLogos.getLogoURLForTeam(AWAY_TEAM.getName());
            final String HOME_TEAM_URL = TeamLogos.getLogoURLForTeam(HOME_TEAM.getName());

            Picasso.with(context).load(AWAY_TEAM_URL).into(awayImageView);
            Picasso.with(context).load(HOME_TEAM_URL).into(homeImageView);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap homeLogoBitmap = FormattingUtilities.drawableToBitmap(homeImageView.getDrawable());
        Palette.generateAsync(homeLogoBitmap, new Palette.PaletteAsyncListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onGenerated(Palette palette) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                int backgroundColor = palette.getMutedColor(0);
                int secondColor = palette.getVibrantColor(0);
                gameLayout.setBackgroundColor(backgroundColor);
                window.setStatusBarColor(secondColor);
                window.setNavigationBarColor(secondColor);
            }
        });
    }
}
