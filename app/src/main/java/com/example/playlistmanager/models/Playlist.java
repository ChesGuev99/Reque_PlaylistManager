package com.example.playlistmanager.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Playlist {

    private Boolean isCollaborative;
    private String description;
    // xternal urls
    private String href;
    private String id;
    private List<String> imgs; // TODO: CHANGE TYPE ADD
    private String name;
    // owner
    private Boolean isPublic;
    private String snapshot_id;

    public Playlist(Boolean isCollaborative, String description, String href, String id, List<String> imgs, String name, Boolean isPublic, String snapshot_id) {
        this.isCollaborative = isCollaborative;
        this.description = description;
        this.href = href;
        this.id = id;
        this.imgs = imgs;
        this.name = name;
        this.isPublic = isPublic;
        this.snapshot_id = snapshot_id;
    }

    public Playlist(String name){
        this.name = name;
        this.isCollaborative = false;
        this.description = "";
        this.isPublic = false;
    }

    public Boolean getCollaborative() {
        return isCollaborative;
    }

    public void setCollaborative(Boolean collaborative) {
        isCollaborative = collaborative;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
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

    public String getSnapshot_id() {
        return snapshot_id;
    }

    public void setSnapshot_id(String snapshot_id) {
        this.snapshot_id = snapshot_id;
    }


}
