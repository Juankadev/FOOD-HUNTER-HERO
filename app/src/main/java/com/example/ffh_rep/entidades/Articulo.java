package com.example.ffh_rep.entidades;

import java.io.Serializable;

public class Articulo implements Serializable {
    private int idArticulo;
    private Comercio comercio; //Composición de Comercio
    private String descripcion;
    private double precio;
    private String imagen;
    private Marca marca; // Composición de Marca
    private Categoria categoria; // Composición de Categoria
    private String estado;

    private Stock stockArticulo;

    public Articulo() {
    }

    public Articulo(int idArticulo, Comercio comercio, String descripcion, double precio, String imagen, Marca marca, Categoria categoria, String estado) {
        this.comercio = comercio;
        this.idArticulo = idArticulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.marca = marca;
        this.categoria = categoria;
        this.estado = estado;
    }

    public Articulo(int idArticulo, Comercio comercio, String descripcion, double precio, String imagen, Marca marca, Categoria categoria, String estado, Stock stockArticulo) {
        this.idArticulo = idArticulo;
        this.comercio = comercio;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.marca = marca;
        this.categoria = categoria;
        this.estado = estado;
        this.stockArticulo = stockArticulo;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Stock getStockArticulo() {
        return stockArticulo;
    }

    public void setStockArticulo(Stock stockArticulo) {
        this.stockArticulo = stockArticulo;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "idArticulo=" + idArticulo +
                ", idComercio='" + comercio.getId() + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", imagen='" + imagen + '\'' +
                ", marca=" + marca.getIdMarca() +
                ", categoria=" + categoria.getIdCategoria() +
                ", estado='" + estado + '\'' +
                '}';
    }
}