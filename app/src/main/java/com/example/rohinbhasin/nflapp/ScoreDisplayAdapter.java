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
import com.squareup.picasso.Picasso;

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
        View restaurantItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant, parent, false);
        return new ViewHolder(restaurantItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // cast the view holder so that we can access its extended variables
        ViewHolder viewOfRestaurant = (ViewHolder) holder;
        final Score gameScore = listOfScores.get(position);
        viewOfRestaurant.restaurantNameView.setText(restaurant.getName());
        viewOfRestaurant.restaurantLocationView.setText(restaurant.getLocation().getAddress());
        viewOfRestaurant.restaurantCuisineView.setText(restaurant.getCuisines());

    }

    @Override
    public int getItemCount() {
        return listOfScores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View restaurantView;
        public TextView restaurantNameView;
        public TextView restaurantLocationView;
        public TextView restaurantCuisineView;
        public ImageView restaurantImageView;

        public ViewHolder(View restaurantView) {
            super(restaurantView);
            this.restaurantView = restaurantView;
            this.restaurantNameView = (TextView) restaurantView.findViewById(R.id.restaurant_name);
            this.restaurantLocationView = (TextView) restaurantView.findViewById(R.id.restaurant_location);
            this.restaurantCuisineView = (TextView) restaurantView.findViewById(R.id.restaurant_cuisine);
            this.restaurantImageView = (ImageView) restaurantView.findViewById(R.id.restaurant_image);
        }

    }
}
