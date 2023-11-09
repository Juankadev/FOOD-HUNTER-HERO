package com.example.ffh_rep.entidades;

import java.sql.Date;

public class Caza {
    private int idCaza;
    private Hunter hunter;
    private Comercio comercio;
    private int puntos;
    private Date fecha;

    public Caza() {
    }

    public Caza(int idCaza, Hunter hunter, Comercio comercio, int puntos, Date fecha) {
        this.idCaza = idCaza;
        this.hunter = hunter;
        this.comercio = comercio;
        this.puntos = puntos;
        this.fecha = fecha;
    }

    public Caza(Comercio comercio, Date fecha, int puntos) {
        this.comercio = comercio;
        this.puntos = puntos;
        this.fecha = fecha;
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

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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