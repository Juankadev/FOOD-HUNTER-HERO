package com.example.ffh_rep.entidades;

import java.io.Serial;
import java.io.Serializable;

public class Marca implements Serializable {
    private int idMarca;
    private String descripcion;

    public Marca() {
    }

    public Marca(int idMarca, String descripcion) {
        this.idMarca = idMarca;
        this.descripcion = descripcion;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Marca{" +
                "idMarca=" + idMarca +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}