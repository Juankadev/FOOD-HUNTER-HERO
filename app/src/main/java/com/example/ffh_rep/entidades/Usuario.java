package com.example.ffh_rep.entidades;

public class Usuario {
    Integer id_usuario;
    Integer id_rol;
    String username;
    String password;
    String estado;

    public Usuario(Integer id_usuario, Integer id_rol, String username, String password, String estado) {
        this.id_usuario = id_usuario;
        this.id_rol = id_rol;
        this.username = username;
        this.password = password;
        this.estado = estado;
    }
}
