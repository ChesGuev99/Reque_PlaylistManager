package com.example.playlistmanager.models;

import java.util.List;

public class UserPlaylistResponse {

    private List<Playlist> items;
    private String href;
    private Integer limit;
    private String next;
    private Integer offset;
    private String previous;
    private Integer total;


    public UserPlaylistResponse(List<Playlist> items, String href) {
        this.items = items;
        this.href = href;
    }

    public List<Playlist> getItems() {
        return items;
    }

    public void setItems(List<Playlist> items) {
        this.items = items;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
