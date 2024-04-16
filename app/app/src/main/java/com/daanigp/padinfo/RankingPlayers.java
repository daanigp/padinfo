package com.daanigp.padinfo;

public class RankingPlayers {
    private int posicion;
    private String puntos;
    private String nombre;
    private int imagen;

    public RankingPlayers() {
    }

    public RankingPlayers(int posicion, String puntos, String nombre, int imagen) {
        this.posicion = posicion;
        this.puntos = puntos;
        this.nombre = nombre;
        this.imagen = imagen;
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

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
