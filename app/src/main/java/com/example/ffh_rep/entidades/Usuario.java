package com.example.ffh_rep.entidades;

public class Usuario {
    Integer id_usuario;
    Integer id_rol;
    String username;
    String password;
    String estado;

    public Usuario (){}
    public Usuario(Integer id_rol, String username, String password, String estado) {
        this.id_rol = id_rol;
        this.username = username;
        this.password = password;
        this.estado = estado;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }
    public Integer getId_rol() {
        return id_rol;
    }
    public String getUsuario() {return username;}
    public String getPassword() {return password;}
    public String getEstado() {return estado;}

    public void setRol(Integer rol) {
        this.id_rol = rol;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setId_rol(Integer id_rol) {
        this.id_rol = id_rol;
    }
}
