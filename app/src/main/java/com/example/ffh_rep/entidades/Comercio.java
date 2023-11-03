package com.example.ffh_rep.entidades;

import java.io.Serializable;

public class Comercio implements Serializable {

    private int Id;
    private Usuario user;
    private String Cuit;
    private String RazonSocial;
    private String Rubro;
    private String Email;
    private String Telefono;
    private String direccion;
    private String aprobado;

    private boolean isFavorite;

    public Comercio(){

    }
    public Comercio(int id, String razonSocial, String cuit, String rubro, String email, String telefono, String direccion, Usuario user, String aprobado) {
        this.Id = id;
        this.RazonSocial = razonSocial;
        this.Cuit = cuit;
        this.Rubro = rubro;
        this.Email = email;
        this.Telefono = telefono;
        this.direccion = direccion;
        this.user = user;
        this.aprobado = aprobado;
    }

    public Comercio(int id, String razonSocial, String cuit, String rubro, String email, String telefono, String direccion, String aprobado) {
        this.Id = id;
        this.RazonSocial = razonSocial;
        this.Cuit = cuit;
        this.Rubro = rubro;
        this.Email = email;
        this.Telefono = telefono;
        this.direccion = direccion;
        this.aprobado = aprobado;
    }

    public boolean isFavorite() {
        return this.isFavorite;
    }

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    public int getId() {
        return Id;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public String getCuit() {
        return Cuit;
    }

    public String getRubro() {
        return Rubro;
    }

    public String getEmail() {
        return Email;
    }

    public String getTelefono() {
        return Telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public Usuario getUser() {
        return user;
    }

    public String getAprobado() {
        return aprobado;
    }

    public void setId(int id) {this.Id = id;}

    public void setRazonSocial(String razonSocial) {
        this.RazonSocial = razonSocial;
    }

    public void setCuit(String cuit) {
        this.Cuit = cuit;
    }

    public void setRubro(String rubro) {
        this.Rubro = rubro;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public void setTelefono(String telefono) {
        this.Telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setAprobado(String aprobado){this.aprobado = aprobado;}

    public void setUser(Usuario user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comercio{" +
                "Id=" + Id +
                ", RazonSocial='" + RazonSocial + '\'' +
                ", Cuit=" + Cuit +
                ", Rubro='" + Rubro + '\'' +
                ", Email='" + Email + '\'' +
                ", Telefono='" + Telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", user=" + user + '\'' +
                ", aprobado=" + aprobado +
                '}';
    }
}
