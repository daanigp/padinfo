package com.daanigp.padinfo.Entity;

public class Torneo {
    private long id;
    private String name;
    private String city;
    private String imageURL;

    public Torneo() {
    }

    public Torneo(long id, String name, String city, String imageURL) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.imageURL = imageURL;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
