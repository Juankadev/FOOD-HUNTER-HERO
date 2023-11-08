package com.example.ffh_rep.entidades;

import java.io.Serializable;
import java.util.List;

public class JSONQRRequest implements Serializable {
    private int idHunter;
    private List<ItemCarrito> lCarrito;
    private int idComercio;
    private int puntos;
    private int idQr;


    public JSONQRRequest(int idHunter, List<ItemCarrito> lCarrito, int idComercio, int puntos, int idqr) {
        this.idHunter = idHunter;
        this.lCarrito = lCarrito;
        this.idComercio = idComercio;
        this.puntos = puntos;
        this.idQr = idqr;
    }

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public int getIdHunter() {
        return idHunter;
    }

    public void setIdHunter(int idHunter) {
        this.idHunter = idHunter;
    }

    public List<ItemCarrito> getlCarrito() {
        return lCarrito;
    }

    public void setlCarrito(List<ItemCarrito> lCarrito) {
        this.lCarrito = lCarrito;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getIdQr() {
        return idQr;
    }

    @Override
    public String toString() {
        return "JSONQRRequest{" +
                "idHunter=" + idHunter +
                ", lCarrito=" + lCarrito +
                ", idComercio=" + idComercio +
                '}';
    }
}
