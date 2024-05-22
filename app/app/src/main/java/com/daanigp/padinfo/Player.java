package com.daanigp.padinfo;

public class Player {
    private int id;
    private int posicion;
    private String puntos;
    private String nombre;
    private String imagenURL;

    private String gender;

    public Player() {
    }

    public Player(int id, int posicion, String puntos, String nombre, String imagenURL, String gender) {
        this.id = id;
        this.posicion = posicion;
        this.puntos = puntos;
        this.nombre = nombre;
        this.imagenURL = imagenURL;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
