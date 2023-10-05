package com.example.ffh_rep.entidades;

import java.util.Date;

public class Resenia {
    private Integer id_resena;
    private Comercio id_comercio;
    private Usuario id_usuario;
    private Integer calificacion;

    public Resenia() {}

    public Resenia(Integer id_resena, Comercio id_comercio, Usuario id_usuario, Integer calificacion) {
        this.id_resena = id_resena;
        this.id_comercio = id_comercio;
        this.id_usuario = id_usuario;
        this.calificacion = calificacion;
    }

    public void setId_resena(Integer id_resena) {
        id_resena = id_resena;
    }
    public void setId_comercio(Comercio id_comercio) {
        id_comercio = id_comercio;
    }
    public void setId_usuario(Usuario id_usuario) {
        id_usuario = id_usuario;
    }
    public void setCalificacion(Integer calificacion) {
        calificacion = calificacion;
    }

    public Integer getId_resena() {return this.id_resena;}
    public Comercio getId_comercio() {return this.id_comercio;}
    public Usuario getId_usuario() {return this.id_usuario;}
    public Integer integer() {return this.calificacion;}
}
