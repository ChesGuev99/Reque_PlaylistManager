package com.example.playlistmanager.models;

import java.util.List;

public class GetPlaylistTracks {
    private String href;
    private Integer limit;
    private Integer offset;
    private Integer total;
    private List<PlaylistTracks> items;

    public GetPlaylistTracks(String href, Integer limit, Integer offset, Integer total, List<PlaylistTracks> items) {
        this.href = href;
        this.limit = limit;
        this.offset = offset;
        this.total = total;
        this.items = items;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<PlaylistTracks> getItems() {
        return items;
    }

    public void setItems(List<PlaylistTracks> items) {
        this.items = items;
    }
}
