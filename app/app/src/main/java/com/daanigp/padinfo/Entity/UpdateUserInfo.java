package com.daanigp.padinfo.Entity;

public class UpdateUserInfo {
    private String name;
    private String lastname;
    private String email;
    private String imageURL;

    public UpdateUserInfo() {
    }

    public UpdateUserInfo(String name, String lastname, String email, String imageURL) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
