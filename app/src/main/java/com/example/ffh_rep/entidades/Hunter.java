package com.example.ffh_rep.entidades;

import java.io.Serializable;
import java.sql.Date;

public class Hunter implements Serializable {
    private Integer idHunter;
    private Usuario user;
    private String nombre;
    private String apellido;
    private String dni;
    private String sexo;
    private String correo_electronico;
    private String telefono;
    private String direccion;
    private Date fecha_nacimiento;
    private Rango id_rango;
    private Integer puntaje;

    public Hunter() {}

    //For Register
    public Hunter(Integer idHunter, Integer id_usuario, String nombre, String apellido, String dni, String sexo, String correo_electronico, String telefono, String direccion, Date fecha_nacimiento){
        this.idHunter = idHunter;
        this.user = user;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.sexo = sexo;
        this.correo_electronico = correo_electronico;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    //FOR UPDATE
    public Hunter(Integer idHunter, Usuario id_usuario, String nombre, String apellido, String dni, String sexo, String correo_electronico, String telefono, String direccion, Date fecha_nacimiento, Integer puntaje){
        this.idHunter = idHunter;
        this.user = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.sexo = sexo;
        this.correo_electronico = correo_electronico;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fecha_nacimiento = fecha_nacimiento;
        this.puntaje = puntaje;
    }
    public Hunter(Usuario user, String nombre, String apellido, String dni, String sexo, String correo_electronico, String telefono, String direccion, Date fecha_nacimiento, Rango id_rango, Integer puntaje) {
        this.user = user;
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


    public Integer getIdHunter (){return idHunter;}
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

    public Rango getId_rango() {
        return id_rango;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdHunter(Integer id) {
        this.idHunter = id;
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

    public void setId_rango(Rango id_rango) {
        this.id_rango = id_rango;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Hunter{" +
                "id_hunter=" + idHunter +
                "id_usuario=" + user + '\'' +
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
