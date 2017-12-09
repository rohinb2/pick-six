package com.example.rohinbhasin.nflapp.JsonClasses;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Class to house the votes for each team for a game.
 */
public class GameVotes {

    private ArrayList<FirebaseUser> votesForAwayTeam = new ArrayList<>();
    private ArrayList<FirebaseUser> votesForHomeTeam = new ArrayList<>();

    public ArrayList<FirebaseUser> getVotesForAwayTeam() {
        return votesForAwayTeam;
    }

    public ArrayList<FirebaseUser> getVotesForHomeTeam() {
        return votesForHomeTeam;
    }

    public void voteForAwayTeam(FirebaseUser user) {
        votesForAwayTeam.add(user);
    }

    public void voteForHomeTeam(FirebaseUser user) {
        votesForHomeTeam.add(user);
    }

    public void unvoteForAwayTeam(FirebaseUser user) {
        votesForAwayTeam.remove(user);
    }

    public void unvoteForHomeTeam(FirebaseUser user) {
        votesForHomeTeam.remove(user);
    }

    public boolean checkIfUserHasVoted(FirebaseUser user) {
        return votesForHomeTeam.contains(user) || votesForAwayTeam.contains(user);
    }
}
