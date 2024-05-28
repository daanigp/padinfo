package com.daanigp.padinfo.Entity;

public class CreateUpdateTournament {
    private String name;
    private String city;
    private String imageURL;

    public CreateUpdateTournament() {
    }

    public CreateUpdateTournament(String name, String city, String imageURL) {
        this.name = name;
        this.city = city;
        this.imageURL = imageURL;
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
