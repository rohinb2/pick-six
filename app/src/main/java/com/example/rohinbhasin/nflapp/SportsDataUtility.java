package com.example.rohinbhasin.nflapp;

import android.telephony.CellIdentityCdma;
import android.util.Base64;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rohinbhasin on 11/9/17.
 */

public class SportsDataUtility {

    private static final int END_INDEX_FOR_DATE = 8;
    private static final int END_INDEX_FOR_DAY = 3;

    public static void main(String[] args) {
        ArrayList<Score> scores = getScoresForCurrentWeek();
        for (Score s : scores) {
            System.out.println(s.getGame().getAwayTeam().getName() + " vs " + s.getGame().getHomeTeam().getName());
        }
    }

    private static int getDateAsInteger(Date dateToFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String formattedTime = dateFormat.format(dateToFormat);
        String date = formattedTime.substring(0, END_INDEX_FOR_DATE);
        return Integer.valueOf(date);
    }

    /**
     * Gets what day of the week it is (e.g. Mon, Tue, etc)
     *
     * @return int: an integer that represents what day of the week it is.
     */
    private static int getCurrentDayOfWeek() {

        final HashMap<String, Integer> DAYS_AS_INTEGERS = new HashMap<String, Integer>() {
            {
                put("Sun", 0);
                put("Mon", 1);
                put("Tue", 2);
                put("Wed", 3);
                put("Thu", 4);
                put("Fri", 5);
                put("Sat", 6);
            }
        };
        Calendar c = Calendar.getInstance();
        return DAYS_AS_INTEGERS.get(c.getTime().toString().substring(0, END_INDEX_FOR_DAY));
    }

    public static ArrayList<Score> getScoresForCurrentWeek() {
        ArrayList<Score> fullWeekScores = new ArrayList<>();
        final int DAY_OF_THE_WEEK = getCurrentDayOfWeek();

        switch (DAY_OF_THE_WEEK) {
            case 0:
                addScoresBasedOnDay(fullWeekScores, 0, 1, -4);
                break;
            case 1:
                addScoresBasedOnDay(fullWeekScores, 0, -1, -3);
                break;
            case 2:
                addScoresBasedOnDay(fullWeekScores, -1, -2, -3);
                break;
            case 3:
                addScoresBasedOnDay(fullWeekScores, 1, 3, 1);
                break;
            case 4:
                addScoresBasedOnDay(fullWeekScores, 0, 3, 1);
                break;
            case 5:
                addScoresBasedOnDay(fullWeekScores, 2, 1, -4);
                break;
            case 6:
                addScoresBasedOnDay(fullWeekScores, 1, 2, -4);
        }
        return fullWeekScores;
    }

    private static void addScoresBasedOnDay(ArrayList<Score> scores, int firstDay, int secondDay, int thirdDay) {
        Calendar currentDate = Calendar.getInstance();

        currentDate.add(Calendar.DATE, firstDay);
        addScoresFromADate(scores, currentDate);

        currentDate.add(Calendar.DATE, secondDay);
        addScoresFromADate(scores, currentDate);

        currentDate.add(Calendar.DATE, thirdDay);
        addScoresFromADate(scores, currentDate);
    }

    private static void addScoresFromADate(ArrayList<Score> scores, Calendar calendarDate) {
        Date d = calendarDate.getTime();
        Score[] scoresForDate = getScoresForDate(getDateAsInteger(d));
        scores.addAll(Arrays.asList(scoresForDate));
    }

    /**
     * Makes call to API with a given integer date and gets a JSON parsed result.
     *
     * @param dateForScores int: date in YYYYMMDD format that represents today's date.
     * @return ScoreboardWrapper: Parsed JSON result.
     */
    private static ScoreboardWrapper getScoreboardForDate(int dateForScores) {

        final String AUTHENTICATION_STRING = "r_bhasin7:rohin";
        String urlString = "https://api.mysportsfeeds.com/v1.1/pull/nfl/2017-regular/scoreboard.json?fordate=" + dateForScores;

        // connect to the URL using URLConnection
        try {

            URL url = new URL(urlString);
            byte[] data = AUTHENTICATION_STRING.getBytes("UTF-8");
            String encoding = RohinEncode.encodeToString(data, RohinEncode.DEFAULT).replace("\n", "");

            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
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
     *
     *
     * @param dateForScores
     * @return
     */
    private static Score[] getScoresForDate(int dateForScores) {
        return getScoreboardForDate(dateForScores).getScoreboard().getGameScore();
    }
}
