package com.example.rohinbhasin.nflapp.JsonClasses;

/**
 * Class to house the Game Stats of the relevant players.
 */
public class GameStats {

    private PlayerStats[] gamelogs;

    public PlayerStats[] getGamelogs() {
        return gamelogs;
    }

    public void setGamelogs(PlayerStats[] gamelogs) {
        this.gamelogs = gamelogs;
    }
}
