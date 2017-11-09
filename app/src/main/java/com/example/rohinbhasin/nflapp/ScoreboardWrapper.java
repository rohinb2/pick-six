package com.example.rohinbhasin.nflapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Wrapper to parse Scoreboard object from JSON.
 */

public class ScoreboardWrapper implements Parcelable {

    private Scoreboard scoreboard;

    protected ScoreboardWrapper(Parcel in) {
        scoreboard = in.readParcelable(Scoreboard.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(scoreboard, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ScoreboardWrapper> CREATOR = new Creator<ScoreboardWrapper>() {
        @Override
        public ScoreboardWrapper createFromParcel(Parcel in) {
            return new ScoreboardWrapper(in);
        }

        @Override
        public ScoreboardWrapper[] newArray(int size) {
            return new ScoreboardWrapper[size];
        }
    };

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
