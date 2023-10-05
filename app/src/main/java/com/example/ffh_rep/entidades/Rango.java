package com.example.ffh_rep.entidades;

public class Rango {
    private Integer id_rango;
    private String descripcion;
    private Integer puntaje;

    public Rango(){}

    public Rango(Integer id_rango, String descripcion, Integer puntaje) {
        this.id_rango = id_rango;
        this.descripcion = descripcion;
        this.puntaje = puntaje;
    }

    public void setIdRango(Integer idRango) {
        id_rango = idRango;
    }

    public void setDescripcion(String descripcion) {
        descripcion = descripcion;
    }
    public void setPuntaje(Integer puntaje) {
        puntaje = puntaje;
    }

    public Integer getId_rango() {return this.id_rango;}

    public String getDescripcion() {
        return descripcion;
    }
    public Integer getPuntaje() {
        return puntaje;
    }
}
