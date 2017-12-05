package com.example.rohinbhasin.nflapp;

import java.util.HashMap;

/**
 * Contains a HashMap with the logo URL for any given team.
 */
public class TeamLogos {

    private static final HashMap<String, String> LOGO_URLS;

    static {
        LOGO_URLS = new HashMap<>();
        LOGO_URLS.put("Cardinals", "http://content.sportslogos.net/logos/7/177/full/kwth8f1cfa2sch5xhjjfaof90.png");
        LOGO_URLS.put("Falcons", "http://content.sportslogos.net/logos/7/173/full/299.png");
        LOGO_URLS.put("Ravens", "http://content.sportslogos.net/logos/7/153/full/318.png");
        LOGO_URLS.put("Bills", "http://content.sportslogos.net/logos/7/149/full/n0fd1z6xmhigb0eej3323ebwq.png");
        LOGO_URLS.put("Panthers", "http://content.sportslogos.net/logos/7/174/full/f1wggq2k8ql88fe33jzhw641u.png");
        LOGO_URLS.put("Bears", "http://content.sportslogos.net/logos/7/169/full/364.png");
        LOGO_URLS.put("Bengals", "http://content.sportslogos.net/logos/7/154/full/403.png");
        LOGO_URLS.put("Browns", "http://content.sportslogos.net/logos/7/155/full/7855_cleveland_browns-primary-2015.png");
        LOGO_URLS.put("Cowboys", "http://content.sportslogos.net/logos/7/165/full/406.png");
        LOGO_URLS.put("Broncos", "http://content.sportslogos.net/logos/7/161/full/9ebzja2zfeigaziee8y605aqp.png");
        LOGO_URLS.put("Lions", "http://content.sportslogos.net/logos/7/170/full/1398_detroit_lions-primary-2017.png");
        LOGO_URLS.put("Packers", "http://content.sportslogos.net/logos/7/171/full/dcy03myfhffbki5d7il3.png");
        LOGO_URLS.put("Texans", "http://content.sportslogos.net/logos/7/157/full/570.png");
        LOGO_URLS.put("Colts", "http://content.sportslogos.net/logos/7/158/full/593.png");
        LOGO_URLS.put("Jaguars", "http://content.sportslogos.net/logos/7/159/full/8856_jacksonville_jaguars-alternate-2013.png");
        LOGO_URLS.put("Chiefs", "http://content.sportslogos.net/logos/7/162/full/857.png");
        LOGO_URLS.put("Chargers", "http://content.sportslogos.net/logos/7/6446/full/2415_los_angeles__chargers-primary-2017.png");
        LOGO_URLS.put("Rams", "http://content.sportslogos.net/logos/7/5941/full/7953_los_angeles_rams-primary-2017.png");
        LOGO_URLS.put("Dolphins", "http://content.sportslogos.net/logos/7/150/full/4105_miami_dolphins-primary-2013.png");
        LOGO_URLS.put("Vikings", "http://content.sportslogos.net/logos/7/172/full/2704_minnesota_vikings-primary-2013.png");
        LOGO_URLS.put("Patriots", "http://content.sportslogos.net/logos/7/151/full/y71myf8mlwlk8lbgagh3fd5e0.gif");
        LOGO_URLS.put("Saints", "http://content.sportslogos.net/logos/7/175/full/907.png");
        LOGO_URLS.put("Giants", "http://content.sportslogos.net/logos/7/166/full/919.gif");
        LOGO_URLS.put("Jets", "http://content.sportslogos.net/logos/7/152/full/v7tehkwthrwefgounvi7znf5k.png");
        LOGO_URLS.put("Raiders", "http://content.sportslogos.net/logos/7/163/full/g9mgk6x3ge26t44cccm9oq1vl.png");
        LOGO_URLS.put("Eagles", "http://content.sportslogos.net/logos/7/167/full/960.png");
        LOGO_URLS.put("Steelers", "http://content.sportslogos.net/logos/7/156/full/970.png");
        LOGO_URLS.put("49ers", "http://content.sportslogos.net/logos/7/179/full/9455_san_francisco_49ers-primary-2009.png");
        LOGO_URLS.put("Seahawks", "http://content.sportslogos.net/logos/7/180/full/pfiobtreaq7j0pzvadktsc6jv.png");
        LOGO_URLS.put("Buccaneers", "http://content.sportslogos.net/logos/7/176/full/3670_tampa_bay_buccaneers-primary-2014.png");
        LOGO_URLS.put("Titans", "http://content.sportslogos.net/logos/7/160/full/1053.png");
        LOGO_URLS.put("Redskins", "http://content.sportslogos.net/logos/7/168/full/im5xz2q9bjbg44xep08bf5czq.png");
    }

    public static String getLogoURLForTeam(String team) {
        return LOGO_URLS.get(team);
    }
}
