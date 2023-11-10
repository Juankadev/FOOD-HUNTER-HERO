package com.example.ffh_rep.entidades;

import java.io.Serializable;

public class Beneficio implements Serializable {
    private Integer id_beneficio;
    private Comercio id_comercio;
    private String descripcion;
    private Integer puntos_requeridos;
    private boolean estado;

    public Beneficio() {}

    public Beneficio(Integer id_beneficio, Comercio id_comercio, String descripcion, Integer puntos_requeridos, boolean estado) {
        this.id_beneficio = id_beneficio;
        this.id_comercio = id_comercio;
        this.descripcion = descripcion;
        this.puntos_requeridos = puntos_requeridos;
        this.estado = estado;
    }

    public void setId_beneficio(Integer id_beneficio) {this.id_beneficio = id_beneficio;}
    public void setId_comercio(Comercio id_comercio) {this.id_comercio = id_comercio;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setPuntos_requeridos(Integer puntos_requeridos) {this.puntos_requeridos = puntos_requeridos;}
    public void setEstado(boolean estado) {this.estado = estado;}

    public Integer getId_beneficio() {return this.id_beneficio;}
    public Comercio getId_comercio() {return this.id_comercio;}
    public String getDescripcion() {return this.descripcion;}
    public Integer getPuntos_requeridos() {return this.puntos_requeridos;}
    public boolean getEstado() {return this.estado;}

    @Override
    public String toString() {
        return "Beneficio{" +
                "id_beneficio=" + id_beneficio +
                ", id_comercio=" + id_comercio +
                ", descripcion='" + descripcion + '\'' +
                ", puntos_requeridos=" + puntos_requeridos +
                ", estado=" + estado +
                '}';
    }
}
