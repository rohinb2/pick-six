package com.example.rohinbhasin.nflapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rohinbhasin.nflapp.JsonClasses.Game;
import com.example.rohinbhasin.nflapp.JsonClasses.Score;
import com.example.rohinbhasin.nflapp.JsonClasses.Team;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Display adapter that populates the Recycler View holders.
 */
public class ScoreDisplayAdapter extends RecyclerView.Adapter {

    private static final String TRUE = "true";
    private List<Score> listOfScores = new ArrayList<>();

    public ScoreDisplayAdapter(List<Score> scores) {
        listOfScores.addAll(scores);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // inflate the layout of a restaurant item that will be within the recycler view
        View scoreItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_score, parent, false);
        return new ViewHolder(scoreItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewOfGame = (ViewHolder) holder;

        final Score GAME_SCORE = listOfScores.get(position);
        final Game GAME = GAME_SCORE.getGame();
        final Team AWAY_TEAM = GAME.getAwayTeam();
        final Team HOME_TEAM = GAME.getHomeTeam();

        viewOfGame.awayTeamView.setText(AWAY_TEAM.getName());
        viewOfGame.awayCityView.setText(AWAY_TEAM.getCity());
        viewOfGame.homeTeamView.setText(HOME_TEAM.getName());
        viewOfGame.homeCityView.setText(HOME_TEAM.getCity());

        final String SCORE_DISPLAY = GAME_SCORE.getAwayScore() + " - " + GAME_SCORE.getHomeScore();
        final String FINAL_SCORE = "Final\n" + SCORE_DISPLAY;
        final String CURRENT_SCORE = "Current\n" + SCORE_DISPLAY;
        final String DATE_DISPLAY = FormattingUtilities.reformatDateForDisplay(GAME.getDate()) + "\n" + GAME.getTime();

        if (GAME_SCORE.getIsUnplayed().equals(TRUE)) {
            viewOfGame.statusView.setText(DATE_DISPLAY);
        } else if (GAME_SCORE.getIsInProgress().equals(TRUE)){
            viewOfGame.statusView.setText(CURRENT_SCORE);
        } else {
            viewOfGame.statusView.setText(FINAL_SCORE);
        }

        try {
            final Context context = viewOfGame.scoreView.getContext();
            final String AWAY_TEAM_URL = TeamLogos.getLogoURLForTeam(AWAY_TEAM.getName());
            final String HOME_TEAM_URL = TeamLogos.getLogoURLForTeam(HOME_TEAM.getName());

            Picasso.with(context).load(AWAY_TEAM_URL).into(viewOfGame.awayImageView);
            Picasso.with(context).load(HOME_TEAM_URL).into(viewOfGame.homeImageView);

        } catch (Exception e) {
            e.printStackTrace();
        }

        viewOfGame.scoreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();
                Intent clickedForDetail = new Intent(context, GameActivity.class);
                clickedForDetail.putExtra("score", GAME_SCORE);
                context.startActivity(clickedForDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfScores.size();
    }

    /**
     * Class that holds the view and populates the Recycler View
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public View scoreView;
        public TextView statusView;
        public TextView awayTeamView;
        public TextView awayCityView;
        public ImageView awayImageView;
        public TextView homeTeamView;
        public TextView homeCityView;
        public ImageView homeImageView;


        public ViewHolder(View scoreView) {
            super(scoreView);
            this.scoreView = scoreView;
            this.statusView = (TextView) scoreView.findViewById(R.id.status_view);
            this.awayTeamView = (TextView) scoreView.findViewById(R.id.away_team_name);
            this.awayCityView = (TextView) scoreView.findViewById(R.id.away_team_city);
            this.awayImageView = (ImageView) scoreView.findViewById(R.id.away_team_logo);
            this.homeTeamView = (TextView) scoreView.findViewById(R.id.home_team_name);
            this.homeCityView = (TextView) scoreView.findViewById(R.id.home_team_city);
            this.homeImageView = (ImageView) scoreView.findViewById(R.id.home_team_logo);
        }
    }
}
