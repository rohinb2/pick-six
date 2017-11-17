package com.example.rohinbhasin.nflapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rohinbhasin.nflapp.JsonClasses.Score;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ScoreDisplayAdapter extends RecyclerView.Adapter {

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

        // cast the view holder so that we can access its extended variables
        ViewHolder viewOfGame = (ViewHolder) holder;
        final Score gameScore = listOfScores.get(position);
        viewOfGame.gameCompetitorsView.setText(gameScore.getGame().getAwayTeam().getName() + " vs. " + gameScore.getGame().getHomeTeam().getName());
        viewOfGame.gameTimeView.setText(gameScore.getGame().getDate());

    }

    @Override
    public int getItemCount() {
        return listOfScores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View scoreView;
        public TextView gameCompetitorsView;
        public TextView gameTimeView;


        public ViewHolder(View scoreView) {
            super(scoreView);
            this.scoreView = scoreView;
            this.gameCompetitorsView = (TextView) scoreView.findViewById(R.id.teams_competing);
            this.gameTimeView = (TextView) scoreView.findViewById(R.id.time_of_game);
        }

    }
}
