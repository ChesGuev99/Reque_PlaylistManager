package com.example.playlistmanager.models;

import java.util.List;

public class User {
    private String country;
    private String displayName;
    private String email;
    private String href;
    private String id;
    private String product;
    private String type;
    private String uri;

    public User(String country, String displayName, String email, String href, String id, String product, String type, String uri) {
        this.country = country;
        this.displayName = displayName;
        this.email = email;
        this.href = href;
        this.id = id;
        this.product = product;
        this.type = type;
        this.uri = uri;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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


