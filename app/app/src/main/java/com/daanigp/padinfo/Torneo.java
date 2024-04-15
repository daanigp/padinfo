package com.daanigp.padinfo;

public class Torneo {
    private String nombre;
    private String ciudad;
    private int imagen;

    public Torneo() {
    }

    public Torneo(String nombre, String ciudad, int imagen) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.imagen = imagen;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
