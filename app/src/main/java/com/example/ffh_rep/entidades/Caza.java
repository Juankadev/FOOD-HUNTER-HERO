package com.example.ffh_rep.entidades;

import java.sql.Date;

public class Caza {
    private int idCaza;
    private Hunter hunter;
    private Comercio comercio;
    private int puntos;
    private Date fecha;
    //agrego este atributo para usarlo en Mis Compras Hunter y no tener que hacer una entidad aparte
    private int cantidad;

    public Caza() {
    }

    public Caza(int idCaza, Hunter hunter, Comercio comercio, int puntos, Date fecha) {
        this.idCaza = idCaza;
        this.hunter = hunter;
        this.comercio = comercio;
        this.puntos = puntos;
        this.fecha = fecha;
    }

    public Caza(Comercio comercio, Date fecha, int puntos, int cantidad) {
        this.comercio = comercio;
        this.puntos = puntos;
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    public int getIdCaza() {
        return idCaza;
    }

    public void setIdCaza(int idCaza) {
        this.idCaza = idCaza;
    }

    public Hunter getHunter() {
        return hunter;
    }

    public void setHunter(Hunter hunter) {
        this.hunter = hunter;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }

    public int getPuntos() {
        return puntos;
    }
    public int getCantidad() {
        return cantidad;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    @Override
    public String toString() {
        return "Caza{" +
                "idCaza=" + idCaza +
                ", hunter=" + hunter.getUser().getId_usuario() +
                ", comercio=" + comercio.getUser().getId_usuario() +
                ", puntos=" + puntos +
                ", fecha=" + fecha +
                '}';
    }
}