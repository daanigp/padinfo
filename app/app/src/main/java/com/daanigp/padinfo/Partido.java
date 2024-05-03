package com.daanigp.padinfo;

public class Partido {
    private int idGame;
    private String user;
    private String player1;
    private String player2;
    private String player3;
    private String player4;
    private int set1PointsT1;
    private int set2PointsT1;
    private int set3PointsT1;
    private int set1PointsT2;
    private int set2PointsT2;
    private int set3PointsT2;
    private int equipoGanador;

    public Partido() {
    }

    public Partido(int idGame, String user, String player1, String player2, String player3, String player4, int set1PointsT1, int set2PointsT1, int set3PointsT1, int set1PointsT2, int set2PointsT2, int set3PointsT2, int equipoGanador) {
        this.idGame = idGame;
        this.user = user;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.set1PointsT1 = set1PointsT1;
        this.set2PointsT1 = set2PointsT1;
        this.set3PointsT1 = set3PointsT1;
        this.set1PointsT2 = set1PointsT2;
        this.set2PointsT2 = set2PointsT2;
        this.set3PointsT2 = set3PointsT2;
        this.equipoGanador = equipoGanador;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer3() {
        return player3;
    }

    public void setPlayer3(String player3) {
        this.player3 = player3;
    }

    public String getPlayer4() {
        return player4;
    }

    public void setPlayer4(String player4) {
        this.player4 = player4;
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

    public int getEquipoGanador() {
        return equipoGanador;
    }

    public void setEquipoGanador(int equipoGanador) {
        this.equipoGanador = equipoGanador;
    }
}