package com.example.rohinbhasin.nflapp.JsonClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a Game object when parsed from json.
 */

public class Game implements Parcelable {

    private String ID;
    private String week;
    private String scheduleStatus;
    private String originalDate;
    private String originalTime;
    private String delayedOrPostponedReason;
    private String date;
    private Team awayTeam;
    private Team homeTeam;
    private String location;

    protected Game(Parcel in) {
        ID = in.readString();
        week = in.readString();
        scheduleStatus = in.readString();
        originalDate = in.readString();
        originalTime = in.readString();
        delayedOrPostponedReason = in.readString();
        date = in.readString();
        awayTeam = in.readParcelable(Team.class.getClassLoader());
        homeTeam = in.readParcelable(Team.class.getClassLoader());
        location = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public String getID() {
        return ID;
    }

    public String getWeek() {
        return week;
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public String getOriginalDate() {
        return originalDate;
    }

    public String getOriginalTime() {
        return originalTime;
    }

    public String getDelayedOrPostponedReason() {
        return delayedOrPostponedReason;
    }

    public String getDate() {
        return date;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ID);
        parcel.writeString(week);
        parcel.writeString(scheduleStatus);
        parcel.writeString(originalDate);
        parcel.writeString(originalTime);
        parcel.writeString(delayedOrPostponedReason);
        parcel.writeString(date);
        parcel.writeParcelable(awayTeam, i);
        parcel.writeParcelable(homeTeam, i);
        parcel.writeString(location);
    }
}
