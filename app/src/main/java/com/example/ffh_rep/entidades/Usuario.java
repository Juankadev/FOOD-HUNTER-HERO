package com.example.ffh_rep.entidades;

import java.io.Serializable;

public class Usuario implements Serializable {
    private Integer id_usuario;
    private Rol rol;
    private String username;
    private String password;
    private boolean estado;

    public Usuario() {
    }

    public Usuario(Rol rol, String username, String password, boolean estado) {
        this.rol = rol;
        this.username = username;
        this.password = password;
        this.estado = estado;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", estado='" + estado + '\'' +
                ", rol=" + rol +
                '}';
    }
}