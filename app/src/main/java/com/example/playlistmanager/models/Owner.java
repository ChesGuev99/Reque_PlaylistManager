package com.example.playlistmanager.models;

import java.util.List;

public class Owner {
    private List<User> users;

    public Owner(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
