package com.example.ffh_rep.entidades;

public class QrObject {

    private Hunter hunter;
    private Comercio commerce;
    private boolean estado;

    public QrObject (){}

    public QrObject(Hunter hunter, Comercio commerce, boolean estado) {
        this.hunter = hunter;
        this.commerce = commerce;
        this.estado = estado;
    }

    public Hunter getHunter() {
        return hunter;
    }

    public void setHunter(Hunter hunter) {
        this.hunter = hunter;
    }

    public Comercio getCommerce() {
        return commerce;
    }

    public void setCommerce(Comercio commerce) {
        this.commerce = commerce;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
