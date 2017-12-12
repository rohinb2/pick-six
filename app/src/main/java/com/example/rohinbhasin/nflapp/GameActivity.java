package com.example.rohinbhasin.nflapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rohinbhasin.nflapp.JsonClasses.Game;
import com.example.rohinbhasin.nflapp.JsonClasses.GameVotes;
import com.example.rohinbhasin.nflapp.JsonClasses.PlayerStats;
import com.example.rohinbhasin.nflapp.JsonClasses.Score;
import com.example.rohinbhasin.nflapp.JsonClasses.Team;
import com.example.rohinbhasin.nflapp.JsonClasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Activity that has the information about a game including score, stats, and voting functionality.
 */
public class GameActivity extends AppCompatActivity {

    private static final String UNVOTE = "Unvote";
    private static final String VOTE = "Vote";
    private static final String IN_PROGRESS = "In Progress";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String FINAL_SCORE = "Final";
    private static final String ROBOTO_BOLD_FILE_PATH = "fonts/Roboto_Condensed/RobotoCondensed-Bold.ttf";
    private static final String ROBOTO_LIGHT_FILE_PATH = "fonts/Roboto/Roboto-Light.ttf";
    private static final String ROBOTO_CONDENSED_FILE_PATH = "fonts/Roboto_Condensed/RobotoCondensed-Light.ttf";
    private static final String GAMES_DB_REFERENCE = "games";
    private static final String SCORE_INTENT_REFERENCE = "score";
    private static final String PERCENT_SYMBOL = "%";
    private static final String NO_VOTES = "No Votes";
    private static final int TOTAL_PERCENT = 100;
    private static final int LENGTH_OF_PERCENT = 4;
    private static final int TEXT_SIZE_NO_VOTES = 15;
    private static final int TEXT_SIZE_PERCENT = 25;
    private static final int QB_STAT = 0;
    private static final int RB_STAT = 1;
    private static final int WR_STAT = 2;
    public static final String NO_STATS = "No Stats";

    private LinearLayout gameLayout;
    private TextView awayTeamView;
    private TextView awayScoreView;
    private ImageView awayImageView;
    private TextView homeTeamView;
    private TextView homeScoreView;
    private ImageView homeImageView;
    private TextView gameStatusView;

    private TextView awayQBStatsView;
    private TextView homeQBStatsView;
    private TextView awayRBStatsView;
    private TextView homeRBStatsView;
    private TextView awayWRStatsView1;
    private TextView awayWRStatsView2;
    private TextView homeWRStatsView1;
    private TextView homeWRStatsView2;

    private Button awayVoteButton;
    private Button homeVoteButton;
    private TextView awayTeamVotePercentage;
    private TextView homeTeamVotePercentage;
    private TextView votingClosedView;
    private ProgressBar votePercentageBar;

    private FirebaseDatabase database;
    private DatabaseReference currentGameReference;
    private FirebaseAuth mAuth;
    private User currentUser;
    private boolean userHasVoted;

    private GameVotes votesForCurrentGame;
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

        awayQBStatsView = (TextView) findViewById(R.id.away_qb_stats);
        homeQBStatsView = (TextView) findViewById(R.id.home_qb_stats);
        awayRBStatsView = (TextView) findViewById(R.id.away_rb_stats);
        homeRBStatsView = (TextView) findViewById(R.id.home_rb_stats);
        awayWRStatsView1 = (TextView) findViewById(R.id.away_wr_stats);
        awayWRStatsView2 = (TextView) findViewById(R.id.away_wr_stats2);
        homeWRStatsView1 = (TextView) findViewById(R.id.home_wr_stats);
        homeWRStatsView2 = (TextView) findViewById(R.id.home_wr_stats2);

        awayVoteButton = (Button) findViewById(R.id.vote_for_away_button);
        homeVoteButton = (Button) findViewById(R.id.vote_for_home_button);
        awayTeamVotePercentage = (TextView) findViewById(R.id.away_team_percentage);
        homeTeamVotePercentage = (TextView) findViewById(R.id.home_team_percentage);
        votingClosedView = (TextView) findViewById(R.id.no_votes_display);
        votePercentageBar = (ProgressBar) findViewById(R.id.vote_percentage_bar);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = new User(mAuth.getCurrentUser());

        final Intent intent = getIntent();
        gameScore = intent.getParcelableExtra(SCORE_INTENT_REFERENCE);
        game = gameScore.getGame();
        awayTeam = game.getAwayTeam();
        homeTeam = game.getHomeTeam();

        setFonts();
        setTeamProperties();
        setViewBasedOnGameStatus();
        setColorsOfGameView();
        readValuesFromDatabase();

        // Gets the String that can query at the player logs endpoint and get stats of players
        String gameIDQuery = FormattingUtilities.reformatInformationForGameRequest
                (game.getDate(), awayTeam.getAbbreviation(), homeTeam.getAbbreviation());

