package com.example.ffh_rep.entidades;

public class Comercio {

    private int Id;
    private String RazonSocial;
    private int Cuit;
    private String Rubro;
    private String Email;
    private String Telefono;
    private String direccion;
    private Usuario user;

    public Comercio(){

    }
    public Comercio(int id, String razonSocial, int cuit, String rubro, String email, String telefono, String direccion, Usuario user) {
        Id = id;
        RazonSocial = razonSocial;
        Cuit = cuit;
        Rubro = rubro;
        Email = email;
        Telefono = telefono;
        this.direccion = direccion;
        this.user = user;
    }

    public Comercio(int id, String razonSocial, int cuit, String rubro, String email, String telefono, String direccion) {
        Id = id;
        RazonSocial = razonSocial;
        Cuit = cuit;
        Rubro = rubro;
        Email = email;
        Telefono = telefono;
        this.direccion = direccion;
    }

    public int getId() {
        return Id;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public int getCuit() {
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

    public void setId(int id) {
        Id = id;
    }

    public void setRazonSocial(String razonSocial) {
        RazonSocial = razonSocial;
    }

    public void setCuit(int cuit) {
        Cuit = cuit;
    }

    public void setRubro(String rubro) {
        Rubro = rubro;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

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
                ", user=" + user +
                '}';
    }
}
