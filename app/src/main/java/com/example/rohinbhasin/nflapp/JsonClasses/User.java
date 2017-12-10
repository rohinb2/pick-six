package com.example.rohinbhasin.nflapp.JsonClasses;

import com.google.firebase.auth.FirebaseUser;

/**
 * User class for voting for games.
 */

public class User {

    private String id;
    private String name;
    private String email;

    public User() {

    }

    public User(FirebaseUser user) {
        id = user.getUid();
        name = user.getDisplayName();
        email = user.getEmail();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

}
