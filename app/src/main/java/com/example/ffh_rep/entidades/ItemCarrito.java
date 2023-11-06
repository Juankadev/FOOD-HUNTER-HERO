package com.example.ffh_rep.entidades;

import java.io.Serializable;

public class ItemCarrito implements Serializable {
    private Stock stock;
    private int cantidad;

    public ItemCarrito(){}

    public ItemCarrito(Stock stock, int cant){
        this.stock = stock;
        this.cantidad = cant;
    }
    public Stock getArtc() {
        return stock;
    }

    public void setArtc(Stock artc) {
        this.stock = artc;
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
                "stock=" + stock +
                ", cantidad=" + cantidad +
                '}';
    }
}
