package com.example.rohinbhasin.nflapp.JsonClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a team in the score parsed from Json.
 */

public class Team implements Parcelable {

    private String ID;
    private String City;
    private String Name;
    private String Abbreviation;

    protected Team(Parcel in) {
        ID = in.readString();
        City = in.readString();
        Name = in.readString();
        Abbreviation = in.readString();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public String getID() {
        return ID;
    }

    public String getCity() {
        return City;
    }

    public String getName() {
        return Name;
    }

    public String getAbbreviation() {
        return Abbreviation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ID);
        parcel.writeString(City);
        parcel.writeString(Name);
        parcel.writeString(Abbreviation);
    }
}
