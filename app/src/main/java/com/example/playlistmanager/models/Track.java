package com.example.playlistmanager.models;

import java.util.List;

public class Track {
    private String href;
    private String id;
    private String name;
    private String preview_url;
    private int track_number;
    private String type;
    private String uri;
    private long duration_ms;
    private List<Artist> artists;

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public Track(String href, String id, String name, String preview_url, int track_number, String type, String uri, long duration_ms) {
        this.href = href;
        this.id = id;
        this.name = name;
        this.preview_url = preview_url;
        this.track_number = track_number;
        this.type = type;
        this.uri = uri;
        this.duration_ms = duration_ms;
    }

    public long getDuration_ms() {
        return duration_ms;
    }

    public void setDuration_ms(long duration_ms) {
        this.duration_ms = duration_ms;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public int getTrack_number() {
        return track_number;
    }

    public void setTrack_number(int track_number) {
        this.track_number = track_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}

