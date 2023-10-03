package com.example.ffh_rep.entidades;

import java.sql.Date;

public class Hunter {
    Integer id_usuario;
    String nombre;
    String apellido;
    String dni;
    String sexo;
    String correo_electronico;
    String telefono;
    String direccion;
    Date fecha_nacimiento;
    Integer id_rango;
    Integer puntaje;

    public Hunter() {
    }

    public Hunter(Integer id_usuario, String nombre, String apellido, String dni, String sexo, String correo_electronico, String telefono, String direccion, Date fecha_nacimiento, Integer id_rango, Integer puntaje) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.sexo = sexo;
        this.correo_electronico = correo_electronico;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fecha_nacimiento = fecha_nacimiento;
        this.id_rango = id_rango;
        this.puntaje = puntaje;
    }


    public Integer getId_usuario() {
        return id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }

    public String getSexo() {
        return sexo;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public Integer getId_rango() {
        return id_rango;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public void setId_rango(Integer id_rango) {
        this.id_rango = id_rango;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }


    @Override
    public String toString() {
        return "Hunter{" +
                "id_usuario=" + id_usuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", sexo='" + sexo + '\'' +
                ", correo_electronico='" + correo_electronico + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha_nacimiento=" + fecha_nacimiento +
                ", id_rango=" + id_rango +
                ", puntaje=" + puntaje +
                '}';
    }
}
