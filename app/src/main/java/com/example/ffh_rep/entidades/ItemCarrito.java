package com.example.ffh_rep.entidades;

import java.io.Serializable;

public class ItemCarrito implements Serializable {
    private Articulo artc;
    private int cantidad;

    public ItemCarrito(Articulo artc, int cant){
        this.artc = artc;
        this.cantidad = cant;
    }
    public Articulo getArtc() {
        return artc;
    }

    public void setArtc(Articulo artc) {
        this.artc = artc;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ItemCarrito{" +
                "artc=" + artc.toString() +
                ", cantidad=" + cantidad +
                '}';
    }
}
