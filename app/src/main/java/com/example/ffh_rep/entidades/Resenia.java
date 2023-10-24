package com.example.ffh_rep.entidades;

import java.util.Date;

public class Resenia {
    private Integer id_resena;
    private Comercio comercio;
    private Usuario usuario;
    private Integer calificacion;

    public Resenia() {}

    public Resenia(Integer id_resena, Comercio id_comercio, Usuario id_usuario, Integer calificacion) {
        this.id_resena = id_resena;
        this.comercio = id_comercio;
        this.usuario = id_usuario;
        this.calificacion = calificacion;
    }

    public void setId_resena(Integer id_resena) {
        id_resena = id_resena;
    }
    public void setComercio(Comercio comercio) {comercio = comercio;}
    public void setId_usuario(Usuario id_usuario) {
        id_usuario = id_usuario;
    }
    public void setCalificacion(Integer calificacion) {
        calificacion = calificacion;
    }

    public Integer getId_resena() {return this.id_resena;}
    public Comercio getComercio() {return this.comercio;}
    public Usuario getUsuario() {return this.usuario;}
    public Integer getCalificacion() {return this.calificacion;}
}
