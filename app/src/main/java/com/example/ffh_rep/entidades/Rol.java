package com.example.ffh_rep.entidades;

import java.io.Serializable;

public class Rol implements Serializable {
    private Integer IdRol;
    private String Descripcion;

    public Rol(){}
    public Rol(Integer idRol, String descripcion) {
        IdRol = idRol;
        Descripcion = descripcion;
    }

    public Integer getIdRol() {
        return IdRol;
    }

    public void setIdRol(Integer idRol) {
        IdRol = idRol;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }


    @Override
    public String toString() {
        return "Rol{" +
                "IdRol=" + IdRol +
                ", Descripcion='" + Descripcion + '\'' +
                '}';
    }
}
