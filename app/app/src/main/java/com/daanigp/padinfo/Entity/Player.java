package com.daanigp.padinfo.Entity;

public class Player {
    private long id;
    private String gender;
    private int rankingPosition;
    private String points;
    private String name;
    private String imageURL;

    public Player() {
    }

    public Player(long id, String gender, int rankingPosition, String points, String name, String imageURL) {
        this.id = id;
        this.gender = gender;
        this.rankingPosition = rankingPosition;
        this.points = points;
        this.name = name;
        this.imageURL = imageURL;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getRankingPosition() {
        return rankingPosition;
    }

    public void setRankingPosition(int rankingPosition) {
        this.rankingPosition = rankingPosition;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
