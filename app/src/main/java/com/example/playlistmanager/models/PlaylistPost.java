package com.example.playlistmanager.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaylistPost {
    private String name;
    private String description;
    @SerializedName("public")
    private Boolean isPublic;

    public PlaylistPost(String name, String description , Boolean isPublic) {
        this.description = description;
        this.name = name;
        this.isPublic = isPublic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }
}
