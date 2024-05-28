package com.daanigp.padinfo.Entity;

public class UpdateGame {
    private String namePlayer1;
    private String namePlayer2;
    private String namePlayer3;
    private String namePlayer4;
    private int set1PointsT1;
    private int set2PointsT1;
    private int set3PointsT1;
    private int set1PointsT2;
    private int set2PointsT2;
    private int set3PointsT2;
    private int winnerTeam;

    public UpdateGame() {
    }

    public UpdateGame(String namePlayer1, String namePlayer2, String namePlayer3, String namePlayer4, int set1PointsT1, int set2PointsT1, int set3PointsT1, int set1PointsT2, int set2PointsT2, int set3PointsT2, int winnerTeam) {
        this.namePlayer1 = namePlayer1;
        this.namePlayer2 = namePlayer2;
        this.namePlayer3 = namePlayer3;
        this.namePlayer4 = namePlayer4;
        this.set1PointsT1 = set1PointsT1;
        this.set2PointsT1 = set2PointsT1;
        this.set3PointsT1 = set3PointsT1;
        this.set1PointsT2 = set1PointsT2;
        this.set2PointsT2 = set2PointsT2;
        this.set3PointsT2 = set3PointsT2;
        this.winnerTeam = winnerTeam;
    }

    public String getNamePlayer1() {
        return namePlayer1;
    }

    public void setNamePlayer1(String namePlayer1) {
        this.namePlayer1 = namePlayer1;
    }

    public String getNamePlayer2() {
        return namePlayer2;
    }

    public void setNamePlayer2(String namePlayer2) {
        this.namePlayer2 = namePlayer2;
    }

    public String getNamePlayer3() {
        return namePlayer3;
    }

    public void setNamePlayer3(String namePlayer3) {
        this.namePlayer3 = namePlayer3;
    }

    public String getNamePlayer4() {
        return namePlayer4;
    }

    public void setNamePlayer4(String namePlayer4) {
        this.namePlayer4 = namePlayer4;
    }

    public int getSet1PointsT1() {
        return set1PointsT1;
    }

    public void setSet1PointsT1(int set1PointsT1) {
        this.set1PointsT1 = set1PointsT1;
    }

    public int getSet2PointsT1() {
        return set2PointsT1;
    }

    public void setSet2PointsT1(int set2PointsT1) {
        this.set2PointsT1 = set2PointsT1;
    }

    public int getSet3PointsT1() {
        return set3PointsT1;
    }

    public void setSet3PointsT1(int set3PointsT1) {
        this.set3PointsT1 = set3PointsT1;
    }

    public int getSet1PointsT2() {
        return set1PointsT2;
    }

    public void setSet1PointsT2(int set1PointsT2) {
        this.set1PointsT2 = set1PointsT2;
    }

    public int getSet2PointsT2() {
        return set2PointsT2;
    }

    public void setSet2PointsT2(int set2PointsT2) {
        this.set2PointsT2 = set2PointsT2;
    }

    public int getSet3PointsT2() {
        return set3PointsT2;
    }

    public void setSet3PointsT2(int set3PointsT2) {
        this.set3PointsT2 = set3PointsT2;
    }

    public int getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(int winnerTeam) {
        this.winnerTeam = winnerTeam;
    }
}