        new PlayerStatsAsyncTask().execute(gameIDQuery);
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
     * Sets initial team properties Views with names, scores, and logos.
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

        // checks if the game hasn't been played, then it changes the status view of the score
        if (gameScore.getIsUnplayed().equals(TRUE)) {
            votingClosedView.setVisibility(View.INVISIBLE);
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
     * Sets the color of the view based on the color of the home team.
     */
    private void setColorsOfGameView() {

        // Converts home team image into a Bitmap to generate a Palette, which can get the vibrant,
        // muted, or dominant colors from the image.
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
                awayVoteButton.setBackgroundColor(secondColor);
                homeVoteButton.setBackgroundColor(secondColor);
                votePercentageBar.getProgressDrawable().setColorFilter(secondColor,android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        });
    }

    /**
     * Depending on whether or not the user has already voted, method either adds user's vote or
     * removes their vote for the away team in the corresponding game.
     *
     * @param v This is an onClick method.
     */
    public void changeVoteForAwayTeam(View v) {
        if (!userHasVoted) {
            votesForCurrentGame.voteForAwayTeam(currentUser);
        } else {
            votesForCurrentGame.unvoteForAwayTeam(currentUser);
        }
        currentGameReference.setValue(votesForCurrentGame);
    }

    /**
     * Depending on whether or not the user has already voted, method either adds user's vote or
     * removes their vote for the home team in the corresponding game.
     *
     * @param v This is an onClick method.
     */
    public void changeVoteForHomeTeam(View v) {
        if (!userHasVoted) {
            votesForCurrentGame.voteForHomeTeam(currentUser);
        } else {
            votesForCurrentGame.unvoteForHomeTeam(currentUser);
        }
        currentGameReference.setValue(votesForCurrentGame);
    }

