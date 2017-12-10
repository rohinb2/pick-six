package com.example.rohinbhasin.nflapp.JsonClasses;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Class to house the votes for each team for a game.
 */
public class GameVotes {

    private ArrayList<User> votesForAwayTeam;
    private ArrayList<User> votesForHomeTeam;

    public GameVotes() {
        votesForAwayTeam = new ArrayList<>();
        votesForHomeTeam = new ArrayList<>();
    }

    public ArrayList<User> getVotesForAwayTeam() {
        return votesForAwayTeam;
    }

    public ArrayList<User> getVotesForHomeTeam() {
        return votesForHomeTeam;
    }

    public void voteForAwayTeam(User user) {
        votesForAwayTeam.add(user);
    }

    public void voteForHomeTeam(User user) {
        votesForHomeTeam.add(user);
    }

    public void unvoteForAwayTeam(User user) {
        votesForAwayTeam.remove(user);
    }

    public void unvoteForHomeTeam(User user) {
        votesForHomeTeam.remove(user);
    }

    public int getNumAwayTeamVotes() {
        return votesForAwayTeam.size();
    }

    public int getNumHomeTeamVotes() {
        return votesForHomeTeam.size();
    }

    public boolean hasVotedForAwayTeam(User user) {
        return votesForAwayTeam.contains(user);
    }

    public boolean hasVotedForHomeTeam(User user) {
        return votesForHomeTeam.contains(user);
    }
}
