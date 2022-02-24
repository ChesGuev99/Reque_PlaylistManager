package com.example.playlistmanager.models;

public class AddSongResponse {

    private String snapshot_id;

    public AddSongResponse(String snapshot_id) {
        this.snapshot_id = snapshot_id;
    }

    public String getSnapshot_id() {
        return snapshot_id;
    }

    public void setSnapshot_id(String snapshot_id) {
        this.snapshot_id = snapshot_id;
    }
}
