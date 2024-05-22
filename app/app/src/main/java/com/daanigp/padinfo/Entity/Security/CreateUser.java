package com.daanigp.padinfo.Entity.Security;

import java.util.List;

public class CreateUser {
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String email;
    private List<Long> rolIds;

    public CreateUser() {
    }

    public CreateUser(String username, String password, String name, String lastname, String email, List<Long> rolIds) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.rolIds = rolIds;
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

    public List<Long> getRolIds() {
        return rolIds;
    }

    public void setRolIds(List<Long> rolIds) {
        this.rolIds = rolIds;
    }
}
