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

    /**
     * Adds user to the list of users that have voted for the away team.
     * @param user User object to add to list.
     */
    public void voteForAwayTeam(User user) {
        votesForAwayTeam.add(user);
    }

    /**
     * Adds user to the list of users that have voted for the home team.
     * @param user User object to add to list.
     */
    public void voteForHomeTeam(User user) {
        votesForHomeTeam.add(user);
    }

    /**
     * Removes user from the list of users that have voted for the away team.
     * @param user User object to add to list.
     */
    public void unvoteForAwayTeam(User user) {
        votesForAwayTeam.remove(user);
    }

    /**
     * Removes user from the list of users that have voted for the home team.
     * @param user User object to add to list.
     */
    public void unvoteForHomeTeam(User user) {
        votesForHomeTeam.remove(user);
    }

    /**
     * Get the number of users that have voted for the away team.
     * @return Integer that is the size of the Voted For Away Team list.
     */
    public int getNumAwayTeamVotes() {
        return votesForAwayTeam.size();
    }

    /**
     * Get the number of users that have voted for the home team.
     * @return Integer that is the size of the Voted For Home Team list.
     */
    public int getNumHomeTeamVotes() {
        return votesForHomeTeam.size();
    }

    /**
     * Checks if the given User has already voted for the away team.
     *
     * @param user User to check if has voted.
     * @return boolean True if the user has voted for the away team, False otherwise.
     */
    public boolean hasVotedForAwayTeam(User user) {
        return votesForAwayTeam.contains(user);
    }

    /**
     * Checks if the given User has already voted for the home team.
     *
     * @param user User to check if has voted.
     * @return boolean True if the user has voted for the home team, False otherwise.
     */
    public boolean hasVotedForHomeTeam(User user) {
        return votesForHomeTeam.contains(user);
    }
}
