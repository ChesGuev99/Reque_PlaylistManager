package com.example.playlistmanager.models;

import java.util.List;

public class TrackSearch {
        private Tracks tracks;

    public TrackSearch(Tracks items) {
        this.tracks = items;
    }

    public Tracks getItems() {
        return tracks;
    }

    public void setItems(Tracks items) {
        this.tracks = items;
    }

}
