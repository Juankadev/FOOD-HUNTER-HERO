package com.example.ffh_rep.entidades;

import java.util.Date;

public class Resenia {
    private Integer id_resena;
    private Comercio comercio;
    private Usuario usuario;
    private Integer calificacion;
    private String comentario;

    public Resenia() {}

    public Resenia(Integer id_resena, Comercio id_comercio, Usuario id_usuario, Integer calificacion, String comentario) {
        this.id_resena = id_resena;
        this.comercio = id_comercio;
        this.usuario = id_usuario;
        this.calificacion = calificacion;
        this.comentario = comentario;
    }

    public Resenia(Integer id_resenia, Integer calificacion, String comentario){
        this.id_resena = id_resenia;
        this.comercio = new Comercio();
        this.usuario = new Usuario();
        this.calificacion = calificacion;
        this.comentario = comentario;
    }

    public void setId_resena(Integer id_resena) {
        this.id_resena = id_resena;
    }
    public void setComercio(Comercio comercio) {this.comercio = comercio;}
    public void setId_usuario(Usuario id_usuario) {
        this.usuario = id_usuario;
    }
    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public Integer getId_resena() {return this.id_resena;}
    public Comercio getComercio() {return this.comercio;}
    public Usuario getUsuario() {return this.usuario;}
    public Integer getCalificacion() {return this.calificacion;}

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
