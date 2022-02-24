package com.example.playlistmanager.models;


public class PlaylistTracks {
    private Track track;

    public PlaylistTracks(Track track) {
        this.track = track;
    }

    public Track getTrack() {
        return track;
    }

    public void setItems(Track items) {
        this.track = items;
    }
}
