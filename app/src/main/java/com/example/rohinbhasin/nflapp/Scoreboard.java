package com.example.rohinbhasin.nflapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Scoreboard Class that has all the scores on a given day for parsing JSON.
 */

public class Scoreboard implements Parcelable {

    private String lastUpdatedOn;
    private Score[] gameScore;

    protected Scoreboard(Parcel in) {
        lastUpdatedOn = in.readString();
        gameScore = in.createTypedArray(Score.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lastUpdatedOn);
        dest.writeTypedArray(gameScore, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Scoreboard> CREATOR = new Creator<Scoreboard>() {
        @Override
        public Scoreboard createFromParcel(Parcel in) {
            return new Scoreboard(in);
        }

        @Override
        public Scoreboard[] newArray(int size) {
            return new Scoreboard[size];
        }
    };

    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public Score[] getGameScore() {
        return gameScore;
    }
}
