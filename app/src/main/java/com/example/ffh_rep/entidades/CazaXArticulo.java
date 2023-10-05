package com.example.ffh_rep.entidades;

public class CazaXArticulo {
    private Caza ID_caza;
    private Articulo id_articulo;
    private Integer Cantidad;

    public CazaXArticulo() {}

    public CazaXArticulo(Caza ID_caza, Articulo id_articulo, Integer Cantidad) {
        this.ID_caza = ID_caza;
        this.id_articulo = id_articulo;
        this.Cantidad = Cantidad;
    }

    public void setID_caza(Caza ID_caza) {
        ID_caza = ID_caza;
    }
    public void setId_articulo(Articulo id_articulo) {
        id_articulo = id_articulo;
    }
    public void setCantidad(Integer Cantidad) {
        Cantidad = Cantidad;
    }
    public Caza getID_caza() {return this.ID_caza;}
    public Articulo getId_articulo() {return this.id_articulo;}
    public Integer getCantidad() {return this.Cantidad;}
}
