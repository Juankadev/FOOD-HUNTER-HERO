package com.example.ffh_rep.entidades;

public class ComerciosFavoritos {
    private  Integer id_comercio_favorito;
    private Comercio id_comercio;
    private Usuario id_usuario;

    public ComerciosFavoritos() {}

    public ComerciosFavoritos(Integer id_comercio_favorito, Comercio id_comercio, Usuario id_usuario) {
        this.id_comercio_favorito = id_comercio_favorito;
        this.id_comercio = id_comercio;
        this.id_usuario = id_usuario;
    }

    public void setId_comercio_favorito(Integer id_comercio_favorito) {id_comercio_favorito = id_comercio_favorito;}
    public void setId_comercio(Comercio id_comercio) {
        id_comercio = id_comercio;
    }
    public void setId_usuario(Usuario id_usuario) {
        id_usuario = id_usuario;
    }

    public Integer getId_comercio_favorito() {return this.id_comercio_favorito;}
    public Comercio getId_comercio() {return this.id_comercio;}
    public Usuario getId_usuario() {return this.id_usuario;}

}
