package com.daanigp.padinfo.Entity;

public class CreateUpdatePlayer {
    private Integer rankingPosition;
    private String points;
    private String name;
    private String imageURL;
    private String gender;

    public CreateUpdatePlayer() {
    }

    public CreateUpdatePlayer(Integer rankingPosition, String points, String name, String imageURL, String gender) {
        this.rankingPosition = rankingPosition;
        this.points = points;
        this.name = name;
        this.imageURL = imageURL;
        this.gender = gender;
    }

    public Integer getRankingPosition() {
        return rankingPosition;
    }

    public void setRankingPosition(Integer rankingPosition) {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
