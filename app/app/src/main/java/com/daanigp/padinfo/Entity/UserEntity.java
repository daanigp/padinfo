package com.daanigp.padinfo.Entity;

import java.util.List;

public class UserEntity {
    private long id;
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String email;
    private String imageURL;

    private List<Long> rolIds;

    public UserEntity() {
    }

    public UserEntity(long id, String username, String password, String name, String lastname, String email, String imageURL, List<Long> rolIds) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.imageURL = imageURL;
        this.rolIds = rolIds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Long> getRolIds() {
        return rolIds;
    }

    public void setRolIds(List<Long> rolIds) {
        this.rolIds = rolIds;
    }
}
