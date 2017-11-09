package com.example.rohinbhasin.nflapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class that parses the JSON of a Game between two teams and its scoring attributes.
 */

public class Score implements Parcelable {

    private Game game;
    private String isUnplayed;
    private String isInProgress;
    private String isCompleted;
    private String awayScore;
    private String homeScore;

    protected Score(Parcel in) {
        game = in.readParcelable(Game.class.getClassLoader());
        isUnplayed = in.readString();
        isInProgress = in.readString();
        isCompleted = in.readString();
        awayScore = in.readString();
        homeScore = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(game, flags);
        dest.writeString(isUnplayed);
        dest.writeString(isInProgress);
        dest.writeString(isCompleted);
        dest.writeString(awayScore);
        dest.writeString(homeScore);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

    public Game getGame() {
        return game;
    }

    public String getIsUnplayed() {
        return isUnplayed;
    }

    public String getIsInProgress() {
        return isInProgress;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public String getAwayScore() {
        return awayScore;
    }

    public String getHomeScore() {
        return homeScore;
    }
}
