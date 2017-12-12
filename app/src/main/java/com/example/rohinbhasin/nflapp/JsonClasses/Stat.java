package com.example.rohinbhasin.nflapp.JsonClasses;

import com.google.gson.annotations.SerializedName;

/**
 * Class to hold a specific stat like Pass Attempts or Total Fumbles
 */

public class Stat {

    private @SerializedName("#text") String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