    /**
     * Sets up an initial onClickListener and sets views based on that.
     */
    private void readValuesFromDatabase() {
        currentGameReference = database.getReference(GAMES_DB_REFERENCE).child(game.getID());
        currentGameReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                votesForCurrentGame = dataSnapshot.getValue(GameVotes.class);

                // if there are no votes yet, set the buttons and make a new GameVotes object
                if (votesForCurrentGame == null) {
                    currentGameReference.setValue(new GameVotes());
                    awayTeamVotePercentage.setText(NO_VOTES);
                    homeTeamVotePercentage.setText(NO_VOTES);
                    votePercentageBar.setVisibility(View.INVISIBLE);

                // otherwise get the number of votes for each team and change the views accordingly
                } else {
                    int numAwayTeamVotes = votesForCurrentGame.getNumAwayTeamVotes();
                    int numHomeTeamVotes = votesForCurrentGame.getNumHomeTeamVotes();
                    changeViewsBasedOnVotes(numAwayTeamVotes, numHomeTeamVotes);
                }
                changeButtonsText();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    /**
     * Change the Views based on how many votes there are for each team.
     * @param numAwayTeamVotes int: The number of votes for the away team
     * @param numHomeTeamVotes int: The number of votes for the home team
     */
    private void changeViewsBasedOnVotes(int numAwayTeamVotes, int numHomeTeamVotes) {

        if (numAwayTeamVotes == 0 && numHomeTeamVotes == 0) {
            awayTeamVotePercentage.setText(NO_VOTES);
            homeTeamVotePercentage.setText(NO_VOTES);
            votePercentageBar.setVisibility(View.INVISIBLE);

        } else {
            int percentageOfAwayTeamVotes = (int) (((double) numAwayTeamVotes * 100)
                    / (numAwayTeamVotes + numHomeTeamVotes));

            final String AWAY_TEAM_PERCENTAGE = String.valueOf(
                    percentageOfAwayTeamVotes) + PERCENT_SYMBOL;
            final String HOME_TEAM_PERCENTAGE = String.valueOf(
                    TOTAL_PERCENT - percentageOfAwayTeamVotes) + PERCENT_SYMBOL;

            awayTeamVotePercentage.setText(AWAY_TEAM_PERCENTAGE);
            homeTeamVotePercentage.setText(HOME_TEAM_PERCENTAGE);
            votePercentageBar.setVisibility(View.VISIBLE);
            votePercentageBar.setProgress(percentageOfAwayTeamVotes);
        }
    }

    /**
     * Adjusts the text of the buttons based on whether or not the user has voted yet.
     */
    private void changeButtonsText() {

        awayTeamVotePercentage.setTextSize(TEXT_SIZE_PERCENT);
        homeTeamVotePercentage.setTextSize(TEXT_SIZE_PERCENT);

        if (gameScore.getIsUnplayed().equals(FALSE)) {
            awayVoteButton.setClickable(false);
            homeVoteButton.setClickable(false);
            awayVoteButton.setVisibility(View.INVISIBLE);
            homeVoteButton.setVisibility(View.INVISIBLE);

        } else if (votesForCurrentGame.hasVotedForAwayTeam(currentUser)) {
            awayVoteButton.setText(UNVOTE);
            homeVoteButton.setVisibility(View.INVISIBLE);
            homeVoteButton.setClickable(false);
            userHasVoted = true;

        } else if (votesForCurrentGame.hasVotedForHomeTeam(currentUser)) {
            homeVoteButton.setText(UNVOTE);
            awayVoteButton.setVisibility(View.INVISIBLE);
            awayVoteButton.setClickable(false);
            userHasVoted = true;

        } else {
            awayVoteButton.setVisibility(View.VISIBLE);
            homeVoteButton.setVisibility(View.VISIBLE);
            awayVoteButton.setClickable(true);
            homeVoteButton.setClickable(true);
            awayVoteButton.setText(VOTE);
            homeVoteButton.setText(VOTE);
            userHasVoted = false;
        }

        if (awayTeamVotePercentage.getText().length() > LENGTH_OF_PERCENT) {
            awayTeamVotePercentage.setTextSize(TEXT_SIZE_NO_VOTES);
            homeTeamVotePercentage.setTextSize(TEXT_SIZE_NO_VOTES);
        }
    }

    /**
     * Sets the Views that show the stats for a Quarterback.
     *
     * @param qbStats List of stats that you can retrieve for a player.
     */
    private void setQBViews(PlayerStats[] qbStats) {

        // Checks if each stat in the provided stats corresponds to the home or away team and sets
        // views accordingly
        for (PlayerStats playerStats : qbStats) {
            if (playerStats.getTeam().getName().equals(awayTeam.getName())) {
                awayQBStatsView.setText(playerStats.getQBStatsAsString());
            } else {
                homeQBStatsView.setText(playerStats.getQBStatsAsString());
            }
        }
    }

    /**
     * Sets the Views that show the stats for a Runningback.
     *
     * @param rbStats List of stats that you can retrieve for a player.
     */
    private void setRBViews(PlayerStats[] rbStats) {

        // Checks if each stat in the provided stats corresponds to the home or away team and sets
        // views accordingly, only populates with the top stats and doesn't populate if the view
        // has already been set
        for (PlayerStats playerStats : rbStats) {
            if (playerStats.getTeam().getName().equals(awayTeam.getName())) {
                if (awayRBStatsView.getText().length() == 0 || awayRBStatsView.getText().equals(NO_STATS)) {
                    awayRBStatsView.setText(playerStats.getRBStatsAsString());
                }
            } else {
                if (homeRBStatsView.getText().length() == 0 || homeRBStatsView.getText().equals(NO_STATS)) {
                    homeRBStatsView.setText(playerStats.getRBStatsAsString());
                }
            }
        }
    }

    /**
     * Sets the Views that show the stats for the receivers.
     *
     * @param wrStats List of stats that you can retrieve for a player.
     */
    private void setWRViews(PlayerStats[] wrStats) {

        // Checks if each stat in the provided stats corresponds to the home or away team and sets
        // views accordingly, only populates with the top stats and doesn't populate if the view
        // has already been set
        for (PlayerStats playerStats : wrStats) {
            if (playerStats.getTeam().getName().equals(awayTeam.getName())) {
                if (awayWRStatsView1.getText().length() == 0 || awayWRStatsView1.getText().equals(NO_STATS)) {
                    awayWRStatsView1.setText(playerStats.getWRStatsAsString());
                } else if (awayWRStatsView2.getText().length() == 0) {
                    awayWRStatsView2.setText(playerStats.getWRStatsAsString());
                }
            } else {
                if (homeWRStatsView1.getText().length() == 0 || homeWRStatsView1.getText().equals(NO_STATS)) {
                    homeWRStatsView1.setText(playerStats.getWRStatsAsString());
                } else if (homeWRStatsView2.getText().length() == 0) {
                    homeWRStatsView2.setText(playerStats.getWRStatsAsString());
                }
            }
        }
    }

    /**
     * Asynchronous Task for getting stats about Quarterbacks, Runningbacks, and Recievers for display.
     */
    public class PlayerStatsAsyncTask extends AsyncTask<String, String, PlayerStats[][]> {

        @Override
        protected void onPostExecute(PlayerStats[][] playerStats) {
            setQBViews(playerStats[QB_STAT]);
            setRBViews(playerStats[RB_STAT]);
            setWRViews(playerStats[WR_STAT]);
        }

        @Override
        protected PlayerStats[][] doInBackground(String... formattedGameID) {
            return SportsDataUtility.getPlayerStats(formattedGameID[0]);
        }
    }
}
