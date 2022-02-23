package com.example.playlistmanager.models;

import java.util.List;

public class Tracks {
    private List<Track> items;
    private String href;
    private int limit;
    private String next;
    private int offset;
    private int total;

    public Tracks(List<Track> items, String href, int limit, String next, int offset, int total) {
        this.items = items;
        this.href = href;
        this.limit = limit;
        this.next = next;
        this.offset = offset;
        this.total = total;
    }

    public List<Track> getItems() {
        return items;
    }

    public void setItems(List<Track> items) {
        this.items = items;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
