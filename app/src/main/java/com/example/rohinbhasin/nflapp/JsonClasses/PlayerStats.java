package com.example.rohinbhasin.nflapp.JsonClasses;

/**
 * Class to house the Stats and Information for a specific player.
 */

public class PlayerStats {

    private Player player;
    private StatList stats;
    private Team team;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public StatList getStats() {
        return stats;
    }

    public void setStats(StatList stats) {
        this.stats = stats;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Summarizes all the passing stats as a String
     *
     * @return String with the players name, completions, attempts, pass yards, and touchdowns
     */
    public String getQBStatsAsString() {

        StringBuilder qbStats = new StringBuilder();
        qbStats.append(player.getFirstName());
        qbStats.append(" ");
        qbStats.append(player.getLastName());
        qbStats.append(": ");
        qbStats.append(stats.getPassCompletions().getText());
        qbStats.append("-");
        qbStats.append(stats.getPassAttempts().getText());
        qbStats.append(", ");
        qbStats.append(stats.getPassYards().getText());
        qbStats.append(" YDS, ");
        qbStats.append(stats.getPassTD().getText());
        qbStats.append(" TD");

        return qbStats.toString();
    }

    /**
     * Summarizes all the rushing stats as a String
     *
     * @return String with the players name, rush attempts, rushing yards, and touchdowns
     */
    public String getRBStatsAsString() {

        StringBuilder rbStats = new StringBuilder();
        rbStats.append(player.getFirstName());
        rbStats.append(" ");
        rbStats.append(player.getLastName());
        rbStats.append(": ");
        rbStats.append(stats.getRushAttempts().getText());
        rbStats.append(" CAR, ");
        rbStats.append(stats.getRushYards().getText());
        rbStats.append(" YDS, ");
        rbStats.append(stats.getRushTD().getText());
        rbStats.append(" TD");

        return rbStats.toString();
    }

    /**
     * Summarizes all the receiving stats as a String
     *
     * @return String with the players name, receptions, receiving yards, and touchdowns
     */
    public String getWRStatsAsString() {

        StringBuilder wrStats = new StringBuilder();
        wrStats.append(player.getFirstName());
        wrStats.append(" ");
        wrStats.append(player.getLastName());
        wrStats.append(": ");
        wrStats.append(stats.getReceptions().getText());
        wrStats.append(" REC, ");
        wrStats.append(stats.getRecYards().getText());
        wrStats.append(" YDS, ");
        wrStats.append(stats.getRecTD().getText());
        wrStats.append(" TD");

        return wrStats.toString();
    }
}

