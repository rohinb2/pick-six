package com.example.rohinbhasin.nflapp;

import android.util.Base64;

import com.example.rohinbhasin.nflapp.JsonClasses.GameStatsWrapper;
import com.example.rohinbhasin.nflapp.JsonClasses.PlayerStats;
import com.example.rohinbhasin.nflapp.JsonClasses.Score;
import com.example.rohinbhasin.nflapp.JsonClasses.ScoreboardWrapper;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * A class for getting data for Scores for a the NFL app.
 */
public class SportsDataUtility {

    private static final String AUTHENTICATION_STRING = "r_bhasin7:rohin";
    private static final String REQUEST_FOR_SCOREBOARD = "https://api.mysportsfeeds.com/v1.1/pull/nfl/2017-regular/scoreboard.json?fordate=";
    private static final String PLAYER_STATS_REQUEST = "https://api.mysportsfeeds.com/v1.1/pull/nfl/2017-regular/player_gamelogs.json?game=";
    private static final String QB_REQUEST = "&sort=stats.passing-yds.d&limit=2";
    private static final String RB_REQUEST = "&sort=stats.rushing-yds.d&limit=10";
    private static final String WR_REQUEST = "&sort=stats.receiving-yds.d&limit=10";
    public static final int QB_STAT = 0;
    public static final int RB_STAT = 1;
    public static final int WR_STAT = 2;

    /**
     * Meat of the method for the class, gets scores for the week starting Thursday.
     *
     * @return ArrayList of Score objects for all the games occurring that week.
     */
    public static ArrayList<Score> getScoresForCurrentWeek() {
        ArrayList<Score> fullWeekScores = new ArrayList<>();
        final int DAY_OF_THE_WEEK = FormattingUtilities.getCurrentDayOfWeek();
        Calendar dateToStartFrom = Calendar.getInstance();

        switch (DAY_OF_THE_WEEK) {
            case 0:
                dateToStartFrom.add(Calendar.DATE, -3);
                addScoresStartingOnDay(fullWeekScores, dateToStartFrom);
                break;
            case 1:
                dateToStartFrom.add(Calendar.DATE, -4);
                addScoresStartingOnDay(fullWeekScores, dateToStartFrom);
                break;
            case 2:
                dateToStartFrom.add(Calendar.DATE, -5);
                addScoresStartingOnDay(fullWeekScores, dateToStartFrom);
                break;
            case 3:
                dateToStartFrom.add(Calendar.DATE, 1);
                addScoresStartingOnDay(fullWeekScores, dateToStartFrom);
                break;
            case 4:
                dateToStartFrom.add(Calendar.DATE, 0);
                addScoresStartingOnDay(fullWeekScores, dateToStartFrom);
                break;
            case 5:
                dateToStartFrom.add(Calendar.DATE, -1);
                addScoresStartingOnDay(fullWeekScores, dateToStartFrom);
                break;
            case 6:
                dateToStartFrom.add(Calendar.DATE, -2);
                addScoresStartingOnDay(fullWeekScores, dateToStartFrom);
                break;
        }
        return fullWeekScores;
    }

    /**
     * Adds all scores in a week starting on any given day of that week.
     *
     * @param scores ArrayList<Score>: A collection of scores to add to.
     * @param firstDay Calendar: a calendar object of the first day to start on.
     */
    private static void addScoresStartingOnDay(ArrayList<Score> scores, Calendar firstDay) {
        for (int i = 0; i < 7; i++) {
            addScoresFromDate(scores, firstDay);
            firstDay.add(Calendar.DATE, 1);
        }
    }

    /**
     * Adds all scores from an inputted Calendar Date to the scores array.
     *
     * @param scores ArrayList<Score>: a collection of scores for games in a given week.
     * @param calendarDate Calendar: the date to add scores from.
     */
    private static void addScoresFromDate(ArrayList<Score> scores, Calendar calendarDate) {
        Date d = calendarDate.getTime();
        Score[] scoresForDate = getScoresForDate(FormattingUtilities.getDateAsString(d));

        if (scoresForDate != null) {
            scores.addAll(Arrays.asList(scoresForDate));
        }
    }

    /**
     * Makes call to API with a given integer date and gets a JSON parsed result.
     *
     * @param dateForScores String: date in YYYYMMDD format that date to request for.
     * @return ScoreboardWrapper: Parsed JSON result.
     */
    private static ScoreboardWrapper getScoreboardForDate(String dateForScores) {

        String urlString = REQUEST_FOR_SCOREBOARD + dateForScores;

        try {
            URL url = new URL(urlString);
            byte[] data = AUTHENTICATION_STRING.getBytes("UTF-8");
            String encoding = Base64.encodeToString(data, Base64.DEFAULT).replace("\n", "");

            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            connection.connect();

            InputStream inStream = connection.getInputStream();
            InputStreamReader inStreamReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));

            Gson gson = new Gson();
            ScoreboardWrapper score = gson.fromJson(inStreamReader, ScoreboardWrapper.class);
            return score;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Takes in a date to get the NFL games and scores of games on that date.
     *
     * @param date String: Date in format YYYYMMDD
     * @return Score[]: the scores for the games on that day.
     */
    private static Score[] getScoresForDate(String date) {
        ScoreboardWrapper scoreboardForDate = getScoreboardForDate(date);
        if (scoreboardForDate != null) {
            return scoreboardForDate.getScoreboard().getGameScore();
        } else {
            return null;
        }
    }

    /**
     * Gets the stats for players in a specific game in a specific position.
     *
     * @param formattedGameID A game identification String formatted as YYYYMMDD-HOME-AWAY
     * @param playerPosition An integer suggesting which stats are needed, 0 for QB, 1 for RB, 2 for WR.
     * @return An array of player stats for that game.
     */
    private static PlayerStats[] getPositionStats(String formattedGameID, int playerPosition) {

        String urlString = PLAYER_STATS_REQUEST + formattedGameID;

        if (playerPosition == 0) {
            urlString += QB_REQUEST;
        } else if (playerPosition == 1) {
            urlString += RB_REQUEST;
        } else if (playerPosition == 2) {
            urlString += WR_REQUEST;
        }

        try {
            URL url = new URL(urlString);
            byte[] data = AUTHENTICATION_STRING.getBytes("UTF-8");
            String encoding = Base64.encodeToString(data, Base64.DEFAULT).replace("\n", "");

            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            connection.connect();

            InputStream inStream = connection.getInputStream();
            InputStreamReader inStreamReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));

            Gson gson = new Gson();
            GameStatsWrapper gameStatsWrapper = gson.fromJson(inStreamReader, GameStatsWrapper.class);

            return gameStatsWrapper.getPlayergamelogs().getGamelogs();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets the player stats for passing, rushing, and receiving as a 2D array.
     *
     * @param formattedGameID A game identification String formatted as YYYYMMDD-HOME-AWAY
     * @return a 2D array of all player stats for the game.
     */
    public static PlayerStats[][] getPlayerStats(String formattedGameID) {
        PlayerStats[][] allPositionStats = new PlayerStats[3][];

        allPositionStats[QB_STAT] = getPositionStats(formattedGameID, QB_STAT);
        allPositionStats[RB_STAT] = getPositionStats(formattedGameID, RB_STAT);
        allPositionStats[WR_STAT] = getPositionStats(formattedGameID, WR_STAT);

        return allPositionStats;
    }
}
