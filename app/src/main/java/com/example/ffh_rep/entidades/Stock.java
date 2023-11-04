package com.example.ffh_rep.entidades;

import java.io.Serializable;
import java.util.Date;

public class Stock implements Serializable {

    private Integer id_stock;
    private Articulo id_articulo;
    private Comercio id_comercio;
    private Date fecha_vencimiento;
    private Integer cantidad;

    public Stock() {}

    public Stock(Integer id_stock, Articulo id_articulo, Comercio id_comercio, Date fecha_vencimiento, Integer cantidad) {
        this.id_stock = id_stock;
        this.id_articulo = id_articulo;
        this.id_comercio = id_comercio;
        this.fecha_vencimiento = fecha_vencimiento;
        this.cantidad = cantidad;
    }

    public void setId_stock(Integer id_stock) {
        id_stock = id_stock;
    }
    public void setId_articulo(Articulo id_articulo) {
        id_articulo = id_articulo;
    }
    public void setId_comercio(Comercio id_comercio) {
        id_comercio = id_comercio;
    }
    public void setFecha_vencimiento(Date fecha_vencimiento) {fecha_vencimiento = fecha_vencimiento;}
    public void setCantidad(Integer cantidad) {cantidad = cantidad;}

    public Integer getId_stock() {return this.id_stock;}
    public Articulo getId_articulo() {return this.id_articulo;}
    public Comercio getId_comercio() {return this.id_comercio;}
    public Date getFecha_vencimiento() {return this.fecha_vencimiento;}
    public Integer getCantidad() {return this.cantidad;}
}
