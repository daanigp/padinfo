package com.daanigp.padinfo;

public class Torneo {
    private Long id;
    private String name;
    private String city;
    private String imageURL;

    public Torneo() {
    }

    public Torneo(Long id, String name, String city, String imageURL) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.imageURL = imageURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
